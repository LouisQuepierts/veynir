package net.quepierts.animata4j.core.data.tree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface NestedPathView<T> extends PathResolvable<T> {
    static <T> @NotNull Result<T> resolve(
            @NotNull final NestedPathView<T> view,
            @NotNull final String[] tokens
    ) {
        int i = 0;
        NestedPathView<T> current = view;
        while (i < tokens.length - 1) {
            final String token = tokens[i];
            final NestedPathView<T> next = current.getChild(token);

            if (next == null) {
                return Result.of(false, i, current.getValue(token));
            }

            current = next;
            i++;
        }

        return Result.of(true, i, current.getValue(tokens[i]));
    }

    @Override
    @Contract(value = "_ -> new", pure = true)
    default @NotNull Result<T> resolve(final @NotNull String[] tokens) {
        return NestedPathView.resolve(this, tokens);
    }

    @Nullable NestedPathView<T> getChild(@NotNull final String name);

    @Nullable T getValue(@NotNull final String name);
}
