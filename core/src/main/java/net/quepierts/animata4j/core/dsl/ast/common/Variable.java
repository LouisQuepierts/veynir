package net.quepierts.animata4j.core.dsl.ast.common;

import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class Variable {

    public static Variable of(String datatype, VariableDeclarator declarator) {
        return new Variable(declarator.getName(), datatype, declarator.getDimensions());
    }

    private final String name;
    private final String datatype;
    private final IntList dimensions;

}
