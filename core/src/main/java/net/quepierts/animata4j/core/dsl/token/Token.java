package net.quepierts.animata4j.core.dsl.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Token {
    private final SourceSpan span;

    public abstract TypeIdentifier<? extends Token> getType();

    public boolean is(@NotNull TypeIdentifier<?> type) {
        return this.getType() == type;
    }
}
