package net.quepierts.animata4j.core.dsl.parser;

import net.quepierts.animata4j.core.dsl.ast.ExpressionNode;
import net.quepierts.animata4j.core.dsl.lexer.ExpressionLexer;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser extends Parser {

    public ExpressionParser(final @NotNull ExpressionLexer lexer) {
        super(lexer);
    }

    public @NotNull ExpressionNode parse() {
        throw new UnsupportedOperationException();
    }

    /* <expression> ::= <term> [ (ADD | SUB) <term> ]* */
    private ExpressionNode parseExpression() {

    }

    /* <term> ::= <unary> [ (MUL | DIV | MOD) <unary> ]* */
    private ExpressionNode parseTerm() {

    }

    /* <unary> ::= [ (ADD | SUB) ] <primary> | <power> */
    private ExpressionNode parseUnary() {

    }

    /* <power> ::= <primary> [ POW <primary> ]? */
    private ExpressionNode parsePower() {

    }

    /*
    * <primary> ::= <number>
    *               | <word>
    *               | LPAREN <expression> RPAREN
    *  */
    private ExpressionNode parsePrimary() {

    }
}
