package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.backend.channel.ChannelFormat;
import net.quepierts.animata4j.backend.channel.ChannelLayout;
import net.quepierts.animata4j.backend.sampler.AnimationSampler;
import net.quepierts.animata4j.backend.sampler.SamplingMode;
import net.quepierts.animata4j.backend.uniform.UniformReader;
import net.quepierts.animata4j.core.AnimationState;
import net.quepierts.animata4j.core.adapter.PipelineInputProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnimationContext {

    float                               getProgress();

    @NotNull    ChannelLayout           getChannelLayout();

    @NotNull    ChannelFormat           getChannelFormat();

    @NotNull    AnimationState          getAnimationState();

    @NotNull    AnimationSampler        getSampler(int location);

    @NotNull    SamplingMode            getSamplingMode(int location);

    @NotNull    AnimationFrameBuffer    getFrameBuffer(int location);

    @NotNull    AnimationBuffer         getParameterBuffer();

    @NotNull    UniformReader           getUniform();

    @Nullable   UniformReader           getUniformBuffer(int location);

    @Nullable   PipelineInputProvider   getInputProvider();

    boolean                             getOperationMask(int index);

    boolean                             getSamplerMask(int channel);

}
