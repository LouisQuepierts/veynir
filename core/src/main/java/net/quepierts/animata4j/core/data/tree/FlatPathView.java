package net.quepierts.animata4j.core.data.tree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface FlatPathView<T> extends PathResolvable<T> {
    @Override
    @Contract(value = "_ -> new", pure = true)
    default @NotNull Result<T> resolve(@NotNull final String[] tokens) {
        final int length = tokens.length;

        int current = this.rootIndex();
        int last = current;

        int i = 0;
        while (i < length && this.isUniqueName(tokens[i])) {
            last = current;
            current = this.getUniqueIndex(tokens[i]);
            i ++;
        }

        for (; i < length; i++) {
            last = current;
            current = this.findChild(current, tokens[i]);
            if (current == -1) {
                break;
            }
        }

        return Result.of(
                i == length,
                i,
                i == length ? this.getValue(current) : this.getValue(last)
        );
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
}
