package net.quepierts.animata4j.core.pipeline.drive;

import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.WritablePointer;
import net.quepierts.animata4j.core.pipeline.datasource.AnimationSource;
import net.quepierts.animata4j.core.pipeline.state.MultichannelState;

public class MultichannelDriver implements AnimationDriver<MultichannelState> {

    private final String[] channels;
    private final AnimationSource[] sources;

    public MultichannelDriver(String[] channels, AnimationSource[] sources) {
        this.channels = channels;
        this.sources = sources;
    }

    @Override
    public MultichannelState createState() {
        return new MultichannelState();
    }

    @Override
    public void update(ReadonlyAnimationContext context, MultichannelState state) {
        var output = context.getOutputBuffer();
        var pointer = WritablePointer.of(output);

        for (int i = 0; i < sources.length; i++) {
            var subState = state.getSourceState(i);
            pointer.setOffset(state.getWriteOffset(i));
            sources[i].eval(pointer, context, subState);
        }
    }

}
