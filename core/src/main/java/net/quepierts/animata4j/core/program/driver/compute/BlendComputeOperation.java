package net.quepierts.animata4j.core.program.driver.compute;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class BlendComputeOperation implements ComputeDriver {

    private final int input1Id;
    private final int input2Id;
    private final int outputId;

    private final int weightOffset;

    @Override
    public void execute(@NotNull ComputeContext context) {
        final var input1 = context.getFrameBuffer(input1Id);
        final var input2 = context.getFrameBuffer(input2Id);
        final var output = context.getFrameBuffer(outputId);
        var weight = getWeight(context);

        output.copy(input1);
        output.mix(input2, weight);
    }

    private float getWeight(@NotNull ComputeContext context) {
        return context.getParameterBuffer().read(weightOffset);
    }
}
