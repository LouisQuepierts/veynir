package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.adapter.Consumer4f;

public interface AnimationResultView {

    void read(int channel, Consumer4f consumer);

    void read(int channel, float[] out);

}
