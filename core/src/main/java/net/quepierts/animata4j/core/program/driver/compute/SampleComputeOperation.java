package net.quepierts.animata4j.core.program.driver.compute;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.program.sampler.Sampler1D;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class SampleComputeOperation implements ComputeDriver {

    private final Sampler1D sampler;
    private final int outputId;

    @Override
    public void execute(@NotNull ComputeContext context) {
        final var target = context.getFrameBuffer(outputId);
        sampler.sample(target, context);
    }
}
