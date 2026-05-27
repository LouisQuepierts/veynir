package net.quepierts.animata4j.core.skeleton;

import lombok.Getter;
import lombok.Setter;
import net.quepierts.animata4j.backend.skeleton.SkeletonLayout;
import net.quepierts.animata4j.backend.uniform.UboDefinition;
import net.quepierts.animata4j.backend.uniform.UniformBuffer;
import net.quepierts.animata4j.backend.uniform.UniformParameter;
import net.quepierts.animata4j.backend.uniform.UniformType;
import net.quepierts.animata4j.core.model.ParentOverrideConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ParentOverrideParameter extends UniformParameter {

    public static ParentOverrideParameter of(@NotNull final SkeletonLayout layout) {
        final var size = layout.size();
        final var definition = UboDefinition.builder()
//                .withArray("order", UniformType.INT, size)
                .withUniform("enable", UniformType.BOOL)
                .withArray("parent", UniformType.INT, size)
                .build();
        return new ParentOverrideParameter(definition);
    }

    private final int ADDR_PARENT;
    private final int LOC_ENABLE;

    @Setter
    @Getter
    private ParentOverrideConfiguration data;

    private ParentOverrideParameter(final UboDefinition definition) {
        super(definition);

        final var locParent = definition.getUniformLocation("parent");
        this.ADDR_PARENT    = definition.getUniformOffset(locParent);
        this.LOC_ENABLE     = definition.getUniformLocation("enable");
    }

    @Override
    public void upload(final @NotNull UniformBuffer buffer) {
        if (this.data == null) {
            buffer.write(this.LOC_ENABLE, 0.0f);
            return;
        }

        buffer.write(this.LOC_ENABLE, 1.0f);
        final var writer = buffer.getRawWriter();
//        writer.write(0, this.data.getOrder());
        writer.write(this.ADDR_PARENT, this.data.getParent());
    }

    public void upload(
            @Nullable final ParentOverrideConfiguration configuration,
            @NotNull  final UniformBuffer               buffer
    ) {
        if (configuration != this.data) {
            this.data = configuration;
            this.upload(buffer);
        }
    }
}
