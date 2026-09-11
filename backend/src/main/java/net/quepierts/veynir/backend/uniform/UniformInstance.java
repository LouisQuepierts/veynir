package net.quepierts.veynir.backend.uniform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UniformParameter;
import org.jspecify.annotations.NonNull;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class UniformInstance<T extends UniformParameter> {

    private final T                     parameter;
    private final UniformBufferObject   buffer;

    public static <T extends UniformParameter> UniformInstance<T> of(@NonNull final T parameter) {
        return new UniformInstance<>(
                parameter,
                parameter.create()
        );
    }

    public void upload() {
        this.parameter.upload(this.buffer);
    }

}
