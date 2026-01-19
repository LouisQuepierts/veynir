package net.quepierts.animata4j.core.program.sampler;

import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.program.driver.compute.ComputeContext;
import org.jetbrains.annotations.NotNull;

public interface Sampler1D {

    void sample(
            @NotNull AnimationWritableTarget target,
            @NotNull ComputeContext context);

    int getRequiredDynamicMemorySize();

    int getChannelAmount();

}
