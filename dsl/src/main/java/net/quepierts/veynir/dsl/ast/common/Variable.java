package net.quepierts.veynir.dsl.ast.common;

import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.dsl.ast.type.Type;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class Variable {

    public static Variable of(Type datatype, VariableDeclarator declarator) {
        return new Variable(declarator.getName(), datatype, declarator.getDimensions());
    }

    private final String name;
    private final Type datatype;
    private final IntList dimensions;

}
