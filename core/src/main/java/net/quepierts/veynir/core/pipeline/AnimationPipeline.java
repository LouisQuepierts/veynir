package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.AnimationState;
import net.quepierts.veynir.core.adapter.AnimationOutput;
import net.quepierts.veynir.core.channel.ChannelFormat;
import net.quepierts.veynir.core.channel.ChannelLayout;
import org.jspecify.annotations.NonNull;

public interface AnimationPipeline {
    String ORIGINAL_SAMPLER = "Sampler#Origin";
    String OUTPUT_BUFFER = "Buffer#Result";

    void submit(
            @NonNull AnimationState state
    );

    default void submit(
            @NonNull AnimationState state,
            @NonNull AnimationOutput output
    ) {
        this.bindTarget(0, output);
        this.submit(state);
        this.bindTarget(0, null);
    }

    void bindSource(
            String name,
            AnimationSamplerObject sampler
    );

    void bindSource(
            int location,
            AnimationSamplerObject sampler
    );

    void bindUbo(
            String name,
            UniformBufferObject buffer
    );

    void bindUbo(
            int location,
            UniformBufferObject buffer
    );

    void bindTarget(
            String name,
            AnimationOutput target
    );

    void bindTarget(
            int location,
            AnimationOutput target
    );

    void setSamplingMode(
            int location,
            SamplingMode mode
    );

    ChannelFormat getChannelFormat();

    @Deprecated(since = "1.3", forRemoval = true)
    ChannelLayout getChannelLayout();

    UniformBufferObject getUniform();

    ExecutionReflection getReflection();
}
