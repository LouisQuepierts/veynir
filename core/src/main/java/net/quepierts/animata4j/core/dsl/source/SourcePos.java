package net.quepierts.animata4j.core.dsl.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SourcePos {
    private final int line;
    private final int col;
    private final int pos;

    public static SourcePos of(int line, int col, int pos) {
        return new SourcePos(line, col, pos);
    }

    public SourcePos copy() {
        return new SourcePos(line, col, pos);
    }
}
