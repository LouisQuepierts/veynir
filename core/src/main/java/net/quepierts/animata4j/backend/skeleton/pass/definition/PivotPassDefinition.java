package net.quepierts.animata4j.backend.skeleton.pass.definition;

import net.quepierts.animata4j.backend.skeleton.pass.PivotPass;
import net.quepierts.animata4j.backend.skeleton.pass.SkeletonPass;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonPipelineCompileContext;
import org.jetbrains.annotations.NotNull;

public final class PivotPassDefinition extends SkeletonPassDefinition {

    public static final String REQUIRED_UBO = "SkeletonPivots";

    private String src;
    private String dst;

    public PivotPassDefinition(final String name) {
        super(name);
    }

    public PivotPassDefinition src(final String src) {
        this.src = src;
        return this;
    }

    public PivotPassDefinition dst(final String dst) {
        this.dst = dst;
        return this;
    }

    @Override
    public SkeletonPass compile(@NotNull final SkeletonPipelineCompileContext context) {
        final var layout    = context.getLayout();
        final var bones     = layout.size();
        final var location  = context.getUboLocation(REQUIRED_UBO);

        final var src       = context.getBufferLocation(this.src);
        final var dst       = context.getBufferLocation(this.dst);

        return new PivotPass(this.getName(), src, dst, bones, location);
    }
}
