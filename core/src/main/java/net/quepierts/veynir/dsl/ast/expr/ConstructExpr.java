package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.type.Type;
import net.quepierts.veynir.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class ConstructExpr extends Expression {

    private final Type datatype;
    private final List<Expression> arguments;

    public ConstructExpr(SourceSpan span, Type datatype, List<Expression> arguments) {
        super(span);
        this.datatype = datatype;
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_CONSTRUCT;
    }
}
