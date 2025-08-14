package net.quepierts.animata4j.core.data.tree.definition;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.misc.Generic;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public final class TreeStructureBuilder implements TreeStructureDefinition.Builder {
    private final Map<String, MutableNode> path2node;
    private final MutableNode root;

    private final ObjectArrayList<MutableNode> editStack;

    private MutableNode last;
    private boolean optimize = false;

    public TreeStructureBuilder(@NotNull final String root) {
        this.editStack = new ObjectArrayList<>();
        this.path2node = new Object2ObjectOpenHashMap<>();
        this.root = new MutableNode(root, null);
        this.last = this.root;
        this.editStack.push(this.root);
        this.path2node.put(root, this.root);
    }

    @Override
    @Contract(value = "_ -> this")
    public TreeStructureBuilder begin(@NotNull final String name) {
        final MutableNode current = this.editStack.top();
        final String parent = current.path();
        final String path = parent + "." + name;
        MutableNode node;

        if (this.path2node.containsKey(path)) {
            node = this.path2node.get(path);
        } else {
            node = new MutableNode(name, parent);
            this.path2node.put(path, node);
        }

        this.editStack.top().children.add(node);
        this.editStack.push(node);
        this.last = node;

        System.out.println("begin: " + path);
        return this;
    }

    @Override
    @Contract(value = "_ -> this")
    public TreeStructureBuilder edit(@NotNull final String path) {
        final MutableNode node = this.path2node.get(path);

        if (node == null) {
            throw new IllegalArgumentException("Node " + path + " does not exist");
        }

        this.editStack.push(node);
        this.last = node;

        return this;
    }

    @Override
    @Contract(value = "_ -> this")
    public TreeStructureBuilder add(@NotNull final String name) {
        final MutableNode current = this.editStack.top();
        final String parent = current.path();
        final String path = parent + "." + name;
        if (this.path2node.containsKey(path)) {
            throw new IllegalArgumentException("Node " + name + " already exists");
        }
        final MutableNode node = new MutableNode(name, parent);
        this.path2node.put(path, node);
        current.children.add(node);
        this.last = node;

        System.out.println("add: " + path);
        return this;
    }

    @Override
    @Contract(value = "_ -> this")
    public TreeStructureBuilder remove(@NotNull final String name) {
        final MutableNode current = this.editStack.top();
        final String parent = current.path();
        final String path = parent + "." + name;
        final MutableNode node = this.path2node.get(path);
        if (node == null) {
            throw new IllegalArgumentException("Node " + name + " does not exist");
        }
        current.children.remove(node);
        this.path2node.remove(path);
        return this;
    }

    @Override
    @Contract(value = "-> this")
    public TreeStructureBuilder end() {
        if (this.editStack.top() == this.root) {
            throw new IllegalStateException("Cannot end root node");
        }
        System.out.println("end: " + this.editStack.top().path());
        this.last = this.editStack.pop();
        return this;
    }

    @Override
    @Contract(value = "_ -> this")
    public TreeStructureBuilder data(Object data) {
        this.last.data = data;
        return this;
    }

    @Override
    @Contract(value = "-> this")
    public TreeStructureBuilder optimize() {
        this.optimize = true;
        return this;
    }
    
    @Override
    @Contract(value = "-> new", pure = true)
    public Result build() {
        final int size = this.path2node.size();
        final List<MutableNode> mutableNodes = new ObjectArrayList<>(size);
        final int[] childIndices = new int[size];
        final int[] childCounts = new int[size];
        final Object[] data = new Object[size];

        Arrays.fill(childIndices, 1);

        // BFS
        final Queue<MutableNode> queue = new ArrayDeque<>(size);
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            final MutableNode node = queue.poll();
            final int index = mutableNodes.size();

            mutableNodes.add(node);
            data[index] = node.data;
            childCounts[index] = node.children.size();

            Collection<MutableNode> children = node.children;
            if (this.optimize) {
                children = children.stream()
                        .sorted()
                        .toList();
            }

            for (MutableNode child : children) {
                queue.offer(child);
            }
        }

        for (int i = 1; i < childIndices.length; i++) {
            final int lastCount = childCounts[i - 1];
            final int count = childCounts[i];

            final int index = TreeStructureDefinition.decodeChildIndex(childIndices[i - 1]) + lastCount;
            childIndices[i] = count == 0 ? TreeStructureDefinition.encodeLeafNode(index) : index;
        }

        final TreeStructureDefinition definition = new TreeStructureDefinition(
                mutableNodes.stream()
                        .map(node -> node.name)
                        .toArray(String[]::new),
                childIndices
        );

        return new Result(definition, data);
    }

    @Override
    @Contract(value = "-> _")
    public int getEditingDeep() {
        return this.editStack.size() - 1;
    }

    static class MutableNode implements Comparable<MutableNode> {
        private final String name;
        private final String parent;
        private final Set<MutableNode> children;

        private Object data;

        public MutableNode(String name, String parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ObjectArraySet<>();
        }

        public String path() {
            return this.parent == null ? this.name : this.parent + "." + this.name;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TreeStructureBuilder.MutableNode && this.name.equals(((MutableNode) obj).name);
        }

        @Override
        public int compareTo(@NotNull TreeStructureBuilder.MutableNode mutableNode) {
            return this.name.compareTo(mutableNode.name);
        }
    }

    @RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static class Result implements TreeStructureDefinition.Result {
        private final TreeStructureDefinition definition;
        private final Object[] data;

        @Override
        @Contract(value = "-> !null")
        public TreeStructureDefinition definition() {
            return this.definition;
        }

        @Override
        @Contract(value = "_ -> !null")
        public <E> TreeData<E> data(@NotNull final Class<E> clazz) {
            return TreeData.of(Generic.cast(this.data));
        }

        @Override
        @Contract(value = "_ -> new")
        public <T, E> T collect(@NotNull final BiFunction<TreeStructureDefinition, E[], T> constructor) {
            return constructor.apply(this.definition, Generic.cast(this.data));
        }
    }
}
