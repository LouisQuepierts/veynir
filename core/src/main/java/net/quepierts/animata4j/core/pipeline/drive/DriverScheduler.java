package net.quepierts.animata4j.core.pipeline.drive;

import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

public interface DriverScheduler {

    void update(
            @NotNull DriverSchedulerState state,
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context
    );

}
