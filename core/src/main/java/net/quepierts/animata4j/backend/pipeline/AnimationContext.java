package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.backend.channel.ChannelFormat;
import net.quepierts.animata4j.backend.channel.ChannelLayout;
import net.quepierts.animata4j.backend.sampler.AnimationSampler;
import net.quepierts.animata4j.backend.sampler.SamplingMode;
import net.quepierts.animata4j.backend.uniform.UniformReader;
import net.quepierts.animata4j.core.AnimationState;
import net.quepierts.animata4j.core.adapter.PipelineInputProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface AnimationContext {

    float                               getProgress();

    @NonNull    ChannelLayout           getChannelLayout();

    @NonNull    ChannelFormat           getChannelFormat();

    @NonNull    AnimationState          getAnimationState();

    @NonNull    AnimationSampler        getSampler(int location);

    @NonNull    SamplingMode            getSamplingMode(int location);

    @NonNull    AnimationFrameBuffer    getFrameBuffer(int location);

    @NonNull    AnimationBuffer         getParameterBuffer();

    @NonNull    UniformReader           getUniform();

    @Nullable   UniformReader           getUniformBuffer(int location);

    @Nullable   PipelineInputProvider   getInputProvider();

    boolean                             getOperationMask(int index);

    boolean                             getSamplerMask(int channel);

}
