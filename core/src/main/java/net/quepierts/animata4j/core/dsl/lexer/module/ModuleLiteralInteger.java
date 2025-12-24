package net.quepierts.animata4j.core.dsl.lexer.module;

import it.unimi.dsi.fastutil.chars.CharPredicate;
import net.quepierts.animata4j.core.dsl.lexer.LexerHelper;
import net.quepierts.animata4j.core.dsl.lexer.LexerView;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModuleLiteralInteger extends LexerModule {

    private final boolean allowBinary;
    private final boolean allowOctal;
    private final boolean allowHex;

    public ModuleLiteralInteger(int priority, boolean allowBinary, boolean allowOctal, boolean allowHex) {
        super(priority);
        this.allowBinary = allowBinary;
        this.allowOctal = allowOctal;
        this.allowHex = allowHex;
    }

    @Override
    public boolean isStart(char current, char last) {
        return LexerHelper.isNumberStart(current);
    }

    @Override
    public @Nullable Token parse(@NotNull SourcePos start, @NotNull LexerView view) {

        char first = view.peek();
        char current = view.advance();
        StringBuilder builder = new StringBuilder()
                .append(first)
                .append(current);

        TokenType type = TokenType.LITERAL_INTEGER;
        CharPredicate predicate = LexerHelper::isDigit;
        if (first == '0') {
            if (allowBinary && (current == 'b' || current == 'B')) {
                type = TokenType.LITERAL_BIN;
                predicate = (c) -> c == '0' || c == '1';
            } else if (allowHex && (current == 'x' || current == 'X')) {
                type = TokenType.LITERAL_HEX;
                predicate = LexerHelper::isHexDigit;
            } else if (allowOctal && LexerHelper.isOctDigit(current)) {
                type = TokenType.LITERAL_OCT;
                predicate = LexerHelper::isOctDigit;
            }
            view.error("Invalid number literal");
        }

        while (view.hasNext()) {
            current = view.advance();

            if (predicate.test(current)) {
                builder.append(current);
            } else {
                return new Token(
                        type,
                        builder.toString(),
                        view.span(start)
                );
            }
        }

        view.error("Unclosed integer");
        return null;
    }
}
