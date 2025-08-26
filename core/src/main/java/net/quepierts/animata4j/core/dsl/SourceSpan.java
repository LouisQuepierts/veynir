package net.quepierts.animata4j.core.dsl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class SourceSpan {
    private final SourcePos begin;
    private final SourcePos end;
}
