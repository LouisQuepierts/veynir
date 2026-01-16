package net.quepierts.animata4j.core.pipeline.drive;

import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.state.RuntimeState;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface AnimationDriver {

    /**
     * Update the driver.
     * @param output the output buffer
     * @param context the context of the animation
     * @param state the state of the driver
     */
    @ApiStatus.Internal
    void _update(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context,
            @NotNull RuntimeState state
    );

    /**
     * Create a state for this driver.
     * @return the state
     */
    @ApiStatus.Internal
    @NotNull RuntimeState _createState(@NotNull MemoryOffsetResolver memoryOffsetResolver);

}
