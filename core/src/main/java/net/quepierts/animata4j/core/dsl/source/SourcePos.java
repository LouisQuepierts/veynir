package net.quepierts.animata4j.core.dsl.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SourcePos {
    private final int line;
    private final int col;

    public static SourcePos of(int line, int col) {
        return new SourcePos(line, col);
    }

    public SourcePos copy() {
        return new SourcePos(line, col);
    }
}
