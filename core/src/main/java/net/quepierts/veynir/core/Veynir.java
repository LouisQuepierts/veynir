package net.quepierts.veynir.core;

import lombok.experimental.UtilityClass;
import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
import net.quepierts.veynir.core.pipeline.AnimationPipelineCompiler;
import net.quepierts.veynir.core.pipeline.SkeletonPipelineCompiler;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import org.jspecify.annotations.NonNull;

/**
 * Public entry point to the active backend.
 * <p>
 * All backend interaction is routed through this facade so that the concrete backend
 * (currently the Java fallback, later a native implementation) stays interchangeable.
 */
@UtilityClass
public class Veynir {

    public static @NonNull AttributeBufferObject attributeBuffer(int size) {
        return VeynirBackend._BACKEND._allocateAttributeBuffer(size);
    }

    public static @NonNull AnimationBufferObject animationBuffer(int size) {
        return VeynirBackend._BACKEND._allocateAnimationBuffer(size);
    }

    public static @NonNull UniformBufferObject uniformBuffer(@NonNull UboDefinition definition) {
        return VeynirBackend._BACKEND._allocateUniformBuffer(definition);
    }

    public static @NonNull AnimationPipelineCompiler animationPipelineCompiler() {
        return VeynirBackend._BACKEND._animationPipelineCompiler();
    }

    public static @NonNull SkeletonPipelineCompiler skeletonPipelineCompiler() {
        return VeynirBackend._BACKEND._skeletonPipelineCompiler();
    }

}
