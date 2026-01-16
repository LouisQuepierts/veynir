package net.quepierts.animata4j.core.pipeline.common;

import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import org.jetbrains.annotations.NotNull;

public interface AnimationContext extends ReadonlyAnimationContext {
    @Override
    @NotNull AnimationReadableTarget getInternalBuffer();
}
