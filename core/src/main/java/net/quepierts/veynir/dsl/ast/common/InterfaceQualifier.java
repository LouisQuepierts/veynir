package net.quepierts.veynir.dsl.ast.common;

import net.quepierts.veynir.dsl.lexer.TokenType;

public enum InterfaceQualifier {
    NONE,
    IN,
    OUT,
    INOUT,
    UNIFORM,
    BUFFER;

    public static InterfaceQualifier of(final TokenType type) {
        switch (type) {
            case KEYWORD_IN:
                return IN;
            case KEYWORD_OUT:
                return OUT;
            case KEYWORD_INOUT:
                return INOUT;
            case KEYWORD_UNIFORM:
                return UNIFORM;
            default:
                throw new IllegalArgumentException("Invalid token type: " + type);
        }
    }
}
