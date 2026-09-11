package net.quepierts.veynir.backend.skeleton.pass;

import net.quepierts.veynir.backend.skeleton.pipeline.SkeletonPipelineCompileContext;
import net.quepierts.veynir.core.pipeline.SkeletonPassDefinition;
import org.jspecify.annotations.NonNull;

/**
 * Interprets a backend neutral {@link SkeletonPassDefinition} into an executable {@link SkeletonPass}
 * by resolving symbolic operand names against the pipeline compile context.
 */
public final class SkeletonPassCompiler {

    private SkeletonPassCompiler() {}

    public static SkeletonPass compile(
            @NonNull final SkeletonPassDefinition definition,
            @NonNull final SkeletonPipelineCompileContext context
    ) {
        return switch (definition.getKind()) {
            case FETCH -> new FetchPass(
                    definition.getName(),
                    context.getProviderLocation(definition.getSrc0()),
                    context.getBufferLocation(definition.getDst())
            );
            case MERGE -> new MergePass(
                    definition.getName(),
                    context.getBufferLocation(definition.getSrc0()),
                    context.getBufferLocation(definition.getSrc1()),
                    context.getBufferLocation(definition.getDst()),
                    context.getLayout().size()
            );
            case PIVOT -> new PivotPass(
                    definition.getName(),
                    context.getBufferLocation(definition.getSrc0()),
                    context.getBufferLocation(definition.getDst()),
                    context.getLayout().size(),
                    context.getUboLocation(SkeletonPassDefinition.REQUIRED_UBO_PIVOT)
            );
            case PARENT_OVERRIDE -> new ParentOverridePass(
                    definition.getName(),
                    context.getBufferLocation(definition.getSrc0()),
                    context.getBufferLocation(definition.getDst()),
                    context.getLayout().size(),
                    context.getUboLocation(SkeletonPassDefinition.REQUIRED_UBO_PARENT_OVERRIDE)
            );
        };
    }

}
