package net.quepierts.animata4j.core.pipeline.common;

import net.quepierts.animata4j.core.data.accessor.StandardMemoryAccessor;
import org.jetbrains.annotations.NotNull;

public interface AnimationContext extends ReadonlyAnimationContext {
    @Override
    @NotNull StandardMemoryAccessor internalBuffer();
}
