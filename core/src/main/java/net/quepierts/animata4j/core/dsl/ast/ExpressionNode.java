package net.quepierts.animata4j.core.dsl.ast;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;

public class ExpressionNode extends Node {
    @Override
    public TypeIdentifier<? extends Node> getType() {
        return null;
    }

    @RequiredArgsConstructor
    public static final class Number extends ExpressionNode {
        public final String value;
    }
}
