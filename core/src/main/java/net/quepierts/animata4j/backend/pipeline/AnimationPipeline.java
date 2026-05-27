package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.backend.channel.ChannelFormat;
import net.quepierts.animata4j.backend.channel.ChannelLayout;
import net.quepierts.animata4j.backend.execution.ExecutionReflection;
import net.quepierts.animata4j.backend.execution.ExecutionState;
import net.quepierts.animata4j.backend.sampler.AnimationSampler;
import net.quepierts.animata4j.backend.sampler.SamplingMode;
import net.quepierts.animata4j.backend.uniform.UniformBuffer;
import net.quepierts.animata4j.core.AnimationState;
import net.quepierts.animata4j.core.adapter.AnimationOutput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface AnimationPipeline {
    String ORIGINAL_SAMPLER = "Sampler#Origin";
    String OUTPUT_BUFFER = "Buffer#Result";

    @Contract(" -> new")
    static DefaultAnimationPipelineImpl.@NotNull Compiler compiler() {
        return DefaultAnimationPipelineImpl.compiler();
    }

    void submit(
            @NotNull AnimationState state
    );

    default void submit(
            @NotNull AnimationState state,
            @NotNull AnimationOutput output
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
