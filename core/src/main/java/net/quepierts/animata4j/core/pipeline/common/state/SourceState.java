package net.quepierts.animata4j.core.pipeline.common.state;

import lombok.Getter;
import lombok.Setter;

public class SourceState extends RuntimeState {

    @Setter
    @Getter
    private int cursor;

    public float readBufferValue(int index) {
        return 0; // todo
    }

}
