package net.quepierts.animata4j.core.pipeline.runtime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.program.buffer.AnimationFrameBuffer;
import net.quepierts.animata4j.core.pipeline.drive.AnimationDriver;

@Getter
@RequiredArgsConstructor
public final class AnimationInitializationContext {

    private final AnimationDriver driver;
    private final AnimationFrameBuffer buffer;
    private final MemoryOffsetResolver resolver;

}
