package net.quepierts.animata4j.core.dsl.lexer;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.quepierts.animata4j.core.dsl.source.SourcePointer;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceProvider;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import net.quepierts.animata4j.core.dsl.TrieTree;
import net.quepierts.animata4j.core.dsl.exception.LexerException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Lexer implements TokenProvider {

    @Getter
    private final SourceProvider source;

    @Getter(AccessLevel.PROTECTED)
    private final SourcePointer pointer;

    @Getter @Setter
    private boolean keepComment = true;

    protected Lexer(@NotNull String source) {
        this.source = SourceProvider.of(source);
        this.pointer = SourcePointer.of(this.source);
    }

    protected Lexer(@NotNull SourceProvider provider) {
        this.source = provider;
        this.pointer = SourcePointer.of(this.source);
    }

    public abstract @Nullable Token next();

    public List<Token> tokenize() {
        ImmutableList.Builder<Token> builder = ImmutableList.builder();
        while (this.hasNext()) {
            Token token = this.next();
            if (token != null) {
                if (token.is(TokenType.EOF)) {
                    break;
                }
                builder.add(token);
            }
        }
        return builder.build();
    }

    public @NotNull SourcePos getSourcePos() {
        return this.pointer.toSourcePos();
    }

    public @NotNull SourceSpan span(@NotNull SourcePos start) {
        return SourceSpan.of(start, this.getSourcePos());
    }

    public boolean hasNext() {
        return this.pointer.hasNext();
    }

    @Contract(pure = true)
    protected final char peek() {
        return this.pointer.peek();
    }

    protected final char advance() {
        return this.pointer.advance();
    }

    protected final void skipWhitespace() {
        this.pointer.skipWhitespace();
    }

    protected final void error(
            final @NotNull String message,
            final @NotNull SourcePos pos
    ) {
        final String line = this.source.getLine(pos.getLine());
        throw new LexerException(message, pos, line);
    }

    protected final void error(final @NotNull String message) {
        throw new LexerException(message, this.pointer);
    }

    protected final String readUntil(char c) {
        StringBuilder builder = new StringBuilder();
        int start = this.pointer.getPos();
        while (this.hasNext() && this.peek() != c) {
            builder.append(this.advance());
        }
        return builder.toString();
    }

    protected final String readWhile(@NotNull final CharPredicate predicate) {
        StringBuilder builder = new StringBuilder();
        this.readWhile(predicate, builder);
        return builder.toString();
    }

    protected final void readWhile(
            @NotNull final CharPredicate predicate,
            @NotNull final StringBuilder builder
    ) {
        while (this.hasNext() && predicate.test(this.peek())) {
            builder.append(this.advance());
        }
    }

    protected final String source() {
        return this.source.getSource();
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

    protected static boolean isDecimalStart(char c) {
        return Character.isDigit(c) || c == '.';
    }

    /* SYMBOLS ::= $(
        LPAREN | RPAREN |
        LBRACE | RBRACE |
        LBRACKET | RBRACKET |
        SEMICOLON | COMMA | DOT
        COLON | QUESTION
       )^
    */
    protected static TokenType symbol(char c) {
        switch (c) {
            case '(':
                return TokenType.LPAREN;
            case ')':
                return TokenType.RPAREN;
            case '{':
                return TokenType.LBRACE;
            case '}':
                return TokenType.RBRACE;
            case '[':
                return TokenType.LBRACKET;
            case ']':
                return TokenType.RBRACKET;
            case ';':
                return TokenType.SEMICOLON;
            case ',':
                return TokenType.COMMA;
            case '.':
                return TokenType.DOT;
            case ':':
                return TokenType.COLON;
            case '?':
                return TokenType.QUESTION;
            default:
                return TokenType.UNDEFINED;
        }
    }

    protected Token eof() {
        return Token.eof(this.span(this.getSourcePos()));
    }

    protected @Nullable Token tryMatch(
            final @NotNull SourcePos start,
            final @NotNull TrieTree<TokenType> patterns
    ) {
        StringBuilder builder = new StringBuilder();

        TokenType type = TokenType.UNDEFINED;
        SourcePos pos = start;

        char c = this.advance();
        builder.append(c);
        int index = patterns.find(c, 0);
        while (index != -1) {
            final TokenType t = patterns.get(index);
            pos = this.getSourcePos();

            type = t;
            char peek = this.peek();

            if (LexerHelper.isBlank(peek)) {
                break;
            }

            index = patterns.find(peek, index);

            if (index > -1) {
                builder.append(peek);
                this.advance();
            }
        }

        return type == TokenType.UNDEFINED ?
                null :
                new Token(
                        type,
                        builder.toString(),
                        SourceSpan.of(start, pos)
                );
    }

    @FunctionalInterface
    protected interface CharPredicate {
        boolean test(char c);
    }

    @FunctionalInterface
    protected interface LexerFactory<T extends Lexer> {
        T create(
                @NotNull SourceProvider source,
                @NotNull SourcePos pos,
                int length
        );
    }
}
