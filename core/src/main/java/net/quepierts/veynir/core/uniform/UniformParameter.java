package net.quepierts.veynir.core.uniform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.Veynir;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import org.jspecify.annotations.NonNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UniformParameter {

    private final UboDefinition definition;

    public abstract void upload(@NonNull final UniformBufferObject buffer);

    public UniformBufferObject create() {
        return Veynir.uniformBuffer(this.definition);
    }

}
