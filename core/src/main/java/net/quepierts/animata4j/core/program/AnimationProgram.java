package net.quepierts.animata4j.core.program;

import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.pipeline.common.AnimationContext;
import org.jetbrains.annotations.NotNull;

public interface AnimationProgram {

    void execute(
            @NotNull AnimationWritableTarget output,
            @NotNull AnimationContext context
    );

    int getRequiredFrameBufferCount();

    int getRequiredRuntimeDataBufferSize();

}
