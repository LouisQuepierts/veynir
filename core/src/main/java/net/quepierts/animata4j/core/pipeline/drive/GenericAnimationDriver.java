package net.quepierts.animata4j.core.pipeline.drive;

import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.state.RuntimeState;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GenericAnimationDriver<TState extends RuntimeState> extends AnimationDriver {

    @Override
    @SuppressWarnings("unchecked")
    default void _update(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context,
            @NotNull RuntimeState state
    ) {
        this.update(output, context, (TState) state);
    }

    @Override
    default @NotNull RuntimeState _createState(@NotNull MemoryOffsetResolver memoryOffsetResolver) {
        return this.createState(memoryOffsetResolver);
    }

    /**
     * Update the driver.
     * @param context the context of the animation {@link ReadonlyAnimationContext}
     * @param state the state of the driver
     */
    void update(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context,
            @NotNull TState state
    );

    /**
     * Create a state for this driver.
     * @return the state
     */
    @Contract(value = "_ -> new", pure = true)
    TState createState(@NotNull MemoryOffsetResolver memoryOffsetResolver);

}
