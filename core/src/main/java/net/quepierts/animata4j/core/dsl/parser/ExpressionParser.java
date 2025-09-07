package net.quepierts.animata4j.core.dsl.parser;

import net.quepierts.animata4j.core.dsl.ast.expr.*;
import net.quepierts.animata4j.core.dsl.lexer.*;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser extends Parser {

    public ExpressionParser(final @NotNull TokenProvider lexer) {
        super(lexer);
    }

    public ExpressionParser(final @NotNull String source) {
        super(new GeneralLexer(source));
    }

    public @NotNull Expression parse() {
        return this.parseExpression();
    }

    /*
    <expression> ::= <term> [ (PLUS | MINUS) <term> ]*
    * */
    private Expression parseExpression() {
//        System.out.println("Expression");
        Expression node = this.parseTerm();
        Token current = this.getCurrent();
        while (current.is(TokenType.PLUS, TokenType.MINUS)) {
            this.advance();

            Expression right = this.parseTerm();
            node = new BinaryExpr(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    current.getType()
            );
            current = this.getCurrent();
        }

        return node;
    }

    /*
    <term> ::= <unary> [ (STAR | SLASH | PERCENT) <unary> ]*
    */
    private Expression parseTerm() {
//        System.out.println("Term");
        Expression node = this.parseUnary();
        Token current = this.getCurrent();

        while (current.is(TokenType.STAR, TokenType.SLASH, TokenType.PERCENT)) {
            this.advance();

            Expression right = this.parseUnary();
            node = new BinaryExpr(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    current.getType()
            );
            current = this.getCurrent();
        }
        return node;
    }

    /*
    <unary> ::= [ (PLUS | MINUS) ] <primary> | <primary>
     */
    private Expression parseUnary() {
//        System.out.println("Unary");
        Token current = this.getCurrent();
        if (current.is(TokenType.PLUS, TokenType.MINUS)) {
            this.advance();

            Expression node = this.parsePrimary();
            return new UnaryExpr(
                    SourceSpan.of(current.getSpan().getBegin(), node.getSpan().getEnd()),
                    node,
                    current.getType()
            );
        } else {
            return this.parsePrimary();
        }
    }
    
    /*
    <reference> ::= IDENTIFIER ( LPAREN <args> RPAREN )?
    <args> ::= <expression> ( COMMA <expression> )*
    */
    private Expression parseReference() {
        final Token current = this.getCurrent();
        this.expect(TokenType.IDENTIFIER);

        if (!this.match(TokenType.LPAREN)) {
            return new IdentifierExpr(
                    current.getSpan(),
                    current.getValue()
            );
        }

        final List<Expression> arguments = new ArrayList<>();
        while (!this.match(TokenType.RPAREN)) {
            arguments.add(this.parseExpression());
            if (!this.match(TokenType.COMMA)) {
                break;
            }
        }

        return new CallExpr(
                SourceSpan.of(current.getSpan(), this.getCurrent().getSpan()),
                current.getValue(),
                arguments
        );
    }

    /*
    <primary> ::= NUMBER
                  | <reference>
                  | LPAREN <expression> RPAREN
    */
    private @NotNull Expression parsePrimary() {
//        System.out.println("Primary");
        Token current = this.getCurrent();

        switch (current.getType()) {
            case LITERAL_INTEGER:
                this.advance();
                return LiteralIntegerExpr.dec(current.getSpan(), current.getValue());
            case LITERAL_HEX:
                this.advance();
                return LiteralIntegerExpr.hex(current.getSpan(), current.getValue());
            case LITERAL_OCT:
                this.advance();
                return LiteralIntegerExpr.oct(current.getSpan(), current.getValue());
            case LPAREN:
                this.advance();
                Expression node = this.parseExpression();
                this.expect(TokenType.RPAREN);
                return node;
            case IDENTIFIER:
                return this.parseReference();
        }

        this.error("Unexpected token " + current.getType());
        throw new RuntimeException(); // unreachable
    }
}
