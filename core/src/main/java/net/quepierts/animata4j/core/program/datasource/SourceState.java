package net.quepierts.animata4j.core.program.datasource;

import lombok.Getter;
import lombok.Setter;

public interface SourceState {

    int getCursor();

    void setCursor(int cursor);

    default float readBufferValue(int index) {
        return 0; // todo
    }


    static SourceState create() {
        return new Impl();
    }

    class Impl implements SourceState {
        @Getter
        @Setter
        private int cursor;
    }

}
