package net.quepierts.animata4j.core.program.driver.compute;

import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import net.quepierts.animata4j.core.program.buffer.AnimationFrameBuffer;
import net.quepierts.animata4j.core.program.buffer.RuntimeStateBuffer;
import net.quepierts.animata4j.core.program.channel.ChannelMapper;
import org.jetbrains.annotations.NotNull;

public interface ComputeContext {

    @NotNull AnimationFrameBuffer getFrameBuffer(int id);

    @NotNull RuntimeStateBuffer getRuntimeStateBuffer();

    @NotNull AnimationReadableTarget getDynamicStorageBuffer();

    @NotNull AnimationReadableTarget getParameterBuffer();

    @NotNull ChannelMapper getChannelMapper();

}
