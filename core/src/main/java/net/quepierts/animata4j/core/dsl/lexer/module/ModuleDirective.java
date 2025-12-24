package net.quepierts.animata4j.core.dsl.lexer.module;

import net.quepierts.animata4j.core.dsl.lexer.LexerHelper;
import net.quepierts.animata4j.core.dsl.lexer.LexerView;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModuleDirective extends LexerModule {
    public ModuleDirective(int priority) {
        super(priority);
    }

    @Override
    public boolean isStart(char current, char last) {
        return current == '#' && last != '\\';
    }

    @Override
    public @Nullable Token parse(@NotNull SourcePos start, @NotNull LexerView view) {

        StringBuilder builder = new StringBuilder()
                .append(view.advance());

        while (view.hasNext()) {
            char c = view.advance();

            if (!LexerHelper.isIdentifierPart(c)) {
                return new Token(
                        TokenType.DIRECTIVE,
                        builder.toString(),
                        view.span(start)
                );
            }
        }

        view.error("Unexpected EOF");
        return null;
    }
}
