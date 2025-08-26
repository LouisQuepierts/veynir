package net.quepierts.animata4j.core.dsl.lexer;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourcePos;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.token.EOFToken;
import net.quepierts.animata4j.core.dsl.token.Token;
import net.quepierts.animata4j.core.dsl.token.TokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
public abstract class Lexer {

    private final String source;
    private final int end;

    private int col;
    private int line;
    private int pos;

    protected Lexer(@NotNull String source) {
        this.source = source;
        this.end = source.length();
        this.pos = 0;
    }

    protected Lexer(
            final @NotNull String source,
            final @NotNull SourcePos pos,
            final int length
    ) {
        this.source = source;
        this.end = pos.getPos() + length;
        this.pos = pos.getPos();
        this.col = pos.getCol();
        this.line = pos.getLine();
    }

    public abstract @Nullable Token next();

    protected @NotNull RuntimeException createException(String message, int errorPos) {
        return new RuntimeException(message);
    }

    public List<Token> tokenize() {
        ImmutableList.Builder<Token> builder = ImmutableList.builder();
        while (this.hasNext()) {
            Token token = this.next();
            if (token != null) {
                if (token.getType() == TokenTypes.EOF) {
                    break;
                }
                builder.add(token);
            }
        }
        return builder.build();
    }

    public @NotNull SourcePos getSourcePos() {
        return new SourcePos(this.line, this.col, this.pos);
    }

    public @NotNull SourceSpan span(@NotNull SourcePos start) {
        return SourceSpan.of(start, this.getSourcePos());
    }

    public boolean hasNext() {
        return this.pos < this.end;
    }

    protected char peek() {
        return this.pos < this.end ? this.source.charAt(this.pos) : '\0';
    }

    protected char advance() {
        if (this.pos >= this.end) {
            return '\0';
        }
        final char c = this.source.charAt(this.pos++);
        if (c == '\n') {
            this.line ++;
            this.col = 1;
        } else {
            this.col ++;
        }
        return c;
    }

    protected void skipWhitespace() {
        while (this.hasNext() && Character.isWhitespace(this.peek())) {
            this.pos++;
        }
    }

    protected void error(final @NotNull String message) {
        int lineStart = this.pos;
        while (lineStart > 0 && this.source.charAt(lineStart - 1) != '\n') {
            lineStart --;
        }
        int lineEnd = this.pos;
        while (lineEnd < this.source.length() && this.source.charAt(lineEnd) != '\n') {
            lineEnd ++;
        }

        String lineContent = this.source.substring(lineStart, lineEnd);

        StringBuilder builder = new StringBuilder()
                .append("Error at line ").append(this.line)
                .append(": ").append(message).append("\n")
                .append(lineContent).append("\n");

        int offset = this.pos - lineStart;
        for (int i = 0; i < offset; i ++) {
            builder.append(" ");
        }
        builder.append("^");
        throw new RuntimeException(builder.toString());
    }

    protected String readUntil(char c) {
        int start = this.pos;
        while (this.hasNext() && this.peek() != c) {
            this.advance();
        }
        return this.source.substring(start, this.pos);
    }

    protected String readWhile(@NotNull final CharPredicate predicate) {
        int start = this.pos;
        while (this.hasNext() && predicate.test(this.peek())) {
            this.advance();
        }
        return this.source.substring(start, this.pos);
    }

    protected <T extends Lexer> T sublexer(
            @NotNull LexerFactory<T> factory,
            int length
    ) {
        return factory.create(this.source, this.getSourcePos(), length);
    }

    protected static boolean isIdentifierStart(char c) {
        return Character.isJavaIdentifierStart(c) || c == '_';
    }

    protected static boolean isIdentifierPart(char c) {
        return Character.isJavaIdentifierPart(c) || c == '_';
    }

    protected static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    protected Token eof() {
        return EOFToken.of(this.getSourcePos());
    }

    @FunctionalInterface
    protected interface CharPredicate {
        boolean test(char c);
    }

    @FunctionalInterface
    protected interface LexerFactory<T extends Lexer> {
        T create(
                @NotNull String source,
                @NotNull SourcePos pos,
                int length
        );
    }
}
