package net.quepierts.animata4j.backend.skeleton.pass.definition;

import net.quepierts.animata4j.backend.skeleton.pass.MergePass;
import net.quepierts.animata4j.backend.skeleton.pass.SkeletonPass;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonPipelineCompileContext;
import org.jetbrains.annotations.NotNull;

public final class MergePassDefinition extends SkeletonPassDefinition {

    private String src0;
    private String src1;
    private String dst;

    public MergePassDefinition(final String name) {
        super(name);
    }

    public MergePassDefinition src0(final String src) {
        this.src0 = src;
        return this;
    }

    public MergePassDefinition src1(final String src) {
        this.src1 = src;
        return this;
    }

    public MergePassDefinition dst(final String dst) {
        this.dst = dst;
        return this;
    }

    @Override
    public SkeletonPass compile(@NotNull final SkeletonPipelineCompileContext context) {
        final var src0       = context.getBufferLocation(this.src0);
        final var src1       = context.getBufferLocation(this.src1);
        final var dst           = context.getBufferLocation(this.dst);
        return new MergePass(
                this.getName(),
                src0,
                src1,
                dst,
                context.getLayout().size()
        );
    }
}
