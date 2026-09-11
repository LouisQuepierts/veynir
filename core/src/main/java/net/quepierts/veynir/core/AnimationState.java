package net.quepierts.veynir.core;

import lombok.Getter;
import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
import net.quepierts.veynir.core.channel.ChannelFormat;
import net.quepierts.veynir.core.channel.ChannelLayout;

@Getter
public abstract class AnimationState {

    public float progress;
    public float lastProgress;

    private final AttributeBufferObject channelAttribute;

    protected AnimationState(
            ChannelLayout channelLayout,
            ChannelFormat channelFormat
    ) {

        var attributes          = channelLayout.getChannelCount() * channelFormat.getAttributeSize();
        this.channelAttribute   = Veynir13.attributeBuffer(attributes);
    }

    public AnimationBufferObject getParameterBuffer() {
        return null;
    }
}
