package net.quepierts.veynir.backend.skeleton.pipeline;

import net.quepierts.veynir.backend.skeleton.pass.definition.SkeletonPassDefinition;
import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.pipeline.PassDefinition;
import net.quepierts.veynir.core.pipeline.SkeletonPipeline;
import net.quepierts.veynir.core.pipeline.SkeletonPipelineCompiler;
import net.quepierts.veynir.core.uniform.UniformType;
import org.jspecify.annotations.NonNull;

public final class SkeletonPipelineCompilerImpl implements SkeletonPipelineCompiler {

    private final DefaultSkeletonPipelineImpl.Compiler compiler;

    public SkeletonPipelineCompilerImpl(final DefaultSkeletonPipelineImpl.@NonNull Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withLayout(final @NonNull SkeletonLayout layout) {
        this.compiler.withLayout(layout);
        return this;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withPass(final @NonNull PassDefinition pass) {
        if (!(pass instanceof SkeletonPassDefinition definition)) {
            throw new IllegalArgumentException("Not a skeleton pass definition: " + pass);
        }
        this.compiler.withPass(definition);
        return this;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withProvider(final @NonNull String name) {
        this.compiler.withProvider(name);
        return this;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withBuffer(final @NonNull String name) {
        this.compiler.withBuffer(name);
        return this;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withUniform(
            final @NonNull String name,
            final @NonNull UniformType type
    ) {
        this.compiler.withUniform(name, type);
        return this;
    }

    @Override
    public @NonNull SkeletonPipelineCompiler withUbo(final @NonNull String name) {
        this.compiler.withUniform(name);
        return this;
    }

    @Override
    public @NonNull SkeletonPipeline compile() {
        return this.compiler.compile();
    }

}
