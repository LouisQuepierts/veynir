package net.quepierts.animata4j.core.pipeline.datasource;

import net.quepierts.animata4j.core.data.layout.AnimataDataType;
import net.quepierts.animata4j.core.data.writer.StandardMemoryWriter;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

/**
 * The datasource of an animation channel.
 * It is a flyweight object, which means it will be evaluated every frame,
 * and it will not have any state, no matter internal or external state.
 * If you want to store some state, please use {@link net.quepierts.animata4j.core.pipeline.drive.AnimationDriver}.
 * */
public interface AnimationSource {
    /**
     * Evaluate the data of this source.
     * @param target the target to store the data
     * @param context the context of the animation {@link ReadonlyAnimationContext}
     * */
    void eval(
            @NotNull final AnimationWritableTarget target,
            @NotNull final ReadonlyAnimationContext context
    );

    /**
     * Get the duration of this source.
     * @return the duration
     * */
    float getDuration();

    /**
     * Check if this source is finished.
     * @param context the context of the animation {@link ReadonlyAnimationContext}
     * @return true if this source is finished
     * */
    boolean isFinished(
            @NotNull final ReadonlyAnimationContext context
    );
}
