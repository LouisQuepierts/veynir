package net.quepierts.veynir.core.adapter;

import net.quepierts.veynir.backend.buffer.WritableBuffer;

public interface PipelineInputProvider {

    void fill(int channel, int offset, WritableBuffer out);

}
