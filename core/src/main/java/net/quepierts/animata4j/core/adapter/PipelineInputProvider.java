package net.quepierts.animata4j.core.adapter;

import net.quepierts.animata4j.backend.buffer.WritableBuffer;

public interface PipelineInputProvider {

    void fill(int channel, int offset, WritableBuffer out);

}
