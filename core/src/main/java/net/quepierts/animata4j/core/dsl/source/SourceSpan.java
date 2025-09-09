package net.quepierts.animata4j.core.dsl.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class SourceSpan {
    private final SourcePos begin;
    private final SourcePos end;

    public static SourceSpan of(SourceSpan left, SourceSpan right) {
        final SourcePos begin = SourcePos.of(
                Math.min(left.begin.getLine(), right.begin.getLine()),
                Math.min(left.begin.getCol(), right.begin.getCol())
        );
        final SourcePos end = SourcePos.of(
                Math.max(left.end.getLine(), right.end.getLine()),
                Math.max(left.end.getCol(), right.end.getCol())
        );
        return SourceSpan.of(begin, end);
    }

    public SourceSpan copy() {
        return SourceSpan.of(begin.copy(), end.copy());
    }
}
