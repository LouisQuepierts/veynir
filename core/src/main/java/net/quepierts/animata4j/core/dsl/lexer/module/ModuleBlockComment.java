package net.quepierts.animata4j.core.dsl.lexer.module;

import net.quepierts.animata4j.core.dsl.lexer.LexerView;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModuleBlockComment extends LexerModule {

    public ModuleBlockComment(int priority) {
        super(priority);
    }

    @Override
    public boolean isStart(char current, char last) {
        return current == '*' && last == '/';
    }

    @Override
    public @Nullable Token parse(@NotNull SourcePos start, @NotNull LexerView view) {
        StringBuilder builder = new StringBuilder()
                .append(view.advance());

        while (!view.isEof()) {
            char last = view.peek();
            char current = view.advance();

            if (current == '/' && last == '*') {
                return new Token(
                        TokenType.COMMENT_BLOCK,
                        builder.toString(),
                        view.span(start)
                );
            }

            builder.append(current);
        }

        view.error("Unclosed comment");
        return null;
    }
}
