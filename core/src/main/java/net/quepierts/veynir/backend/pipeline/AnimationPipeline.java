package net.quepierts.veynir.backend.pipeline;

import net.quepierts.veynir.backend.channel.ChannelFormat;
import net.quepierts.veynir.backend.channel.ChannelLayout;
import net.quepierts.veynir.backend.execution.ExecutionReflection;
import net.quepierts.veynir.backend.execution.ExecutionState;
import net.quepierts.veynir.backend.sampler.AnimationSampler;
import net.quepierts.veynir.backend.sampler.SamplingMode;
import net.quepierts.veynir.backend.uniform.UniformBuffer;
import net.quepierts.veynir.core.AnimationState;
import net.quepierts.veynir.core.adapter.AnimationOutput;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

public interface AnimationPipeline {
    String ORIGINAL_SAMPLER = "Sampler#Origin";
    String OUTPUT_BUFFER = "Buffer#Result";

    @Contract(" -> new")
    static DefaultAnimationPipelineImpl.@NonNull Compiler compiler() {
        return DefaultAnimationPipelineImpl.compiler();
    }

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
            AnimationSampler sampler
    );

    void bindSource(
            int location,
            AnimationSampler sampler
    );

    void bindUbo(
            String name,
            UniformBuffer buffer
    );

    void bindUbo(
            int location,
            UniformBuffer buffer
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

    ChannelLayout getChannelLayout();

    UniformBuffer getUniform();

    ExecutionReflection getReflection();

    ExecutionState getExecutionState();
}
