package net.quepierts.veynir.backend;

import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.buffer.AttributeBuffer;
import net.quepierts.veynir.backend.uniform.UniformBuffer;
import net.quepierts.veynir.core.VeynirBackend;
import net.quepierts.veynir.core.buffer.AnimationBufferObject;
import net.quepierts.veynir.core.buffer.AttributeBufferObject;
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
}
