package net.quepierts.animata4j.core.memory;

public interface ReadonlyMemoryAccess {

    int getInteger(int slot);

    void getInteger(int slot, int[] out);

    float getFloat(int slot);

    void getFloat(int slot, float[] out);

    default boolean getBoolean(int slot) {
        return this.getInteger(slot) != 0;
    }

    default void getBoolean(int slot, boolean[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getInteger(slot + i) != 0;
        }
    }
}
