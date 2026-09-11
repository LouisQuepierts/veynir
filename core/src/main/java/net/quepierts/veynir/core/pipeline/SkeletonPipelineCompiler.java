package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.uniform.UniformType;
import org.jspecify.annotations.NonNull;

public interface SkeletonPipelineCompiler {

    @NonNull SkeletonPipelineCompiler withLayout(@NonNull SkeletonLayout layout);

    @NonNull SkeletonPipelineCompiler withPass(@NonNull PassDefinition pass);

    @NonNull SkeletonPipelineCompiler withProvider(@NonNull String name);

    @NonNull SkeletonPipelineCompiler withBuffer(@NonNull String name);

    @NonNull SkeletonPipelineCompiler withUniform(
            @NonNull String name,
            @NonNull UniformType type
    );

    @NonNull SkeletonPipelineCompiler withUbo(@NonNull String name);

    @NonNull SkeletonPipeline compile();

}
