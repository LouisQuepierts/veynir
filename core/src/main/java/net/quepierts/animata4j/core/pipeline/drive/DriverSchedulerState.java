package net.quepierts.animata4j.core.pipeline.drive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.program.driver.DriverMask;
import net.quepierts.animata4j.core.program.driver.compute.ComputeContext;
import net.quepierts.animata4j.core.program.driver.control.ControlContext;

@Getter
@RequiredArgsConstructor
public final class DriverSchedulerState {

    private final DriverMask mask;

    private final ControlContext controlContext;
    private final ComputeContext computeContext;

}
