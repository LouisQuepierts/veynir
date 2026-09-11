package net.quepierts.veynir.core.skeleton;

import lombok.Getter;
import lombok.Setter;
import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.model.ParentOverrideConfiguration;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import net.quepierts.veynir.core.uniform.UniformParameter;
import net.quepierts.veynir.core.uniform.UniformType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class ParentOverrideParameter extends UniformParameter {

    public static ParentOverrideParameter of(@NonNull final SkeletonLayout layout) {
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
    public void upload(final @NonNull UniformBufferObject buffer) {
        final var writer = buffer.getRawWriter();

        if (this.data == null) {
            writer.write(this.LOC_ENABLE, 0.0f);
            return;
        }

        writer.write(this.LOC_ENABLE, 1.0f);
//        writer.write(0, this.data.getOrder());
        writer.write(this.ADDR_PARENT, this.data.getParent());
    }

    public void upload(
            @Nullable final ParentOverrideConfiguration configuration,
            @NonNull  final UniformBufferObject         buffer
    ) {
        if (configuration != this.data) {
            this.data = configuration;
            this.upload(buffer);
        }
    }
}
