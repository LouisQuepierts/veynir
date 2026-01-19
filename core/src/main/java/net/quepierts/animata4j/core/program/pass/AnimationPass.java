package net.quepierts.animata4j.core.program.pass;

import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import org.jetbrains.annotations.NotNull;

public interface AnimationPass {
    void execute(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context
    );

    int getRequiredAnimationStorageBufferSize();

    int getRequireBufferSize();

    int getPassId();
}
