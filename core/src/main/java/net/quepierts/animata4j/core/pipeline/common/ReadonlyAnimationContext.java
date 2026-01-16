package net.quepierts.animata4j.core.pipeline.common;

import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import org.jetbrains.annotations.NotNull;

public interface ReadonlyAnimationContext {
    float getLocalTime();

    float getDeltaTime();

    int getDirection();

    @NotNull AnimationReadableTarget getInternalBuffer();

}
