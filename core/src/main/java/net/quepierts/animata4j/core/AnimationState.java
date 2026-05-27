package net.quepierts.animata4j.core;

import lombok.Getter;
import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.backend.buffer.AttributeBuffer;
import net.quepierts.animata4j.backend.channel.ChannelFormat;
import net.quepierts.animata4j.backend.channel.ChannelLayout;

@Getter
public abstract class AnimationState {

    public float progress;
    public float lastProgress;

    private final AttributeBuffer channelAttribute;

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
