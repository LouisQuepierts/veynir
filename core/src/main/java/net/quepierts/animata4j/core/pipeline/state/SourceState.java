package net.quepierts.animata4j.core.pipeline.state;

import lombok.Getter;
import lombok.Setter;

public class SourceState extends RuntimeState {

    @Setter
    @Getter
    private int lastFrameIndex;

}
