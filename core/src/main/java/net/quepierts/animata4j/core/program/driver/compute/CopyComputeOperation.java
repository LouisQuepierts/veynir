package net.quepierts.animata4j.core.program.driver.compute;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class CopyComputeOperation implements ComputeDriver {

    private final int inputId;
    private final int outputId;

    @Override
    public void execute(@NotNull ComputeContext context) {
        final var input = context.getFrameBuffer(inputId);
        final var output = context.getFrameBuffer(outputId);
        output.copy(input);
    }
}
