package net.quepierts.veynir.core;

import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
import net.quepierts.veynir.core.pipeline.AnimationPipelineCompiler;
import net.quepierts.veynir.core.pipeline.SkeletonPipelineCompiler;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import net.quepierts.veynir.core.util.Prioritized;
import net.quepierts.veynir.core.util.Services;
import org.jspecify.annotations.NonNull;

/**
 * Service provider interface of a rendering backend.
 * <p>
 * Implementations are discovered through {@link Services} and are expected to be driven through
 * {@link Veynir}. Methods are prefixed with {@code _} to mark them as implementation surface.
 */
public interface VeynirBackend extends Prioritized {

    VeynirBackend _BACKEND = Services.load(VeynirBackend.class);

    @Override
    default int priority() {
        return Prioritized.DEFAULT_PRIORITY;
    }

    @NonNull AttributeBufferObject _allocateAttributeBuffer(int size);

    @NonNull AnimationBufferObject _allocateAnimationBuffer(int size);

    @NonNull UniformBufferObject _allocateUniformBuffer(@NonNull UboDefinition definition);

    @NonNull AnimationPipelineCompiler _animationPipelineCompiler();

    @NonNull SkeletonPipelineCompiler _skeletonPipelineCompiler();

}
