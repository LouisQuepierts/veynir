package net.quepierts.veynir.core.memory;

public interface MemoryAccess extends ReadonlyMemoryAccess {
    static MemoryAccess allocate(int size) {
        return FloatMemoryAccess.allocate(size);
    }

    void putInteger(int slot, int value);

    void putInteger(int slot, int[] values);

    void putFloat(int slot, float value);

    void putFloat(int slot, float[] values);

    default void putBoolean(int slot, boolean value) {
        this.putInteger(slot, value ? 1 : 0);
    }

    default void putBoolean(int slot, boolean[] values) {
        for (int i = 0; i < values.length; i++) {
            this.putInteger(slot + i, values[i] ? 1 : 0);
        }
    }
}
