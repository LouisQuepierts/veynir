package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.LayoutCapability;
import net.quepierts.animata4j.core.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public final class BlockInterfaceDecl
        extends Declaration
        implements LayoutCapability {

    @Nullable
    private final LayoutQualifier layout;
    private final InterfaceQualifier qualifier;
    private final String name;
    private final List<MemberDecl> members;

    public BlockInterfaceDecl(
            SourceSpan span,
            @Nullable LayoutQualifier layout,
            InterfaceQualifier qualifier,
            String name,
            List<MemberDecl> members
    ) {
        super(span);
        this.layout = layout;
        this.qualifier = qualifier;
        this.name = name;
        this.members = members;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_BLOCK_INTERFACE;
    }
}
