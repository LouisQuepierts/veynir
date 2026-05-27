package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.core.adapter.Consumer4f;

public interface AnimationResultView {

    void read(int channel, Consumer4f consumer);

    void read(int channel, float[] out);

}
