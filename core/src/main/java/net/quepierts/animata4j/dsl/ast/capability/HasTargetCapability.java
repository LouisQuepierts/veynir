package net.quepierts.animata4j.dsl.ast.capability;

import net.quepierts.animata4j.dsl.ast.expr.Expression;

public interface HasTargetCapability {
    Expression getScope();
}
