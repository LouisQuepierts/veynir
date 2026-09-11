package net.quepierts.veynir.backend.pipeline;

import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.core.channel.ChannelFormat;
import net.quepierts.veynir.core.channel.ChannelLayout;
import net.quepierts.veynir.backend.sampler.AnimationSampler;
import net.quepierts.veynir.core.pipeline.SamplingMode;
import net.quepierts.veynir.core.pipeline.UniformReader;
import net.quepierts.veynir.core.AnimationState;
import net.quepierts.veynir.core.adapter.PipelineInputProvider;
import net.quepierts.veynir.backend.interpolator.Interpolator4f;
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

    @NonNull    UniformReader           getUniformBuffer(int location);

    @NonNull    Interpolator4f[]        getInterpolators();

    @Nullable   PipelineInputProvider   getInputProvider();

    boolean                             getOperationMask(int index);

    boolean                             getChannelMask(int channel);

}
