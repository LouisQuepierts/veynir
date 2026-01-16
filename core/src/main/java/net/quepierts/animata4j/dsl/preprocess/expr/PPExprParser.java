package net.quepierts.animata4j.dsl.preprocess.expr;

import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.lexer.ArlLexer;
import net.quepierts.animata4j.dsl.lexer.Lexer;
import net.quepierts.animata4j.dsl.lexer.Token;
import net.quepierts.animata4j.dsl.lexer.TokenType;
import net.quepierts.animata4j.dsl.parser.Parser;
import org.jetbrains.annotations.NotNull;

public final class PPExprParser extends Parser {

    public static PPExpr parse(@NotNull String origin) {
        if (origin.isBlank()) {
            throw new IllegalArgumentException("Source cannot be blank.");
        }

        return new PPExprParser(new ArlLexer(origin)).parsePPExpr();
    }

    public PPExprParser(Lexer lexer) {
        super(lexer);
    }

    public Node parse() {
        throw new UnsupportedOperationException();
    }

    public PPExpr parsePPExpr() {
        return this.parseOr();
    }

    private PPExpr parseOr() {
        PPExpr left = parseAnd();

        while (this.match(TokenType.OR2)) {
            PPExpr right = this.parseAnd();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.OR
            );
        }

        return left;
    }

    private PPExpr parseAnd() {
        PPExpr left = parseBitOr();

        while (this.match(TokenType.AND2)) {
            PPExpr right = this.parseBitOr();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.AND
            );
        }

        return left;
    }

    private PPExpr parseBitOr() {
        PPExpr left = parseBitAnd();

        while (this.match(TokenType.OR)) {
            PPExpr right = this.parseBitAnd();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.BOR
            );
        }

        return left;
    }

    private PPExpr parseBitAnd() {
        PPExpr left = parseEquality();

        while (this.match(TokenType.AND)) {
            PPExpr right = this.parseEquality();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.BAND
            );
        }

        return left;
    }

    private PPExpr parseEquality() {
        PPExpr left = parseComparison();
        TokenType type = this.getCurrent().getType();

        while (type == TokenType.EQEQ || type == TokenType.NOTEQ) {
            this.advance();
            PPExpr right = this.parseComparison();
            left = new PPBinaryExpr(
                    left, right,
                    type == TokenType.EQEQ ?
                            PPBinaryExpr.Operator.EQ :
                            PPBinaryExpr.Operator.NE
            );
            type = this.getCurrent().getType();
        }

        return left;
    }

    private PPExpr parseComparison() {
        PPExpr left = parseMinus();
        TokenType type = this.getCurrent().getType();

        while (type == TokenType.LT
                || type == TokenType.GT
                || type == TokenType.LTEQ
                || type == TokenType.GTEQ) {
            this.advance();
            PPExpr right = parseMinus();

            PPBinaryExpr.Operator operator = PPBinaryExpr.Operator.GE;
            switch (type) {
                case LT: {
                    operator = PPBinaryExpr.Operator.LT;
                    break;
                }
                case GT: {
                    operator = PPBinaryExpr.Operator.GT;
                    break;
                }
                case LTEQ: {
                    operator = PPBinaryExpr.Operator.LE;
                    break;
                }
            }

            left = new PPBinaryExpr(
                    left, right,
                    operator
            );

            type = this.getCurrent().getType();
        }

        return left;
    }

    private PPExpr parseMinus() {
        PPExpr left = parsePlus();

        while (this.match(TokenType.MINUS)) {
            PPExpr right = parsePlus();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.SUB
            );
        }

        return left;
    }

    private PPExpr parsePlus() {
        PPExpr left = parseDiv();

        while (this.match(TokenType.PLUS)) {
            PPExpr right = parseDiv();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.ADD
            );
        }

        return left;
    }

    private PPExpr parseDiv() {
        PPExpr left = parseMul();

        while (this.match(TokenType.SLASH)) {
            PPExpr right = parseMul();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.DIV
            );
        }

        return left;
    }

    private PPExpr parseMul() {
        PPExpr left = parseUnary();

        while (this.match(TokenType.STAR)) {
            PPExpr right = parseUnary();
            left = new PPBinaryExpr(
                    left, right,
                    PPBinaryExpr.Operator.MUL
            );
        }

        return left;
    }

    private PPExpr parseUnary() {
        Token current = this.getCurrent();
        TokenType type = current.getType();

        if (type == TokenType.NOT || type == TokenType.MINUS) {
            return new PPUnaryExpr(
                    type == TokenType.NOT ? PPUnaryExpr.Operator.NOT : PPUnaryExpr.Operator.MINUS,
                    this.parseUnary()
            );
        } else {
            return this.parsePrimary();
        }
    }

    private PPExpr parsePrimary() {
        Token current = this.getCurrent();
        TokenType type = current.getType();

        switch (type) {
            case LITERAL_INTEGER: {
                this.advance();
                return new PPNumberExpr(Long.parseLong(current.getValue()));
            }
            case LITERAL_HEX: {
                this.advance();
                return new PPNumberExpr(Long.parseLong(current.getValue(), 16));
            }
            case LITERAL_OCT: {
                this.advance();
                return new PPNumberExpr(Long.parseLong(current.getValue(), 8));
            }
            case LITERAL_TRUE: {
                this.advance();
                return new PPNumberExpr(PPExpr.BOOL_TRUE);
            }
            case LITERAL_FALSE: {
                this.advance();
                return new PPNumberExpr(PPExpr.BOOL_FALSE);
            }
            case IDENTIFIER: {
                this.advance();
                if ("defined".equalsIgnoreCase(current.getValue()) && this.match(TokenType.LPAREN)) {
                    Token macro = this.expect(TokenType.IDENTIFIER);
                    this.consume(TokenType.RPAREN);
                    return new PPUnaryExpr(PPUnaryExpr.Operator.DEFINED, new PPMacroExpr(macro.getValue()));
                }
                return new PPMacroExpr(current.getValue());
            }
            case LPAREN: {
                this.advance();
                PPExpr expr = this.parsePPExpr();
                this.consume(TokenType.RPAREN);
                return expr;
            }
        }

        this.error("Unexpected token");
        return new PPNumberExpr(0);
    }
}
