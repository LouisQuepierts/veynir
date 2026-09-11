package net.quepierts.veynir.backend;

import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.buffer.AttributeBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationPipelineCompilerImpl;
import net.quepierts.veynir.backend.pipeline.DefaultAnimationPipelineImpl;
import net.quepierts.veynir.backend.skeleton.pipeline.DefaultSkeletonPipelineImpl;
import net.quepierts.veynir.backend.skeleton.pipeline.SkeletonPipelineCompilerImpl;
import net.quepierts.veynir.backend.uniform.UniformBuffer;
import net.quepierts.veynir.core.VeynirBackend;
import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
import net.quepierts.veynir.core.pipeline.AnimationPipelineCompiler;
import net.quepierts.veynir.core.pipeline.SkeletonPipelineCompiler;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import org.jspecify.annotations.NonNull;

public class VeynirJavaBackend implements VeynirBackend {

    @Override
    public int priority() {
        return -100;
    }

    @Override
    public @NonNull AttributeBufferObject _allocateAttributeBuffer(final int size) {
        return new AttributeBuffer(size);
    }

    @Override
    public @NonNull AnimationBufferObject _allocateAnimationBuffer(final int size) {
        return new AnimationBuffer(size);
    }

    @Override
    public @NonNull UniformBufferObject _allocateUniformBuffer(final @NonNull UboDefinition definition) {
        return new UniformBuffer(definition);
    }

    @Override
    public @NonNull AnimationPipelineCompiler _animationPipelineCompiler() {
        return new AnimationPipelineCompilerImpl(DefaultAnimationPipelineImpl.compiler());
    }

    @Override
    public @NonNull SkeletonPipelineCompiler _skeletonPipelineCompiler() {
        return new SkeletonPipelineCompilerImpl(DefaultSkeletonPipelineImpl.compiler());
    }
}
