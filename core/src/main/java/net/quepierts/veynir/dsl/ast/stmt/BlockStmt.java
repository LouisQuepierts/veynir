package net.quepierts.veynir.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class BlockStmt extends Statement {

    private final List<Statement> statements;

    public BlockStmt(SourceSpan span, List<Statement> statements) {
        super(span);
        this.statements = statements;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_BLOCK;
    }
}
