package net.quepierts.veynir.core.data.tree.definition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@RequiredArgsConstructor(staticName = "of")
public final class TreeData<T> {
    private final T[] values;

    public T get(
            @NotNull final String path,
            @NotNull final TreeStructureDefinition definition
    ) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Appended<T> iterate(@NotNull final TreeStructureDefinition definition) {
        if (this.values.length != definition.size()) {
            throw new IllegalArgumentException("Invalid size");
        }

        return new Appended<>(definition, this.values);
    }

    @Contract(value = "-> _", pure = true)
    public int size() {
        return this.values.length;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Appended<T> implements Iterable<Pair<T>> {
        private final TreeStructureDefinition definition;
        private final T[] values;

        @Override
        @Contract(value = "-> new", pure = true)
        public @NotNull Iterator<Pair<T>> iterator() {
            return new Iter<>(this.definition, this.values);
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Pair<T> {
        private final String name;
        private final int childIndex;
        private final int childCount;
        private final T value;

        public String first() {
            return this.name;
        }

        public int second() {
            return this.childIndex;
        }

        public int third() {
            return this.childCount;
        }

        public T fourth() {
            return this.value;
        }
    }

    private static final class Iter<T> implements Iterator<Pair<T>> {
        private final TreeStructureDefinition definition;
        private final T[] values;
        private int index;

        private Iter(
                @NotNull final TreeStructureDefinition definition,
                @NotNull final T[] values
        ) {
            this.definition = definition;
            this.values = values;
            this.index = 0;
        }

        @Override
        @Contract(value = "-> _", pure = true)
        public boolean hasNext() {
            return this.index < this.values.length;
        }

        @Override
        @Contract(value = "-> new")
        public Pair<T> next() {
            if (!this.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + this.index + ", Size: " + this.values.length);
            }
            Pair<T> pair = new Pair<>(
                    this.definition.getName(this.index),
                    this.definition.getRawChildIndex(this.index),
                    this.definition.getChildCount(this.index),
                    this.values[this.index]
            );
            this.index++;
            return pair;
        }
    }
}
