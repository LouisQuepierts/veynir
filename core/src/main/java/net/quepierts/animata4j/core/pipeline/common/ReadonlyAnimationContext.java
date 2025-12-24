package net.quepierts.animata4j.core.pipeline.common;

import net.quepierts.animata4j.core.data.reader.StandardMemoryReader;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

public interface ReadonlyAnimationContext {
    float getDeltaTime();

    @NotNull StandardMemoryReader getInternalBuffer();

    @NotNull AnimationWritableTarget getOutputBuffer();
}
