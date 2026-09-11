package net.quepierts.veynir.backend.pipeline;

import net.quepierts.veynir.backend.interpolator.Interpolator4f;
import net.quepierts.veynir.backend.pass.definition.AnimationPassDefinition;
import net.quepierts.veynir.core.channel.ChannelFormat;
import net.quepierts.veynir.core.channel.ChannelLayout;
import net.quepierts.veynir.core.pipeline.AnimationPipeline;
import net.quepierts.veynir.core.pipeline.AnimationPipelineCompiler;
import net.quepierts.veynir.core.pipeline.PassDefinition;
import net.quepierts.veynir.core.uniform.UboDefinition;
import net.quepierts.veynir.core.uniform.UniformType;
import org.jspecify.annotations.NonNull;

public final class AnimationPipelineCompilerImpl implements AnimationPipelineCompiler {

    private final DefaultAnimationPipelineImpl.Compiler compiler;

    public AnimationPipelineCompilerImpl(final DefaultAnimationPipelineImpl.@NonNull Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withChannelLayout(final @NonNull ChannelLayout layout) {
        this.compiler.withChannelLayout(layout);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withChannelFormat(final @NonNull ChannelFormat format) {
        this.compiler.withChannelFormat(format);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withPass(final @NonNull PassDefinition pass) {
        if (!(pass instanceof AnimationPassDefinition definition)) {
            throw new IllegalArgumentException("Not an animation pass definition: " + pass);
        }
        this.compiler.withPass(definition);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withSampler(final @NonNull String name) {
        this.compiler.withSampler(name);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withBuffer(final @NonNull String name) {
        this.compiler.withBuffer(name);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withOutput(final @NonNull String name) {
        this.compiler.withOutput(name);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withUniform(
            final @NonNull String name,
            final @NonNull UniformType type
    ) {
        this.compiler.withUniform(name, type);
        return this;
    }

    @Override
    public @NonNull AnimationPipelineCompiler withUbo(
            final @NonNull String name,
            final @NonNull UboDefinition definition
    ) {
        this.compiler.withUniform(name, definition);
        return this;
    }

    public @NonNull AnimationPipelineCompiler withInterpolation(
            final @NonNull String name,
            final @NonNull Interpolator4f interpolation
    ) {
        this.compiler.withInterpolation(name, interpolation);
        return this;
    }

    @Override
    public @NonNull AnimationPipeline compile() {
        return this.compiler.compile();
    }

}
