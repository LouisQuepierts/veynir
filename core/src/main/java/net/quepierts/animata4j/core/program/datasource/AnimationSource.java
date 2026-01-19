package net.quepierts.animata4j.core.program.datasource;

import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The datasource of an animation channel.
 * It is a flyweight object, which means it will be evaluated every frame,
 * and it will not have any state, no matter internal or external state.
 * */
public interface AnimationSource {
    /**
     * Evaluate the data of this source.
     * @param target the target to store the data
     * @param context the context of the animation {@link ReadonlyAnimationContext}
     * */
    void eval(
            @NotNull final AnimationWritableTarget target,
            @NotNull final ReadonlyAnimationContext context,
            @NotNull final SourceState state
    );

    /**
     * Get the duration of this source.
     * @return the duration
     * */
    @Contract(pure = true)
    float getDuration();

    /**
     * Get the component count of this source.
     * @return the component count
     * */
    @Contract(pure = true)
    int getComponentCount();

    /**
     * Check if this source is finished.
     * @param context the context of the animation {@link ReadonlyAnimationContext}
     * @return {@code true} if this source is finished
     * */
    @Contract(pure = true)
    boolean isFinished(
            @NotNull final ReadonlyAnimationContext context
    );

    /**
     * Create a state for this source.
     *
     * @return the state
     *
     */
    @Contract(pure = true, value = "-> new")
    @NotNull SourceState createState();
}
