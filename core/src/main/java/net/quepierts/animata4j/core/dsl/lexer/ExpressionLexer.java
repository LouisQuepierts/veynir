package net.quepierts.animata4j.core.dsl.lexer;

import net.quepierts.animata4j.core.dsl.CharMask;
import net.quepierts.animata4j.core.dsl.SourcePos;
import net.quepierts.animata4j.core.dsl.token.ExpressionToken;
import net.quepierts.animata4j.core.dsl.token.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExpressionLexer extends Lexer {

    private static final CharMask SYMBOLS = CharMask.compile("+-*/%^()");

    public ExpressionLexer(final @NotNull String source) {
        super(source);
    }

    public ExpressionLexer(
            final @NotNull String source,
            final @NotNull SourcePos pos,
            final int length
    ) {
        super(source, pos, length);
    }

    @Override
    public @Nullable Token next() {
        this.skipWhitespace();
        if (!this.hasNext()) {
            return this.eof();
        }

        char c = this.advance();

        final SourcePos leftPos = this.getSourcePos();
        if (SYMBOLS.matches(c)) {
            return ExpressionToken.symbol(this.span(leftPos), c);
        }

        if (Lexer.isDigit(c)) {
            final String number = this.readWhile(Lexer::isDigit);
            return ExpressionToken.number(this.span(leftPos), number);
        }

        if (Lexer.isIdentifierStart(c)) {
            final String identifier = this.readWhile(Lexer::isIdentifierPart);
            return ExpressionToken.word(this.span(leftPos), identifier);
        }

        this.error("Invalid character: " + c);
        return null; // unreachable
    }
}
