package net.quepierts.veynir.backend.pass;

import net.quepierts.veynir.backend.pipeline.AnimationPipelineCompileContext;
import net.quepierts.veynir.core.pipeline.ComputePassDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Interprets a backend neutral {@link ComputePassDefinition} into an executable {@link ComputePass}
 * by resolving symbolic operand names against the pipeline compile context.
 */
public final class ComputePassCompiler {

    private ComputePassCompiler() {}

    public static ComputePass compile(
            @NotNull final ComputePassDefinition definition,
            @NotNull final AnimationPipelineCompileContext context
    ) {
        final var descriptors   = definition.getOperations();
        final var size          = descriptors.size();

        var operations          = new Operation[size];
        var oids                = new int[size];

        for (int i = 0; i < size; i++) {
            var descriptor      = descriptors.get(i);

            oids[i]             = context.oidObject(descriptor.semantic());

            operations[i]       = switch (descriptor.type()) {
                case SAMPLE -> Operation.sample(
                        context.getSamplerLocation(descriptor.src0()),
                        context.getBufferLocation(descriptor.dst())
                );
                case BLEND_P -> Operation.blend(
                        context.getBufferLocation(descriptor.src0()),
                        context.getBufferLocation(descriptor.src1()),
                        context.getBufferLocation(descriptor.dst()),
                        descriptor.param0()
                );
                case BLEND_A -> Operation.blend(
                        context.getBufferLocation(descriptor.src0()),
                        context.getBufferLocation(descriptor.src1()),
                        context.getBufferLocation(descriptor.dst()),
                        context.getUniformLocation(descriptor.arg0())
                );
                case ASSIGN -> Operation.assign(
                        context.getBufferLocation(descriptor.src0()),
                        context.getBufferLocation(descriptor.dst())
                );
                case CLEAR -> Operation.clear(
                        context.getBufferLocation(descriptor.dst())
                );
                case CUSTOM -> throw new UnsupportedOperationException("Custom operations are not supported yet.");
            };
        }

        return new ComputePass(definition.getName(), operations, oids);
    }

}
