package net.quepierts.veynir.core.pipeline;

import org.jspecify.annotations.Nullable;

/**
 * Backend neutral description of a single compute pass operation.
 * <p>
 * All operands are symbolic names resolved to locations by the backend at compile time.
 */
public record OperationDescriptor(
        OperationType type,

        @Nullable String dst,
        @Nullable String src0,
        @Nullable String src1,

        @Nullable String arg0,
        @Nullable String arg1,

        float param0,
        float param1,

        @Nullable String semantic
) {
}
