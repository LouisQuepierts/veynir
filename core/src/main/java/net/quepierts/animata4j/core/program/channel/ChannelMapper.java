package net.quepierts.animata4j.core.program.channel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelMapper {

    private final int[] offsets;

    public int getOffset(int index) {
        return offsets[index];
    }

    public boolean isActive(int index) {
        return getOffset(index) != -1;
    }

}
