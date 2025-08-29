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

        final SourcePos leftPos = this.getSourcePos();
        char c = this.advance();

        if (c == DOT) {
            return null;
        }

        if (c == LEFT_BRACKET) {
            final int left = this.getPos();
            final int right = this.findCloseBracket();

            if (right == -1) {
                this.error("Unclosed bracket");
                return null; // unreachable
            }

            final ExpressionLexer subLexer = this.sublexer(ExpressionLexer::new, right - left);
            final ExpressionParser parser = new ExpressionParser(subLexer);
            final ExpressionNode expr = parser.parse();

            while (this.getPos() <= right) {
                this.advance();
            }
            final String value = subLexer.source();
            return PathToken.subscript(value, this.span(leftPos), expr);
        }

        if (Lexer.isIdentifierStart(c)) {
            final String word = c + this.readWhile(Lexer::isIdentifierPart);
            return PathToken.simple(word, this.span(leftPos));
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
