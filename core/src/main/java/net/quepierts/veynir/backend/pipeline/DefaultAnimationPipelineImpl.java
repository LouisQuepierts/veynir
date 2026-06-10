package net.quepierts.veynir.backend.pipeline;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.quepierts.veynir.backend.Patterns;
import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.channel.ChannelFormat;
import net.quepierts.veynir.backend.channel.ChannelFormatElement;
import net.quepierts.veynir.backend.channel.ChannelLayout;
import net.quepierts.veynir.backend.channel.DefaultChannelFormats;
import net.quepierts.veynir.backend.exception.UnboundSamplerException;
import net.quepierts.veynir.backend.exception.UnboundUniformBufferException;
import net.quepierts.veynir.backend.execution.ExecutionReflection;
import net.quepierts.veynir.backend.execution.ExecutionState;
import net.quepierts.veynir.backend.pass.AnimationPass;
import net.quepierts.veynir.backend.pass.definition.AnimationPassDefinition;
import net.quepierts.veynir.backend.sampler.AnimationSampler;
import net.quepierts.veynir.backend.sampler.OriginSampler;
import net.quepierts.veynir.backend.sampler.SamplingMode;
import net.quepierts.veynir.backend.uniform.*;
import net.quepierts.veynir.core.AnimationState;
import net.quepierts.veynir.core.adapter.AnimationOutput;
import net.quepierts.veynir.core.adapter.PipelineInputProvider;
import net.quepierts.veynir.core.interpolator.Interpolator4f;
import net.quepierts.veynir.core.util.ArrayUtils;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultAnimationPipelineImpl implements AnimationPipeline {

    @Getter
    private final ChannelFormat             channelFormat;

    @Getter
    private final ChannelLayout             channelLayout;

    private final AnimationPass[]           parameterPasses;
    private final AnimationPass[]           passes;

    private final AnimationResultBuffer     result;
    private final AnimationFrameBuffer[]    buffers;

    private final AnimationSampler[]        samplers;
    private final SamplingMode[]            samplingModes;
    private final UniformBuffer[]           ubos;
    private final AnimationOutput[]         targets;

    @Getter
    private final UniformBuffer             uniform;

    private final Interpolator4f[]          interpolations;

    private final Reflection                reflection;

    @Getter
    private final ExecutionState            executionState;

    private final Context                   context;

    private DefaultAnimationPipelineImpl(
            ChannelFormat       format,
            ChannelLayout       layout,
            AnimationPass[][]   passes,
            UboDefinition       uniform,
            Interpolator4f[]    interpolations,
            Reflection          reflection
    ) {
        this.channelFormat      = format;
        this.channelLayout      = layout;
        this.parameterPasses    = passes[0];
        this.passes             = passes[1];
        this.interpolations     = interpolations;
        this.reflection         = reflection;
        this.executionState     = new ExecutionState(reflection.oid.size());

        var bufferAmount        = reflection.buffers.size();
        var bufferSize          = layout.getChannelCount() << 2;
        var buffer              = new AnimationBuffer(bufferSize * bufferAmount);
        var buffers             = new AnimationFrameBuffer[bufferAmount];

        for (int i = 0;
             i < bufferAmount;
             i++
        ) {
            final var fbo = new AnimationFrameBuffer(
                    buffer,
                    bufferSize * i,
                    bufferSize
            );
            fbo                 .setClearValue(Float.NaN);
            buffers[i]          = fbo;
        }

        this.result             = new AnimationResultBuffer(buffer, bufferSize);
        this.buffers            = buffers;

        this.samplers           = new AnimationSampler[reflection.samplers.size()];
        this.samplingModes      = new SamplingMode[reflection.samplers.size()];
        this.ubos               = new UniformBuffer[reflection.ubos.size()];
        this.uniform            = new UniformBuffer(uniform);

        this.targets            = new AnimationOutput[1];

        this.samplers[0]        = new OriginSampler();
        Arrays.fill(this.samplingModes, SamplingMode.DEFAULT);

        this.context            = new Context(this);
    }

    @Override
    public void submit(@NonNull final AnimationState state) {
        var context     = this.context;
        context.state   = state;

        for (var buffer : this.buffers) {
            buffer.clear();
        }

        execute("Parameter", this.parameterPasses);
        execute("Compute", this.passes);

        context.state   = null;
        this.targets[0].accept(this.result);
    }

    private void execute(
            String stage,
            AnimationPass[] passes
    ) {
        var pid = 0;
        try {
            for (; pid < passes.length; pid++) {
                final var pass = passes[pid];
                pass.execute(this.context);
            }
        } catch (Exception e) {
            final var pass = passes[pid];
            final var name = pass.getName();

            // print stage, pid, name, exception
            log.error("[Stage: {}] Pass '{}' (PID: {}) failed:", stage, name, pid, e);
        }
    }

    /*@Override
    public void submit(
            @NonNull AnimationState state,
            @Nullable PipelineInputProvider input,
            @NonNull AnimationOutput output
    ) {
        var context     = this.context;
        context.state   = state;
        context.input   = input;

        for (var pass : this.passes) {
            pass.execute(context);
        }

        output.accept(this.result);

        context.state   = null;
        context.input   = null;
    }*/

    @Override
    public void bindSource(
            final String name,
            final AnimationSampler sampler
    ) {
        var location            = this.reflection.samplers.find(name);
        if (location == -1) {
            log.error("Sampler '{}' not found.", name);
            return;
        }

        this.bindSource(location, sampler);
    }

    @Override
    public void bindSource(
            final int location,
            final AnimationSampler sampler
    ) {
        if (location == 0) {
            log.error("Cannot bind source to location 0.");
            return;
        }

        this.samplers[location] = sampler;
    }

    @Override
    public void bindUbo(
            final String name,
            final UniformBuffer buffer
    ) {
        var location            = this.reflection.ubos.find(name);
        if (location == -1) {
            log.error("UBO '{}' not found.", name);
            return;
        }

        this.bindUbo(location, buffer);
    }

    @Override
    public void bindUbo(
            final int location,
            final UniformBuffer buffer
    ) {
        this.ubos[location]     = buffer;
    }

    @Override
    public void bindTarget(
            final String name,
            final AnimationOutput target
    ) {
        // todo: MRT
        this.targets[0] = target;
    }

    @Override
    public void bindTarget(
            final int location,
            final AnimationOutput target
    ) {
        // todo: MRT
        this.targets[0] = target;
    }

    @Override
    public void setSamplingMode(
            final int location,
            final SamplingMode mode
    ) {
        this.samplingModes[location] = mode;
    }

    @Override
    public ExecutionReflection getReflection() {
        return this.reflection;
    }

    public static Compiler compiler() {
        return new Compiler();
    }

    public static final class Compiler {

        private ChannelLayout                           layout;
        private ChannelFormat                           format      = DefaultChannelFormats.EMPTY;
        private final List<AnimationPassDefinition>     passes      = new ArrayList<>();
        private final Set<String>                       samplers    = new ObjectArraySet<>();
        private final Set<String>                       buffers     = new ObjectArraySet<>();
        private final UboDefinition.Builder             uniforms    = UboDefinition.builder();
        private final Map<String, UboDefinition>        ubo         = new Object2ObjectArrayMap<>();
        private final Map<String, Interpolator4f>       lerps       = new Object2ObjectArrayMap<>();

        private Compiler() {
            this.samplers.add(ORIGINAL_SAMPLER);
            this.buffers.add(OUTPUT_BUFFER);

            this.lerps.put("linear",        Interpolator4f.LINEAR);
            this.lerps.put("catmull_rom",   Interpolator4f.CATMULL_ROM);
            this.lerps.put("constant",      Interpolator4f.CONSTANT);
        }

        public Compiler withChannelLayout(ChannelLayout layout) {
            this.layout = layout;
            return this;
        }

        public Compiler withChannelFormat(ChannelFormat format) {
            this.format = format;
            return this;
        }

        public Compiler withPass(AnimationPassDefinition pass) {
            this.passes.add(pass);
            return this;
        }

        public Compiler withSampler(String name) {
            if (!Patterns.PATTERN_IDENTIFIER
                    .matcher(name)
                    .matches()) {
                throw new IllegalArgumentException("Invalid sampler name: " + name);
            }
            this.samplers.add(name);
            return this;
        }

        public Compiler withBuffer(String name) {
            if (!Patterns.PATTERN_IDENTIFIER
                    .matcher(name)
                    .matches()) {
                throw new IllegalArgumentException("Invalid buffer name: " + name);
            }
            this.buffers.add(name);
            return this;
        }

        public Compiler withOutput(String name) {
            // todo: MRT
            return this;
        }

        public Compiler withUniform(String name, UniformType type) {
            if (!Patterns.PATTERN_IDENTIFIER
                    .matcher(name)
                    .matches()) {
                throw new IllegalArgumentException("Invalid uniform name: " + name);
            }
            this.uniforms.withUniform(name, type);
            return this;
        }

        public Compiler withUniform(String name, UboDefinition definition) {
            if (!Patterns.PATTERN_IDENTIFIER
                    .matcher(name)
                    .matches()) {
                throw new IllegalArgumentException("Invalid ubo name: " + name);
            }
            this.ubo.put(name, definition);
            return this;
        }

        public Compiler withInterpolation(String name, Interpolator4f lerp) {
            if (!Patterns.PATTERN_IDENTIFIER
                    .matcher(name)
                    .matches()) {
                throw new IllegalArgumentException("Invalid lerp name: " + name);
            }
            this.lerps.put(name, lerp);
            return this;
        }

        @SuppressWarnings("unchecked")
        public DefaultAnimationPipelineImpl compile() {

            if (this.layout == null) {
                throw new IllegalStateException("Channel layout is not set.");
            }

            if (this.passes.isEmpty()) {
                throw new IllegalStateException("No passes are set.");
            }

            var uniform         = this.uniforms.build();

            var bufferNames     = LocationLookup.of(this.buffers);
            var samplerNames    = LocationLookup.of(this.samplers);
            var uboNames        = LocationLookup.of(this.ubo.keySet());
            var lerpNames       = LocationLookup.of(this.lerps.keySet());

            var oids            = new ArrayList<String>();

            var context         = new AnimationPipelineCompileContext(
                                samplerNames,
                                bufferNames,
                                uniform.getLookup(),
                                uboNames,
                                oids
            );

            var passes          = (ArrayList<AnimationPass>[]) new ArrayList[3];
            ArrayUtils          .init(passes, () -> new ArrayList<AnimationPass>());
            for (final var definition : this.passes) {
                context         .oidObject(definition.getName());
                final var pass  = definition.compile(context);
                passes[definition.getType().ordinal()].add(pass);
            }

            if (context.hasErrors()) {
                context.printErrors(log::error);
                throw new IllegalStateException("Pipeline compile failed.");
            }

            var reflection = new Reflection(
                    bufferNames,
                    samplerNames,
                    uniform.getLookup(),
                    uboNames,
                    LocationLookup.of(oids),
                    lerpNames
            );

            var interpolations = new Interpolator4f[this.lerps.size()];
            for (var i = 0; i < this.lerps.size(); i++) {
                interpolations[i] = this.lerps.get(lerpNames.name(i));
            }

            return new DefaultAnimationPipelineImpl(
                    this.format,
                    this.layout,
                    Arrays.stream(passes)
                            .map(a -> a.toArray(AnimationPass[]::new))
                            .toArray(AnimationPass[][]::new),
                    uniform,
                    interpolations,
                    reflection
            );
        }
    }

    private static final class Context implements AnimationContext {

        private final   DefaultAnimationPipelineImpl    pipeline;

        private final   int                             attrubuteSize;
        private final   int                             enableOffset;
        private final   boolean                         hasMaskElement;


        private         AnimationState                  state;
        private         PipelineInputProvider           input;

        private Context(
                DefaultAnimationPipelineImpl pipeline
        ) {
            this.pipeline       = pipeline;
            var format          = pipeline.getChannelFormat();
            this.attrubuteSize  = format.getAttributeSize();
            this.enableOffset   = format.getOffset(ChannelFormatElement.MASK);
            this.hasMaskElement = format.getElements().contains(ChannelFormatElement.MASK);
        }

        @Override
        public float getProgress() {
            return this.state.getProgress();
        }

        @Override
        public @NonNull ChannelLayout getChannelLayout() {
            return this.pipeline.getChannelLayout();
        }

        @Override
        public @NonNull ChannelFormat getChannelFormat() {
            return this.pipeline.getChannelFormat();
        }

        @Override
        public @NonNull AnimationState getAnimationState() {
            return this.state;
        }

        @Override
        public @NonNull AnimationSampler getSampler(int location) {
            final var sampler = this.pipeline.samplers[location];
            if (sampler == null) {
                throw new UnboundSamplerException(location);
            }
            return sampler;
        }

        @Override
        public @NonNull SamplingMode getSamplingMode(int location) {
            return this.pipeline.samplingModes[location];
        }

        @Override
        public @NonNull AnimationFrameBuffer getFrameBuffer(int location) {
            return this.pipeline.buffers[location];
        }

        @Override
        public @NonNull AnimationBuffer getParameterBuffer() {
            return this.state.getParameterBuffer();
        }

        @Override
        public @NonNull UniformReader getUniform() {
            return this.pipeline.getUniform();
        }

        @Override
        public @NonNull UniformReader getUniformBuffer(final int location) {
            final var ubo = this.pipeline.ubos[location];
            if (ubo == null) {
                throw new UnboundUniformBufferException(location);
            }
            return ubo;
        }

        @Override
        public @NonNull Interpolator4f[] getInterpolators() {
            return this.pipeline.interpolations;
        }

        @Override
        public @Nullable PipelineInputProvider getInputProvider() {
            return this.input;
        }

        @Override
        public boolean getOperationMask(int index) {
            return true; // todo
        }

        @Override
        public boolean getChannelMask(int channel) {
            if (channel == -1) {
                return false;
            }

            return !this.hasMaskElement
                    || this.state.getChannelAttribute()
                        .readBoolean(channel * this.attrubuteSize + this.enableOffset);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class Reflection implements ExecutionReflection {

        private final LocationLookup buffers;
        private final LocationLookup samplers;
        private final LocationLookup uniform;
        private final LocationLookup ubos;

        private final LocationLookup oid;

        private final LocationLookup interpolations;

        @Override
        public int oid(final @NonNull String semantic) {
            return this.oid.find(semantic);
        }

        @Override
        public int location(final @NonNull String semantic) {
            final var args      = semantic.split("\\.");
            final var namespace = args[0];

            return switch (namespace) {
                case "buffer"           -> this.buffers.find(args[1]);
                case "sampler"          -> this.samplers.find(args[1]);
                case "uniform"          -> this.uniform.find(args[1]);
                case "ubo"              -> this.ubos.find(args[1]);
                case "interpolation"    -> this.interpolations.find(args[1]);
                default -> -1;
            };
        }
    }

}
