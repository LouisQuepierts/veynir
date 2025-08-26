package net.quepierts.animata4j.core.dsl.token;

import net.quepierts.animata4j.core.dsl.SourcePos;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeKey;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import org.jetbrains.annotations.NotNull;

public final class EOFToken extends Token {

    EOFToken(@NotNull SourceSpan span) {
        super(span);
    }

    public static EOFToken of(@NotNull SourcePos pos) {
        return new EOFToken(SourceSpan.of(pos, pos));
    }

    @Override
    public TypeIdentifier<EOFToken> getType() {
        return TokenTypes.EOF;
    }

    public enum Keys implements TypeKey {
        EOF
    }
}
