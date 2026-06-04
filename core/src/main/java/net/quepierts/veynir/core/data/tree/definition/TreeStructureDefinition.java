package net.quepierts.veynir.core.data.tree.definition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
public class TreeStructureDefinition {
    @Contract(value = "_ -> new", pure = true)
    public static Builder builder(@NotNull final String root) {
        return new TreeStructureBuilder(root);
    }

    private final String[] nodes;

    // if the highest bit of the index is 1, then this node has children
    // or else, this node is a leaf node
    private final int[] childIndices;

    @Getter
    private final boolean optimized;

    @Contract(value = "_ -> _", pure = true)
    public int getChildCount(int index) {
        final int childIndex = this.childIndices[index];
        return isLeafNode(childIndex) ? 0 : decodeChildIndex(this.childIndices[index + 1]) - childIndex;
    }

    @Contract(value = "_ -> _", pure = true)
    public int getRawChildIndex(int index) {
        return this.childIndices[index];
    }

    @Contract(value = "_ -> _", pure = true)
    public String getName(int index) {
        return this.nodes[index];
    }

    @Contract(value = "-> _", pure = true)
    public int size() {
        return this.nodes.length;
    }

    public static boolean isLeafNode(int childIndex) {
        return (childIndex & 0x80000000) == 0;
    }

    public static int encodeLeafNode(int childIndex) {
        return childIndex | 0x80000000;
    }

    public static int decodeChildIndex(int childIndex) {
        return childIndex & 0x7FFFFFFF;
    }

    public interface Builder {
        @Contract(value = "_ -> this")
        Builder begin(String name);

        @Contract(value = "_ -> this")
        Builder edit(String path);

        @Contract(value = "_ -> this")
        Builder add(String name);

        @Contract(value = "_ -> this")
        Builder remove(String name);

        @Contract(value = "-> this")
        Builder end();

        @Contract(value = "_ -> this")
        Builder data(Object data);

        @Contract(value = "-> this")
        Builder optimize();

        @Contract(value = "-> new", pure = true)
        Result build();

        @Contract(value = "-> _")
        int getEditingDeep();
    }

    public interface Result {
        @Contract(value = "-> !null")
        TreeStructureDefinition definition();

        @Contract(value = "_ -> !null")
        <E> TreeData<E> data(@NotNull Class<E> clazz);

        @Contract(value = "_ -> new")
        <T, E> T collect(@NotNull BiFunction<TreeStructureDefinition, E[], T> constructor);
    }
}
