package net.quepierts.animata4j.dsl.lexer;

import net.quepierts.animata4j.dsl.source.SourceProvider;
import net.quepierts.animata4j.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArlLexer extends Lexer {

    public ArlLexer(@NotNull String source) {
        super(source);
    }

    public ArlLexer(@NotNull SourceProvider source) {
        super(source);
    }

    @Override
    public @Nullable Token next() {
        this.skipWhitespace();
        if (!this.hasNext()) {
            return this.eof();
        }

        final SourcePos leftPos = this.getSourcePos();
        char c = this.peek();

        if (c == '"') {
            return this.string(leftPos);
        }

        if (LexerHelper.isNumberStart(c)) {
            final Token number = this.number(leftPos);
            if (number != null) {
                return number;
            }
        }

        final TokenType symbol = Lexer.symbol(c);
        if (symbol != TokenType.UNDEFINED) {
            this.advance();
            return new Token(
                    symbol,
                    String.valueOf(c),
                    this.span(leftPos)
            );
        }

        final Token operator = this.tryMatch(leftPos, TokenType.SYMBOLS);
        if (operator != null) {
            return operator;
        }

        if (LexerHelper.isIdentifierStart(c)) {
            StringBuilder identifier = new StringBuilder()
                    .append(c);
            this.readWhile(LexerHelper::isIdentifierPart, identifier);
            String str = identifier.toString();
            return new Token(
                    TokenType.KEYWORDS.getOrDefault(str, TokenType.IDENTIFIER),
                    str,
                    this.span(leftPos)
            );
        }

        this.error("Unexpected character: " + c + ", char code: " + (int) c, leftPos);
        return null;
    }

    private Token string(SourcePos leftPos) {
        StringBuilder builder = new StringBuilder()
                .append(this.peek());

        this.advance();
        while (this.hasNext()) {
            char c = this.advance();
            builder.append(c);

            if (c == '"') {
                return new Token(
                        TokenType.LITERAL_STRING,
                        builder.toString(),
                        this.span(leftPos)
                );
            }
        }

        this.error("Unterminated string literal", leftPos);
        throw new RuntimeException(); // unreachable
    }

    private @Nullable Token number(SourcePos leftPos) {
        StringBuilder builder = new StringBuilder();

        if (this.peek() == '0') { // hex or oct
            this.advance();
            builder.append('0');
            final char second = this.peek();
            if (second == 'x' || second == 'X') { // hex;
                builder.append(second);
                this.readWhile(LexerHelper::isHexDigit, builder);
                return new Token(
                        TokenType.LITERAL_HEX,
                        builder.toString(),
                        this.span(leftPos)
                );
            } else if (LexerHelper.isOctDigit(second)) { // oct
                builder.append(second);
                this.readWhile(LexerHelper::isOctDigit, builder);
                return new Token(
                        TokenType.LITERAL_OCT,
                        builder.toString(),
                        this.span(leftPos)
                );
            }
        }

        this.readWhile(LexerHelper::isDigit, builder);

        boolean isDecimal = false;

        if (this.peek() == '.') {
            isDecimal = true;
            builder.append('.');
            this.advance();
            this.readWhile(LexerHelper::isDigit, builder);
        }

        char affixE = this.peek();
        if (affixE == 'e' || affixE == 'E') {
            isDecimal = true;
            builder.append(affixE);
            char symbol = this.advance();
            if (symbol == '+' || symbol == '-') {
                builder.append(symbol);
                this.advance();
            }
            this.readWhile(LexerHelper::isDigit, builder);
        }

        // check -f -F subfix
        char subfixF = this.peek();
        if (subfixF == 'f' || subfixF == 'F') {
            isDecimal = true;
            builder.append('f');
            this.advance();
        }

        final String content = builder.toString();
        if (content.isBlank()) {
            return null;
        }

        return new Token(
                isDecimal ? TokenType.LITERAL_DECIMAL : TokenType.LITERAL_INTEGER,
                builder.toString(),
                this.span(leftPos)
        );
    }
}
