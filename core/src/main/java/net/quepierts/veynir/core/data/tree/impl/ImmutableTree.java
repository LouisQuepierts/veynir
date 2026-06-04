package net.quepierts.veynir.core.data.tree.impl;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.data.tree.FlatPathView;
import net.quepierts.veynir.core.data.tree.definition.TreeStructureDefinition;
import net.quepierts.veynir.core.misc.BinarySearch;
import net.quepierts.veynir.core.misc.Generic;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImmutableTree<T> implements FlatPathView<T> {
    private final Node<T>[] nodes;
    private final Object2IntMap<String> unique;
    private final boolean optimized;

    public static <T> ImmutableTree<T> of(
            @NotNull TreeStructureDefinition definition,
            @NotNull T[] data
    ) {
        final Node<T>[] nodes = Generic.cast(new Node[definition.size()]);
        final Object2IntMap<String> unique = new Object2IntOpenHashMap<>();
        for (int i = 0; i < definition.size(); i++) {
            final String name = definition.getName(i);
            nodes[i] = new Node<>(
                    name,
                    data[i],
                    definition.getRawChildIndex(i)
            );

            if (unique.containsKey(name)) {
                unique.put(name, -1);
            } else {
                unique.put(name, i);
            }
        }

        unique.object2IntEntrySet().removeIf(entry -> entry.getIntValue() == -1);
        return new ImmutableTree<>(
                nodes, unique,
                definition.isOptimized()
        );
    }

    @Override
    public int rootIndex() {
        return 0;
    }

    @Override
    public int findChild(int index, @NotNull String child) {
        final Node<T> node = this.nodes[index];
        if (TreeStructureDefinition.isLeafNode(node.firstChildIndex)) {
            return -1;
        }

        final int left = TreeStructureDefinition.decodeChildIndex(node.firstChildIndex);
        final int right = TreeStructureDefinition.decodeChildIndex(this.nodes[index + 1].firstChildIndex);

        if (this.optimized) {
            return BinarySearch.search(
                    left, right,
                    this.nodes,
                    child,
                    Node::getName
            );
        } else {
            return linearSearch(left, right, child);
        }
    }

    @Override
    public boolean isUniqueName(@NotNull String name) {
        return this.unique.containsKey(name);
    }

    @Override
    public int getUniqueIndex(@NotNull String name) {
        return this.unique.getOrDefault(name, -1);
    }

    @Override
    public String getName(int index) {
        return this.nodes[index].getName();
    }

    @Override
    public T getValue(int index) {
        return this.nodes[index].getValue();
    }

    public boolean isLegalIndex(int index) {
        return index >= 0 && index < this.nodes.length;
    }

    private int linearSearch(int left, int right, @NotNull String name) {
        for (int i = left; i <= right; i++) {
            if (this.nodes[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Getter
    @RequiredArgsConstructor
    private static final class Node<T>{
        private final String name;
        private final T value;
        private final int firstChildIndex;
    }
}
