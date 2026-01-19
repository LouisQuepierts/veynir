package net.quepierts.animata4j.core.pipeline.drive;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.program.driver.compute.ComputeDriver;
import net.quepierts.animata4j.core.program.driver.control.ControlDriver;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class DefaultDriverScheduler implements DriverScheduler {

    private final ControlDriver[] controls;
    private final ComputeDriver[] computes;

    @Override
    public void update(
            @NotNull DriverSchedulerState state,
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context
    ) {
        final var controlContext = state.getControlContext();
        final var computeContext = state.getComputeContext();

        for (ControlDriver control : controls) {
            control.execute(controlContext);
        }

        final var mask = state.getMask();
        for (int i = 0; i < computes.length; i++) {
            if (mask.isEnabled(i)) {
                computes[i].execute(computeContext);
            }
        }

    }
}
