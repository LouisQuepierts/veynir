package net.quepierts.animata4j.dsl.ast.common;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class VariableDeclarator {

    public static VariableDeclarator of(String name) {
        return new VariableDeclarator(name, new IntArrayList());
    }

    public static VariableDeclarator of(String name, IntList dimensions) {
        return new VariableDeclarator(name, dimensions);
    }

    private final String name;
    private final IntList dimensions;

}
