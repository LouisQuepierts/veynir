package net.quepierts.animata4j.core.dsl;

import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.tree.definition.TreeStructureDefinition;
import net.quepierts.animata4j.core.misc.Generic;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TrieTree<T> {
    private final char[] chars;
    private final int[] childIndices;
    private final T[] data;

    private final T defaultValue;

    public int find(final char c, final int index) {
        final int left = TreeStructureDefinition.decodeChildIndex(this.childIndices[index]);
        final int right = TreeStructureDefinition.decodeChildIndex(this.childIndices[index + 1]);

        // liner search
        for (int i = left; i < right; i++) {
            if (this.chars[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public T get(int index) {
        return this.data[index];
    }

    public boolean isTerminal(int index) {
        return this.data[index] != this.defaultValue;
    }

    @RequiredArgsConstructor
    public static final class Builder<T> {
        private final T defaultValue;
        private final MutableNode<T> root = new MutableNode<>('\0');
        private int size = 1;

        public Builder<T> put(String string, T data) {
            MutableNode<T> node = root;
            for (char c : string.toCharArray()) {
                node = node.children.computeIfAbsent(c, k -> {
                    size++;
                    return new MutableNode<>(k);
                });
                node.frequency++;
            }
            node.value = data;
            return this;
        }

        public TrieTree<T> build() {
            final List<MutableNode<T>> mutableNodes = new ObjectArrayList<>(size);
            final int[] childIndices = new int[size];
            final int[] childCounts = new int[size];
            final char[] values = new char[size];
            final T[] data = Generic.newArray(size);

            Arrays.fill(childIndices, 1);
            Arrays.fill(data, this.defaultValue);

            final ObjectArrayFIFOQueue<MutableNode<T>> queue = new ObjectArrayFIFOQueue<>(size);
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                final MutableNode<T> node = queue.dequeue();
                final int index = mutableNodes.size();

                mutableNodes.add(node);
                childCounts[index] = node.children.size();
                values[index] = node.c;
                if (node.value != null) {
                    data[index] = node.value;
                }

                node.children
                        .values()
                        .stream().sorted()
                        .forEach(queue::enqueue);
            }

            for (int i = 1; i < childIndices.length; i++) {
                final int lastCount = childCounts[i - 1];
                final int count = childCounts[i];

                final int index = TreeStructureDefinition.decodeChildIndex(childIndices[i - 1]) + lastCount;
                childIndices[i] = count == 0 ? TreeStructureDefinition.encodeLeafNode(index) : index;
            }

            return new TrieTree<T>(
                    values,
                    childIndices,
                    data,
                    defaultValue
            );
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class MutableNode<T> implements Comparable<MutableNode<T>> {
        private final char c;

        private final Char2ObjectMap<MutableNode<T>> children = new Char2ObjectArrayMap<>();
        private T value;
        private byte frequency = 0;

        @Override
        public int compareTo(@NotNull TrieTree.MutableNode<T> o) {
            return o.frequency - this.frequency;
        }
    }
}
