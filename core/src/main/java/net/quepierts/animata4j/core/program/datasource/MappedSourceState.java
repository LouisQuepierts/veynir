package net.quepierts.animata4j.core.program.datasource;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import net.quepierts.animata4j.core.program.buffer.RuntimeStateBuffer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MappedSourceState implements SourceState {

    private final @NotNull RuntimeStateBuffer runtimeStateBuffer;
    private final @NotNull AnimationReadableTarget dynamicStorageBuffer;

    private int sourceId;

    public void bind(int id) {
        this.sourceId = id;
    }

    @Override
    public int getCursor() {
        return Float.floatToIntBits(runtimeStateBuffer.read(sourceId));
    }

    @Override
    public void setCursor(int cursor) {
        runtimeStateBuffer.write(sourceId, Float.intBitsToFloat(cursor));
    }

    @Override
    public float readBufferValue(int index) {
        return dynamicStorageBuffer.read(index);
    }
}
