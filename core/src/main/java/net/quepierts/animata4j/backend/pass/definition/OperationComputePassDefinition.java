package net.quepierts.animata4j.backend.pass.definition;

import net.quepierts.animata4j.backend.pass.AnimationPass;
import net.quepierts.animata4j.backend.pass.ComputePass;
import net.quepierts.animata4j.backend.pass.Operation;
import net.quepierts.animata4j.backend.pipeline.AnimationPipelineCompileContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class OperationComputePassDefinition extends AnimationPassDefinition {

    private final List<OperationDescription> operations = new ArrayList<>();

    public OperationComputePassDefinition(String name) {
        super(name, PassType.COMPUTE);
    }

    public OperationComputePassDefinition sample(
            String sampler,
            String buffer
    ) {
        this.operations         .add(new OperationDescription(
                Operation.Type.SAMPLE,
                buffer,
                sampler,
                null,
                null,
                null,
                0.0f,
                0.0f,
                new String[1]
        ));
        return this;
    }

    public OperationComputePassDefinition blend(
            String src0,
            String src1,
            String dst,
            String weight
    ) {
        this.operations         .add(new OperationDescription(
                Operation.Type.BLEND_A,
                dst,
                src0,
                src1,
                weight,
                null,
                0.0f,
                0.0f,
                new String[1]
        ));
        return this;
    }

    public OperationComputePassDefinition blend(
            String src0,
            String src1,
            String dst,
            float weight
    ) {
        this.operations         .add(new OperationDescription(
                Operation.Type.BLEND_P,
                dst,
                src0,
                src1,
                null,
                null,
                weight,
                0.0f,
                new String[1]
        ));

        return this;
    }

    public OperationComputePassDefinition assign(
            String src,
            String dst
    ) {
        this.operations         .add(new OperationDescription(
                Operation.Type.ASSIGN,
                dst,
                src,
                null,
                null,
                null,
                0.0f,
                0.0f,
                new String[1]
        ));
        return this;
    }

    public OperationComputePassDefinition semantic(
            String semantic
    ) {
        final var last      = this.operations.get(this.operations.size() - 1);
        last.semantic()[0]  = semantic;
        return this;
    }

    @Override
    public AnimationPass compile(@NotNull AnimationPipelineCompileContext context) {
        final var size      = this.operations.size();
        var operations      = new Operation[size];
        var oids            = new int[size];

        for (int i = 0;
             i < operations.length;
             i++
        ) {
            var operation   = this.operations.get(i);

            oids[i]         = context.oidObject(operation.semantic()[0]);

            switch (operation.type()) {
                case SAMPLE: {
                    operations[i]   = Operation.sample(
                            context.getSamplerLocation(operation.src0()),
                            context.getBufferLocation(operation.dst())
                    );
                    break;
                }
                case BLEND_P: {
                    operations[i]   = Operation.blend(
                            context.getBufferLocation(operation.src0()),
                            context.getBufferLocation(operation.src1()),
                            context.getBufferLocation(operation.dst()),
                            operation.param0()
                    );
                    break;
                }
                case BLEND_A: {
                    operations[i]   = Operation.blend(
                            context.getBufferLocation(operation.src0()),
                            context.getBufferLocation(operation.src1()),
                            context.getBufferLocation(operation.dst()),
                            context.getUniformLocation(operation.arg0())
                    );
                }
                case ASSIGN: {
                    operations[i]   = Operation.assign(
                            context.getBufferLocation(operation.src0()),
                            context.getBufferLocation(operation.dst())
                    );
                }
                case CLEAR: {
                    operations[i]   = Operation.clear(
                            context.getBufferLocation(operation.dst())
                    );
                }
                case CUSTOM:{
                    throw new UnsupportedOperationException("Custom operations are not supported yet.");
                }
            };
        }

        return new ComputePass(this.getName(), operations, oids);
    }

    private static final class OperationDescription {
        private final Operation.Type type;
        private final String dst;
        private final String src0;
        private final String src1;
        private final String arg0;
        private final String arg1;
        private final float param0;
        private final float param1;
        private final String[] semantic;

        private OperationDescription(
                Operation.Type type,

                String dst,
                String src0,
                String src1,

                String arg0,
                String arg1,

                float param0,
                float param1,

                String[] semantic
        ) {
            this.type = type;
            this.dst = dst;
            this.src0 = src0;
            this.src1 = src1;
            this.arg0 = arg0;
            this.arg1 = arg1;
            this.param0 = param0;
            this.param1 = param1;
            this.semantic = semantic;
        }

        public Operation.Type type() {
            return type;
        }

        public String dst() {
            return dst;
        }

        public String src0() {
            return src0;
        }

        public String src1() {
            return src1;
        }

        public String arg0() {
            return arg0;
        }

        public String arg1() {
            return arg1;
        }

        public float param0() {
            return param0;
        }

        public float param1() {
            return param1;
        }

        public String[] semantic() {
            return semantic;
        }


    }
}
