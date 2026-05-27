package net.quepierts.animata4j.backend.uniform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UniformParameter {

    private final UboDefinition definition;

    public abstract void upload(@NotNull final UniformBuffer buffer);

    public UniformBuffer create() {
        return new UniformBuffer(this.definition);
    }

}
