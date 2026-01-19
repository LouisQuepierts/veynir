package net.quepierts.animata4j.core.buffer;

import net.quepierts.animata4j.core.program.buffer.AnimationFrameBuffer;

import java.util.ArrayList;
import java.util.List;

public final class BufferManager {

    private final int pageSize;

    private final List<AnimationFrameBuffer> pages;
    private final List<AnimationFrameBuffer> available;

    public BufferManager(int pageSize)
    {
        this.pageSize = pageSize;
        this.pages = new ArrayList<>();
        this.available = new ArrayList<>();
    }

    public AnimationFrameBuffer request() {
        if (this.available.isEmpty()) {
            var buffer = AnimationFrameBuffer.create(this.pageSize);
            this.pages.add(buffer);
            return buffer;
        } else {
            return this.available.remove(this.available.size() - 1);
        }
    }

    public void release(AnimationFrameBuffer buffer, boolean clear) {
        if (this.available.contains(buffer))
        {
            throw new IllegalArgumentException("Buffer is already released.");
        }

        if (clear) {
            buffer.fill(0);
        }

        this.available.add(buffer);
    }

}
