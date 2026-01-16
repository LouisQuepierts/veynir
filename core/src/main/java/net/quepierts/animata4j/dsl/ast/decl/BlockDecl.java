package net.quepierts.animata4j.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class BlockDecl extends Declaration {

    @Nullable
    private final LayoutQualifier layout;

    @NotNull
    private final InterfaceQualifier qualifier;

    @NotNull
    private final String name;

    @Nullable
    private final String blockName;

    @NotNull
    private final List<MemberDecl> members;

    protected BlockDecl(
            @NotNull SourceSpan span,
            @Nullable LayoutQualifier layout,
            @NotNull InterfaceQualifier qualifier,
            @NotNull String name,
            @Nullable String blockName,
            @NotNull List<MemberDecl> members
    ) {
        super(span);
        this.layout = layout;
        this.qualifier = qualifier;
        this.name = name;
        this.blockName = blockName;
        this.members = members;
    }

    /**
     * format like:
     * <pre>{@code
     * layout(std140) in/out blockName {
     *     memberDecl;
     * } interfaceName;
     * }</pre>
     * */
    public static BlockDecl intf(
            @NotNull SourceSpan span,
            @Nullable LayoutQualifier layout,
            @NotNull InterfaceQualifier qualifier,
            @NotNull String blockName,
            @NotNull List<MemberDecl> members,
            @NotNull String interfaceName
    ) {
        return new BlockDecl(span, layout, qualifier, interfaceName, blockName, members);
    }

    /**
     * format like:
     * <pre>{@code
     * layout(std140) uniform name {
     *     memberDecl;
     * };
     * }</pre>
     */
    public static BlockDecl storage(
            @NotNull SourceSpan span,
            @Nullable LayoutQualifier layout,
            @NotNull InterfaceQualifier qualifier,
            @NotNull String name,
            @NotNull List<MemberDecl> members
    ) {
        return new BlockDecl(span, layout, qualifier, name, null, members);
    }

    public boolean hasBlockName() {
        return this.qualifier == InterfaceQualifier.IN || this.qualifier == InterfaceQualifier.OUT;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_BLOCK;
    }
}
