package net.quepierts.animata4j.dsl.lexer.module;

import net.quepierts.animata4j.dsl.lexer.LexerView;
import net.quepierts.animata4j.dsl.lexer.Token;
import net.quepierts.animata4j.dsl.lexer.TokenType;
import net.quepierts.animata4j.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModuleLineComment extends LexerModule {
    public ModuleLineComment(int priority) {
        super(priority);
    }

    @Override
    public boolean isStart(char current, char last) {
        return current == '/' && last == '/';
    }

    @Override
    public @Nullable Token parse(@NotNull SourcePos start, @NotNull LexerView view) {

        StringBuilder builder = new StringBuilder()
                .append(view.advance());

        while (!view.isEol()) {
            builder.append(view.advance());
        }

        return new Token(
                TokenType.COMMENT_LINE,
                builder.toString(),
                view.span(start)
        );
    }
}
