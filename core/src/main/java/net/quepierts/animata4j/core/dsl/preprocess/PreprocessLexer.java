package net.quepierts.animata4j.core.dsl.preprocess;

import net.quepierts.animata4j.core.dsl.lexer.Lexer;
import net.quepierts.animata4j.core.dsl.lexer.LexerHelper;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PreprocessLexer extends Lexer {

    public PreprocessLexer(@NotNull SourceProvider provider) {
        super(provider);
    }

    @Override
    public @Nullable Token next() {
        this.skipWhitespace();
        if (!this.hasNext()) {
            return this.eof();
        }

        final SourcePos leftPos = this.getSourcePos();
        char c = this.peek();

        Token token = this.tryMatch(leftPos, TokenType.SYMBOLS);
        if (token != null) {
            return token;
        }

        boolean isDirective = c == '#';

        StringBuilder identifier = new StringBuilder()
                .append(c);
        this.readWhile(LexerHelper::isIdentifierPart, identifier);
        return new Token(
                isDirective ? TokenType.DIRECTIVE : TokenType.IDENTIFIER,
                identifier.toString(),
                this.span(leftPos)
        );
    }
}
