package net.quepierts.veynir.dsl.ast.capability;

import net.quepierts.veynir.dsl.ast.expr.Expression;

public interface HasTargetCapability {
    Expression getScope();
}
