package net.quepierts.animata4j.core.dsl.lexer.module;

import net.quepierts.animata4j.core.dsl.lexer.LexerView;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModuleLiteralString extends LexerModule {

    public ModuleLiteralString(int priority) {
        super(priority);
    }

    @Override
    public boolean isStart(char current, char last) {
        return current == '"';
    }

    @Override
    public @Nullable Token parse(@NotNull SourcePos start, final @NotNull LexerView view) {

        StringBuilder builder = new StringBuilder()
                .append(view.advance());

        while (!view.isEol()) {
            char c = view.advance();
            if (c == '"') {
                return new Token(
                        TokenType.LITERAL_STRING,
                        builder.toString(),
                        view.span(start)
                );
            }
        }

        view.error("Unclosed string literal");
        return null;
    }

}
