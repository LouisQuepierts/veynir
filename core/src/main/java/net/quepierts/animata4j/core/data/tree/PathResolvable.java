package net.quepierts.animata4j.core.data.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface PathResolvable<T> {

    @Contract(value = "_, _ -> new", pure = true)
    static <T> Result<T> resolve(
            @NotNull final PathResolvable<T> resolvable,
            @NotNull final String[] tokens
    ) {
        final int length = tokens.length;

        int current = resolvable.rootIndex();
        int last = current;

        int i = 0;
        while (i < length && resolvable.isUniqueName(tokens[i])) {
            last = current;
            current = resolvable.getUniqueIndex(tokens[i]);
            i ++;
        }

        for (; i < length; i++) {
            last = current;
            current = resolvable.findChild(current, tokens[i]);
            if (current == -1) {
                break;
            }
        }

        return Result.of(
                i == length,
                i,
                i == length ? resolvable.getValue(current) : resolvable.getValue(last)
        );
    }

    @Contract(value = "_ -> new", pure = true)
    default Result<T> resolve(@NotNull final String path) {
        return resolve(this, path.split("\\."));
    }

    @Contract(value = "_ -> new", pure = true)
    default Result<T> resolve(@NotNull final String[] tokens) {
        return resolve(this, tokens);
    }

    @Contract(pure = true)
    int rootIndex();

    @Contract(pure = true)
    int findChild(final int index, @NotNull final String child);

    @Contract(pure = true)
    boolean isUniqueName(@NotNull final String name);

    @Contract(pure = true)
    int getUniqueIndex(@NotNull final String name);

    @Contract(pure = true)
    String getName(final int index);

    @Contract(pure = true)
    T getValue(final int index);

    @Getter
    @AllArgsConstructor(staticName = "of")
    class Result<T> {
        private final boolean success;
        private final int index;
        private final T value;
    }
}
