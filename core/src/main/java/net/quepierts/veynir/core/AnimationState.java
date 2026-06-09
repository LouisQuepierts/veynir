package net.quepierts.veynir.core;

import lombok.Getter;
import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.buffer.AttributeBuffer;
import net.quepierts.veynir.backend.channel.ChannelFormat;
import net.quepierts.veynir.backend.channel.ChannelLayout;

@Getter
public abstract class AnimationState {

    public float progress;
    public float lastProgress;

    private final AttributeBuffer   channelAttribute;

    protected AnimationState(
            ChannelLayout channelLayout,
            ChannelFormat channelFormat
    ) {

        var attributes          = channelLayout.getChannelCount() * channelFormat.getAttributeSize();
        this.channelAttribute   = new AttributeBuffer(attributes);
    }

    public AnimationBuffer getParameterBuffer() {
        return null;
    }
}
