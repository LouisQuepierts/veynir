package net.quepierts.veynir.core.pipeline;

import lombok.Getter;
import org.jspecify.annotations.NonNull;

/**
 * Backend neutral descriptor of a skeleton pass.
 * <p>
 * Operands are symbolic names resolved to locations by the backend at compile time.
 */
@Getter
public final class SkeletonPassDefinition implements PassDefinition {

    public static final String REQUIRED_UBO_PIVOT           = "SkeletonPivots";
    public static final String REQUIRED_UBO_PARENT_OVERRIDE = "ParentOverrides";

    public enum Kind {
        FETCH,
        MERGE,
        PIVOT,
        PARENT_OVERRIDE
    }

    private final String    name;
    private final Kind      kind;

    private String src0;
    private String src1;
    private String dst;

    private SkeletonPassDefinition(@NonNull final String name, @NonNull final Kind kind) {
        this.name   = name;
        this.kind   = kind;
    }

    public static SkeletonPassDefinition fetch(@NonNull final String name) {
        return new SkeletonPassDefinition(name, Kind.FETCH);
    }

    public static SkeletonPassDefinition merge(@NonNull final String name) {
        return new SkeletonPassDefinition(name, Kind.MERGE);
    }

    public static SkeletonPassDefinition pivot(@NonNull final String name) {
        return new SkeletonPassDefinition(name, Kind.PIVOT);
    }

    public static SkeletonPassDefinition parentOverride(@NonNull final String name) {
        return new SkeletonPassDefinition(name, Kind.PARENT_OVERRIDE);
    }

    public SkeletonPassDefinition src(final String src) {
        this.src0 = src;
        return this;
    }

    public SkeletonPassDefinition src0(final String src) {
        this.src0 = src;
        return this;
    }

    public SkeletonPassDefinition src1(final String src) {
        this.src1 = src;
        return this;
    }

    public SkeletonPassDefinition dst(final String dst) {
        this.dst = dst;
        return this;
    }

}
