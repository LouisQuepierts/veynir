package net.quepierts.animata4j.dsl.lexer.module;

import it.unimi.dsi.fastutil.chars.CharPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.lexer.LexerView;
import net.quepierts.animata4j.dsl.lexer.Token;
import net.quepierts.animata4j.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor
public abstract class LexerModule implements Comparable<LexerModule> {

    private final int priority;

    public abstract boolean isStart(final char current, final char last);

    public abstract @Nullable Token parse(
            final @NotNull SourcePos start,
            final @NotNull LexerView view
    );

    @Override
    public int compareTo(@NotNull LexerModule o) {
        return Integer.compare(this.priority, o.priority);
    }

    protected void readWhile(
            @NotNull final LexerView view,
            @NotNull final CharPredicate predicate,
            @NotNull final StringBuilder builder
    ) {
        while (view.hasNext() && predicate.test(view.peek())) {
            builder.append(view.advance());
        }
    }
}
