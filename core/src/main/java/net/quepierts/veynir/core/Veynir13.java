package net.quepierts.veynir.core;

import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import org.jspecify.annotations.NonNull;

public interface Veynir13 {

    static @NonNull AnimationBufferObject vyAllocateAnimationBuffer(int size) {
        return VeynirBackend._BACKEND._allocateAnimationBuffer(size);
    }

    static @NonNull AttributeBufferObject vyAllocateAttributeBuffer(int size) {
        return VeynirBackend._BACKEND._allocateAttributeBuffer(size);
    }

    static @NonNull UniformBufferObject vyAllocateUniformBuffer(@NonNull UboDefinition definition) {
        return VeynirBackend._BACKEND._allocateUniformBuffer(definition);
    }

}
