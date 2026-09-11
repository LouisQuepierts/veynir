package net.quepierts.veynir.dsl.parser;

import lombok.AccessLevel;
import lombok.Getter;
import net.quepierts.veynir.dsl.Primitive;
import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.ast.type.PrimitiveType;
import net.quepierts.veynir.dsl.ast.type.StructType;
import net.quepierts.veynir.dsl.ast.type.Type;
import net.quepierts.veynir.dsl.ast.type.VoidType;
import net.quepierts.veynir.dsl.exception.ParserException;
import net.quepierts.veynir.dsl.lexer.Token;
import net.quepierts.veynir.dsl.lexer.TokenProvider;
import net.quepierts.veynir.dsl.lexer.TokenType;
import net.quepierts.veynir.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Getter(AccessLevel.PROTECTED)
public abstract class Parser {

    private final TokenProvider lexer;

    @Nullable
    private final Parser parent;
    private Token current;

    protected final Set<String> types;

    protected Parser(final @NotNull TokenProvider lexer) {
        this.lexer = lexer;
        this.parent = null;
        this.advance();
        this.types = new HashSet<>();
    }

    protected Parser(final @NotNull Parser other) {
        this.lexer = other.lexer;
        this.parent = other.parent != null ? other.parent : other;
        this.types = other.types;
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

    protected boolean isDatatype(Token token) {
        final TokenType type = token.getType();
        return type.isPrimitiveType() || types.contains(token.getValue());
    }


    protected @NotNull Type parseType() {
        Token current = this.getCurrent();
        this.advance();
        return parseType(current);
    }

    protected Type parseType(Token token) {
        final TokenType type = token.getType();
        final SourceSpan begin = token.getSpan();
        if (type.isPrimitiveType()) {
            Primitive primitive = Primitive.fromToken(type);
            return new PrimitiveType(begin, primitive);
        } else if (type == TokenType.TYPE_VOID) {
            return new VoidType(begin);
        } else if (type == TokenType.IDENTIFIER) {
            return new StructType(begin, token.getValue());
        }

        this.error("Unexpected token");
        throw new RuntimeException();
    }
}
