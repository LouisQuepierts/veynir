package net.quepierts.animata4j.core.dsl.parser;

import lombok.AccessLevel;
import lombok.Getter;
import net.quepierts.animata4j.core.dsl.exception.ParserException;
import net.quepierts.animata4j.core.dsl.lexer.TokenProvider;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@Getter(AccessLevel.PROTECTED)
public abstract class Parser {

    private final TokenProvider lexer;

    @Nullable
    private final Parser parent;
    private Token current;

    protected Parser(final @NotNull TokenProvider lexer) {
        this.lexer = lexer;
        this.parent = null;
        this.advance();
    }

    protected Parser(final @NotNull Parser other) {
        this.lexer = other.lexer;
        this.parent = other.parent != null ? other.parent : other;
    }

    public abstract Node parse();

    protected void advance() {
//        System.out.println("Advance");
        if (this.parent != null) {
            this.parent.advance();
            this.current = this.parent.getCurrent();
        } else {
            this.current = this.lexer.next();
        }
    }

    protected boolean hasNext() {
        return this.lexer.hasNext();
    }

    protected Token getCurrent() {
        return this.parent != null ? this.parent.getCurrent() : this.current;
    }

    protected boolean match(final @NotNull TokenType type) {
        final Token current = this.getCurrent();
        if (current != null && current.is(type)) {
            this.advance();
            return true;
        }
        return false;
    }

    protected boolean is(final @NotNull TokenType type) {
        final Token current = this.getCurrent();
        return current != null && current.is(type);
    }

    protected void consume(final @NotNull TokenType type) {
        if (!this.match(type)) {
            this.errorExpected(type.toString(), this.getCurrent().getType().name());
        }
    }

    protected void consume(final @NotNull Predicate<Token> predicate, final String error) {
        if (!predicate.test(this.getCurrent())) {
            this.error(error, this.getCurrent());
        }
        this.advance();
    }

    protected Token expect(final @NotNull TokenType type) {
        final Token token = this.getCurrent();
        this.consume(type);
        return token;
    }

    protected Token expect(final @NotNull Predicate<Token> predicate, final String error) {
        final Token token = this.getCurrent();
        this.consume(predicate, error);
        return token;
    }

    protected void error(String message, Token token) {
        throw new ParserException(message, token, this.getLexer().getSource());
    }

    protected void error(String message) {
        this.error(message, this.getCurrent());
    }

    protected void errorExpected(String expected, String actual) {
        error("Expected " + expected + " but found '" + actual + "'");
    }
}
