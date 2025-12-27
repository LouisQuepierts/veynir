package net.quepierts.animata4j.core.pipeline.drive;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.pointer.WriteonlyPointer;
import net.quepierts.animata4j.core.pipeline.common.state.RuntimeState;
import net.quepierts.animata4j.core.pipeline.common.state.SourceState;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import net.quepierts.animata4j.core.pipeline.datasource.AnimationSource;
import org.jetbrains.annotations.NotNull;

public class MultichannelDriver implements GenericAnimationDriver<MultichannelDriver.State> {

    private final String[] channels;
    private final AnimationSource[] sources;

    public MultichannelDriver(String[] channels, AnimationSource[] sources) {
        this.channels = channels;
        this.sources = sources;
    }

    @Override
    public State createState(@NotNull MemoryOffsetResolver resolver) {
        final int length = sources.length;
        final boolean[] actives = new boolean[length];
        final int[] offsets = new int[length];
        final SourceState[] states = new SourceState[length];

        for (int i = 0; i < length; i++) {
            int offset = resolver.getOffset(channels[i]);
            boolean active = offset != -1;

            if (active) {
                actives[i] = true;
                offsets[i] = offset;
                states[i] = sources[i].createState();
            }
        }

        return new State(
                actives,
                offsets,
                states
        );
    }

    @Override
    public void update(
            @NotNull AnimationWritableTarget output,
            @NotNull ReadonlyAnimationContext context,
            @NotNull State state
    ) {
        var pointer = WriteonlyPointer.of(output);

        for (int i = 0; i < sources.length; i++) {
            if (!state.isActive(i)) continue;
            var subState = state.getSourceState(i);
            pointer.setOffset(state.getWriteOffset(i));
            sources[i].eval(pointer, context, subState);
        }
    }

    @RequiredArgsConstructor
    public static final class State extends RuntimeState {

        private final boolean[] actives;
        private final int[] offsets;
        private final SourceState[] states;

        public int getWriteOffset(int index) {
            return offsets[index];
        }

        public SourceState getSourceState(int index) {
            return states[index];
        }

        public boolean isActive(int index) {
            return actives[index];
        }

    }
}
