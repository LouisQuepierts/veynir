package net.quepierts.animata4j.core.program.sampler;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.buffer.pointer.WritablePointer;
import net.quepierts.animata4j.core.program.datasource.AnimationSource;
import net.quepierts.animata4j.core.program.datasource.MappedSourceState;
import net.quepierts.animata4j.core.program.datasource.SourceState;
import net.quepierts.animata4j.core.program.driver.compute.ComputeContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class MultichannelSampler implements Sampler1D {

    private final int[] channelIds;
    private final AnimationSource[] sources;
    private final SourceState[] states;

    @Override
    public void sample(
            @NotNull AnimationWritableTarget target,
            @NotNull ComputeContext context
    ) {
        final var mapper = context.getChannelMapper();
        final var pointer = WritablePointer.of(target);
        final var state = new MappedSourceState(context.getRuntimeStateBuffer(), context.getDynamicStorageBuffer());

        for (int i = 0; i < sources.length; i++) {
            int offset = mapper.getOffset(channelIds[i]);

            if (offset != -1) {
                pointer.setOffset(offset);
            }
        }
    }

    @Override
    public int getRequiredDynamicMemorySize() {
        return 0;
    }

    @Override
    public int getChannelAmount() {
        return sources.length;
    }
}
