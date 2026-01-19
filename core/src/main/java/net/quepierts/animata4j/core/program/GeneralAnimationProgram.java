package net.quepierts.animata4j.core.program;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.program.pass.AnimationPass;
import net.quepierts.animata4j.core.pipeline.common.AnimationContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class GeneralAnimationProgram implements AnimationProgram {

    private final AnimationPass pass;

    public void execute(
            @NotNull AnimationWritableTarget output,
            @NotNull AnimationContext context
    ) {

        pass.execute(output, context);

    }

    @Override
    public int getRequiredFrameBufferCount() {
        return 0;
    }

    @Override
    public int getRequiredRuntimeDataBufferSize() {
        return 0;
    }
}
