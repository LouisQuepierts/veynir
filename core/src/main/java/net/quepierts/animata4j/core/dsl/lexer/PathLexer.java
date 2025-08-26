package net.quepierts.animata4j.core.dsl.lexer;

import net.quepierts.animata4j.core.dsl.SourcePos;
import net.quepierts.animata4j.core.dsl.ast.ExpressionNode;
import net.quepierts.animata4j.core.dsl.parser.ExpressionParser;
import net.quepierts.animata4j.core.dsl.token.PathToken;
import net.quepierts.animata4j.core.dsl.token.Token;
import org.jetbrains.annotations.NotNull;

public class PathLexer extends Lexer {

    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';
    private static final char DOT = '.';

    public PathLexer(final @NotNull String source) {
        super(source);
    }

    public PathLexer(
            final @NotNull String source,
            final @NotNull SourcePos pos,
            final int length
    ) {
        super(source, pos, length);
    }

    @Override
    public Token next() {
        this.skipWhitespace();
        if (!this.hasNext()) {
            return this.eof();
        }

        char c = this.advance();

        if (c == DOT) {
            this.advance();
            return null;
        }

        final SourcePos leftPos = this.getSourcePos();
        if (c == LEFT_BRACKET) {
            this.advance();
            final int left = this.getPos();
            final int right = this.findCloseBracket();

            if (right == -1) {
                this.error("Unclosed bracket");
                return null; // unreachable
            }

            final ExpressionLexer subLexer = this.sublexer(ExpressionLexer::new, right - left);
            final ExpressionParser parser = new ExpressionParser(subLexer);
            final ExpressionNode expr = parser.parse();

            return PathToken.subscript(this.span(leftPos), expr);
        }

        if (Lexer.isIdentifierStart(c)) {
            final String word = this.readWhile(Lexer::isIdentifierPart);
            return PathToken.simple(this.span(leftPos), word);
        }

        this.error("Invalid character: " + c);
        return null; // unreachable
    }

    private int findCloseBracket() {
        this.skipWhitespace();

        int depth = 1;
        int i = this.getPos();

        while (i < this.getEnd()) {
            char c = this.getSource().charAt(i);
            if (c == LEFT_BRACKET) {
                depth ++;
            } else if (c == RIGHT_BRACKET) {
                depth --;
                if (depth == 0) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }
}
