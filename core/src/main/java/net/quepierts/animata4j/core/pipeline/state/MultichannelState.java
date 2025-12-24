package net.quepierts.animata4j.core.pipeline.state;

public class MultichannelState extends RuntimeState {

    private final int[] offsets = new int[0];
    private final SourceState[] states = new SourceState[0];

    public int getWriteOffset(int index) {
        return offsets[index];
    }

    // todo: add offset
    public SourceState getSourceState(int index) {
        return states[index];
    }

}
