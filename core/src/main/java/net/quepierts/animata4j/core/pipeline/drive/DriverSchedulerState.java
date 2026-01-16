package net.quepierts.animata4j.core.pipeline.drive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class DriverSchedulerState {

    private final DriverMask mask;

    private final ControlContext controlContext;
    private final ComputeContext computeContext;

}
