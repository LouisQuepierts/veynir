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

    public ExpressionParser(Parser parent) {
        super(parent);
    }

    public @NotNull Expression parse() {
        try {
            return this.parseExpression();
        } catch (Exception e) {
//            if (!(e instanceof CompileException)) {
//                this.error(e.getMessage());
//            }
            throw e;
        }
    }

    /*
    <expression> ::= <assignment>
    * */
    private @NotNull Expression parseExpression() {
        return this.parseAssignment();
    }

    /*
    <assignment> ::= <lvalue> EQUAL <assignment>
        | <ternary>
     */
    private @NotNull Expression parseAssignment() {
//        System.out.println("Assignment");
        Expression node = this.parseTernary();
        TokenType type = this.getCurrent().getType();
        if (type == TokenType.EQUAL
                || type == TokenType.PLUS_EQUAL
                || type == TokenType.MINUS_EQUAL
                || type == TokenType.STAR_EQUAL
                || type == TokenType.SLASH_EQUAL) {
            this.advance();

            Expression right = this.parseAssignment();
            return AssignExpr.of(
                    node, right,
                    type
            );
        }

        return node;
    }

    /*
    <ternary> ::= <logical-or> ( QUESTION <assignment> COLON <ternary> )?
    */
    private @NotNull Expression parseTernary() {
        Expression condition = this.parseLogicalOr();

        if (this.match(TokenType.QUESTION)) {
            Expression thenExpr = this.parseAssignment();

            this.consume(TokenType.COLON);

            Expression elseExpr = this.parseTernary();
            return new TernaryExpr(
                    SourceSpan.of(condition.getSpan().getBegin(), elseExpr.getSpan().getEnd()),
                    condition, thenExpr, elseExpr
            );
        }

        return condition;
    }

    /*
    <logical-or> ::= <logical-xor> ( LOGIC_OR <logical-xor> )*
    */
    private @NotNull Expression parseLogicalOr() {
        Expression node = this.parseLogicalXor();

        while (this.match(TokenType.OR2)) {
            Expression right = this.parseLogicalXor();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.LOGIC_OR
            );

        }

        return node;
    }

    /*
    <logical-xor> ::= <logical-and> ( LOGIC_XOR <logical-and> )*
     */
    private @NotNull Expression parseLogicalXor() {
        Expression node = this.parseLogicalAnd();

        while (this.match(TokenType.XOR2)) {
            Expression right = this.parseLogicalAnd();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.LOGIC_XOR
            );

        }

        return node;
    }

    /*
    <logical-and> ::= <equality> ( LOGIC_AND <equality> )*
    */
    private @NotNull Expression parseLogicalAnd() {
        Expression node = this.parseBitOr();

        while (this.match(TokenType.AND2)) {
            Expression right = this.parseBitOr();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.LOGIC_AND
            );
        }

        return node;
    }

    /*
    <bit-or> ::= <bit-xor> ( BIT_OR <bit-xor> )*
     */
    private @NotNull Expression parseBitOr() {
        Expression node = this.parseBitXor();

        while (this.match(TokenType.OR)) {
            Expression right = this.parseBitXor();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.BIT_OR
            );

        }
        return node;
    }

    /*
    <bit-xor> ::= <bit-and> ( BIT_XOR <bit-and> )*
     */
    private @NotNull Expression parseBitXor() {
        Expression node = this.parseBitAnd();

        while (this.match(TokenType.XOR)) {
            Expression right = this.parseBitAnd();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.BIT_XOR
            );
        }

        return node;
    }

    /*
    <bit-and> ::= <equality> ( BIT_AND <equality> )*
     */
    private @NotNull Expression parseBitAnd() {
        Expression node = this.parseEquality();

        while (this.match(TokenType.AND)) {
            Expression right = this.parseBitAnd();
            node = BinaryExpr.of(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    BinaryExpr.Operator.BIT_AND
            );
        }

        return node;
    }

    /*
    <equality> ::= <comparison> ( (EQEQ | NOTEQ) <comparison> )*
    */
    private @NotNull Expression parseEquality() {
        Expression node = this.parseComparison();
        TokenType type = this.getCurrent().getType();

        while (type == TokenType.EQEQ || type == TokenType.NOTEQ) {

            this.advance();

            Expression right = this.parseComparison();
            node = BinaryExpr.logic(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    type
            );

            type = this.getCurrent().getType();
        }
        return node;
    }

    /*
    <comparison> ::= <shift> ( [LT | GT | LTEQ | GTEQ] <shift> )?
     */
    private @NotNull Expression parseComparison() {
        Expression node = this.parseShift();
        TokenType type = this.getCurrent().getType();

        if (type == TokenType.LT
                || type == TokenType.GT
                || type == TokenType.LTEQ
                || type == TokenType.GTEQ) {
            this.advance();

            Expression right = this.parseShift();
            return BinaryExpr.logic(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    type
            );
        }

        return node;
    }

    /*
    <shift> ::= <additive> ( [LSHIFT | RSHIFT] <additive> )*
     */
    private @NotNull Expression parseShift() {
        Expression node = this.parseAdditive();
        TokenType type = this.getCurrent().getType();

        while (type == TokenType.LSHIFT || type == TokenType.RSHIFT) {
            this.advance();

            Expression right = this.parseAdditive();
            node = BinaryExpr.calculate(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    type
            );

            type = this.getCurrent().getType();
        }

        return node;
    }

    /*
    <additive> ::= <multiplicative> ( [PLUS | MINUS] <multiplicative> )*
     */
    private @NotNull Expression parseAdditive() {
        Expression node = this.parseMultiplicative();
        TokenType type = this.getCurrent().getType();

        while (type == TokenType.PLUS || type == TokenType.MINUS) {
            this.advance();

            Expression right = this.parseMultiplicative();
            node = BinaryExpr.calculate(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    type
            );

            type = this.getCurrent().getType();
        }

        return node;
    }

    /*
    <multiplicative> ::= <unary> ( [STAR | SLASH | PERCENT] <unary> )*
    */
    private @NotNull Expression parseMultiplicative() {
//        System.out.println("Term");
        Expression node = this.parseUnary();
        Token current = this.getCurrent();

        while (current.is(TokenType.STAR, TokenType.SLASH, TokenType.PERCENT)) {
            this.advance();

            Expression right = this.parseUnary();
            node = BinaryExpr.calculate(
                    SourceSpan.of(node.getSpan().getBegin(), right.getSpan().getEnd()),
                    node, right,
                    current.getType()
            );
            current = this.getCurrent();
        }
        return node;
    }

    /*
    <unary> ::= ( [PLUS | PLUS2 | MINUS | MINUS2 | NOT | BIT_NOT] <unary> )
        | <postfix>
     */
    private @NotNull Expression parseUnary() {
//        System.out.println("Unary");
        Token current = this.getCurrent();
        TokenType type = current.getType();
        if (type == TokenType.PLUS || type == TokenType.PLUS2
                || type == TokenType.MINUS || type == TokenType.MINUS2
                || type == TokenType.NOT || type == TokenType.WAVE) {
            this.advance();

            Expression node = this.parseUnary();
            return UnaryExpr.prefix(
                    SourceSpan.of(current.getSpan().getBegin(), node.getSpan().getEnd()),
                    node,
                    type
            );
        } else {
            return this.parsePostfix();
        }
    }

    /*
    <postfix> ::= <primary> ( <postfix-suffix> )*

    <postfix-suffix> ::= PLUS2 | MINUS2
        | DOT IDENTIFIER
        | LBRACKET <expression> RBRACKET
     */
    private @NotNull Expression parsePostfix() {
        Expression node = this.parsePrimary();
        boolean flag = true;
        while (flag && this.hasNext()) {
            Token current = this.getCurrent();
            switch (current.getType()) {
                case PLUS2: {
                    node = UnaryExpr.postfix(
                            SourceSpan.of(node.getSpan().getBegin(), current.getSpan().getEnd()),
                            node,
                            TokenType.PLUS2
                    );
                    this.advance();
                    break;
                }
                case MINUS2: {
                    node = UnaryExpr.postfix(
                            SourceSpan.of(node.getSpan().getBegin(), current.getSpan().getEnd()),
                            node,
                            TokenType.MINUS2
                    );
                    this.advance();
                    break;
                }
                case DOT: {
                    this.advance();
                    IdentifierExpr identifier = this.parseIdentifier();
                    node = new MemberAccessExpr(
                            SourceSpan.of(node.getSpan().getBegin(), identifier.getSpan().getEnd()),
                            node,
                            identifier
                    );
                    break;
                }
                case LBRACKET: {
                    this.advance();
                    Expression expr = this.parseExpression();
                    this.consume(TokenType.RBRACKET);
                    node = new ArrayAccessExpr(
                            SourceSpan.of(node.getSpan().getBegin(), expr.getSpan().getEnd()),
                            node,
                            expr
                    );
                    break;
                }
                default:
                    flag = false;
            }
        }

        return node;
    }

    /*
    <primary> ::= <identifier> <function-suffix>?
                        | <number>
                        | <string>
                        | "(" <expression> ")"
    */
    private @NotNull Expression parsePrimary() {
//        System.out.println("Primary");
        Token current = this.getCurrent();

        switch (current.getType()) {
            case IDENTIFIER: {
                IdentifierExpr identifier = this.parseIdentifier();

                if (this.match(TokenType.LPAREN)) {
                    return this.parseFunction(identifier.getValue(), identifier.getSpan());
                } else {
                    return identifier;
                }
            }
            case TYPE_INT:
            case TYPE_FLOAT:
            case TYPE_BOOL: { // constructors
                final String name = this.getCurrent().getValue();
                final SourceSpan begin = this.getCurrent().getSpan();
                this.advance();
                this.consume(TokenType.LPAREN);
                return this.parseFunction(name, begin);
            }
            case LITERAL_INTEGER: {
                this.advance();
                return LiteralIntegerExpr.dec(current.getSpan(), current.getValue());
            }
            case LITERAL_HEX: {
                this.advance();
                return LiteralIntegerExpr.hex(current.getSpan(), current.getValue());
            }
            case LITERAL_OCT: {
                this.advance();
                return LiteralIntegerExpr.oct(current.getSpan(), current.getValue());
            }
            case LITERAL_DECIMAL: {
                this.advance();
                return LiteralDecimalExpr.of(current.getSpan(), current.getValue());
            }
            case LITERAL_STRING: {
                this.advance();
                return LiteralStringExpr.of(current.getSpan(), current.getValue());
            }
            case LITERAL_TRUE: {
                this.advance();
                return LiteralBooleanExpr.ofTrue(current.getSpan());
            }
            case LITERAL_FALSE: {
                this.advance();
                return LiteralBooleanExpr.ofFalse(current.getSpan());
            }
            case LPAREN: {
                this.advance();
                Expression node = this.parseExpression();
                this.consume(TokenType.RPAREN);
                return node;
            }
        }

        this.error("Unexpected token " + current.getType());
        throw new RuntimeException(); // unreachable
    }

    /*
    <function-suffix> ::= LPAREN <expression> ( COMMA <expression> )* RPAREN
     */
    private @NotNull Expression parseFunction(final String name, final SourceSpan begin) {
        List<Expression> args = new ArrayList<>();

        while (!this.is(TokenType.RPAREN)) {
            args.add(this.parseExpression());

            if (!this.match(TokenType.COMMA)) {
                break;
            }
        }

        this.consume(TokenType.RPAREN);

        return new CallExpr(
                SourceSpan.of(begin, this.getCurrent().getSpan()),
                name,
                args
        );
    }

    /*
    <identifier> ::= IDENTIFIER
     */
    private @NotNull IdentifierExpr parseIdentifier() {
        final Token current = this.getCurrent();
        this.consume(TokenType.IDENTIFIER);
        return new IdentifierExpr(
                current.getSpan(),
                current.getValue()
        );
    }
}
