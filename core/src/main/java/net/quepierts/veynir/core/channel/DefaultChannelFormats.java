package net.quepierts.veynir.core.channel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultChannelFormats {

    public static final ChannelFormat EMPTY     = ChannelFormat.builder().build();

    public static final ChannelFormat TIMELINE  = ChannelFormat.builder()
            .add("cursor", ChannelFormatElement.CURSOR)
            .add("weight", ChannelFormatElement.WEIGHT)
            .add("mask", ChannelFormatElement.MASK)
            .build();

}
