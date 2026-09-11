package net.quepierts.veynir.core.skeleton;

import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.pipeline.UniformBufferObject;
import net.quepierts.veynir.core.uniform.UboDefinition;
import net.quepierts.veynir.core.uniform.UniformParameter;
import org.jspecify.annotations.NonNull;

public final class PivotModificationParameter extends UniformParameter {

    public static PivotModificationParameter of(@NonNull final SkeletonLayout layout) {
        final var definition = layout.toPivotDefinition();
        return new PivotModificationParameter(layout, definition);
    }

    private final SkeletonLayout    layout;
    private final float[]           pivots;

    private PivotModificationParameter(
            final @NonNull SkeletonLayout   layout,
            final @NonNull UboDefinition    definition
    ) {
        super(definition);
        this.layout = layout;
        this.pivots = new float[layout.size() << 2];
    }

    public void set(String name, float x, float y, float z) {
        final var id = this.layout.id(name);
        if (id != -1) {
            final var base = id << 2;
            this.pivots[base] = x;
            this.pivots[base + 1] = y;
            this.pivots[base + 2] = z;
        }
    }

    public void enable(String name, boolean enable) {
        final var id = this.layout.id(name);
        if (id != -1) {
            final var base = id << 2;
            this.pivots[base + 3] = enable ? 1.0f : 0.0f;
        }
    }

    @Override
    public void upload(final @NonNull UniformBufferObject buffer) {
        final var writer = buffer.getRawWriter();
        writer.write(0, this.pivots);
    }
}
