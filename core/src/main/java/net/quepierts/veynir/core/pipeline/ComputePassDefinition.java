package net.quepierts.veynir.core.pipeline;

import lombok.Getter;
import net.quepierts.veynir.core.misc.Patterns;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Backend neutral descriptor of a compute pass.
 * <p>
 * Carries the pass configuration as symbolic names; a backend interpreter resolves them into an
 * executable pass. Frontends build these through the {@code compute}/{@code sample}/{@code blend}/
 * {@code assign}/{@code semantic} fluent API.
 */
@Getter
public final class ComputePassDefinition implements PassDefinition {

    private final String    name;
    private final PassType  type        = PassType.COMPUTE;
    private final List<OperationDescriptor> operations = new ArrayList<>();

    public ComputePassDefinition(@NonNull final String name) {
        if (!Patterns.PATTERN_IDENTIFIER
                .matcher(name)
                .matches()) {
            throw new IllegalArgumentException("Invalid pass name: " + name);
        }

        this.name = name;
    }

    public static ComputePassDefinition compute(@NonNull final String name) {
        return new ComputePassDefinition(name);
    }

    public ComputePassDefinition sample(
            final String sampler,
            final String buffer
    ) {
        this.operations.add(new OperationDescriptor(
                OperationType.SAMPLE,
                buffer,
                sampler,
                null,
                null,
                null,
                0.0f,
                0.0f,
                null
        ));
        return this;
    }

    public ComputePassDefinition blend(
            final String src0,
            final String src1,
            final String dst,
            final String weight
    ) {
        this.operations.add(new OperationDescriptor(
                OperationType.BLEND_A,
                dst,
                src0,
                src1,
                weight,
                null,
                0.0f,
                0.0f,
                null
        ));
        return this;
    }

    public ComputePassDefinition blend(
            final String src0,
            final String src1,
            final String dst,
            final float weight
    ) {
        this.operations.add(new OperationDescriptor(
                OperationType.BLEND_P,
                dst,
                src0,
                src1,
                null,
                null,
                weight,
                0.0f,
                null
        ));
        return this;
    }

    public ComputePassDefinition assign(
            final String src,
            final String dst
    ) {
        this.operations.add(new OperationDescriptor(
                OperationType.ASSIGN,
                dst,
                src,
                null,
                null,
                null,
                0.0f,
                0.0f,
                null
        ));
        return this;
    }

    public ComputePassDefinition semantic(final String semantic) {
        final int last                  = this.operations.size() - 1;
        final var descriptor            = this.operations.get(last);

        this.operations.set(last, new OperationDescriptor(
                descriptor.type(),
                descriptor.dst(),
                descriptor.src0(),
                descriptor.src1(),
                descriptor.arg0(),
                descriptor.arg1(),
                descriptor.param0(),
                descriptor.param1(),
                semantic
        ));
        return this;
    }

}
