package net.quepierts.animata4j.core.data.block;

import net.quepierts.animata4j.core.data.accessor.*;
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public interface MemoryBlock extends AutoCloseable, StandardMemoryAccessor {
    long DEFAULT_SIZE = 2 << 9;
    long THRESHOLD_USE_SEG = 2 << 5 - 1;

    @Contract(value = "-> new", pure = true)
    static MemoryBlock create() {
        return DirectMemoryBlock.create();
    }

    @Contract(value = "_ -> new", pure = true)
    static MemoryBlock create(long size) {
        return DirectMemoryBlock.create(size);
    }

    void free();

    long size();

    void expand(long size);

    boolean isFreed();

    @Override
    default void close() {
        this.free();
    }

    default MemoryBlock slice(long offset) {
        return new SlicedMemoryBlock(this, offset);
    }
}
