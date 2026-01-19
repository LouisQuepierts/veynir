package net.quepierts.animata4j.core.program.pass;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.program.driver.compute.ComputeDriver;
import net.quepierts.animata4j.core.program.driver.control.ControlDriver;
import org.jetbrains.annotations.NotNull;

/**
 * it will be separate into different passes</br>
 * instead of resolve all these operations in one pass
 */
@RequiredArgsConstructor
public final class DefaultPass implements AnimationPass {

    private final ControlDriver[] controls;
    private final ComputeDriver[] computes;

    @Override
    public void execute(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context
    ) {

    }

    @Override
    public int getRequiredAnimationStorageBufferSize() {
        return 0;
    }

    @Override
    public int getRequireBufferSize() {
        return 0;
    }

    @Override
    public int getPassId() {
        return 0;
    }
}
