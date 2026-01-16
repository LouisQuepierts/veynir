package net.quepierts.animata4j.dsl.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public final class SourcePos implements Comparable<SourcePos> {
    private final int line;
    private final int col;

    public static SourcePos of(int line, int col) {
        return new SourcePos(line, col);
    }

    public SourcePos copy() {
        return new SourcePos(line, col);
    }

    @Override
    public int compareTo(@NotNull SourcePos other) {
        int compareLine = Integer.compare(this.line, other.line);
        return compareLine != 0 ? compareLine : Integer.compare(this.col, other.col);
    }
}
