package net.quepierts.veynir.core.memory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FloatMemoryAccess implements MemoryAccess {

    public static final float BOOLEAN_TRUE = 1.0f;
    public static final float BOOLEAN_FALSE = 0.0f;

    public static MemoryAccess allocate(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        return new FloatMemoryAccess(new float[size]);
    }

    private final float[] memory;

    @Override
    public void putInteger(int slot, int value) {
        this.memory[slot] = Float.intBitsToFloat(value);
    }

    @Override
    public void putInteger(int slot, int[] values) {
        for (int i = 0; i < values.length; i++) {
            this.memory[slot + i] = Float.intBitsToFloat(values[i]);
        }
    }

    @Override
    public int getInteger(int slot) {
        return Float.floatToRawIntBits(this.memory[slot]);
    }

    @Override
    public void getInteger(int slot, int[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = Float.floatToRawIntBits(this.memory[slot + i]);
        }
    }

    @Override
    public void putFloat(int slot, float value) {
        this.memory[slot] = value;
    }

    @Override
    public void putFloat(int slot, float[] values) {
        System.arraycopy(values, 0, this.memory, slot, values.length);
    }

    @Override
    public float getFloat(int slot) {
        return this.memory[slot];
    }

    @Override
    public void getFloat(int slot, float[] out) {
        System.arraycopy(this.memory, slot, out, 0, out.length);
    }

    @Override
    public void putBoolean(int slot, boolean value) {
        this.memory[slot] = value ? BOOLEAN_TRUE : BOOLEAN_FALSE;
    }

    @Override
    public void putBoolean(int slot, boolean[] values) {
        for (int i = 0; i < values.length; i++) {
            this.memory[slot + i] = values[i] ? BOOLEAN_TRUE : BOOLEAN_FALSE;
        }
    }

    @Override
    public boolean getBoolean(int slot) {
        return this.memory[slot] == BOOLEAN_TRUE;
    }

    @Override
    public void getBoolean(int slot, boolean[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.memory[slot + i] == BOOLEAN_TRUE;
        }
    }
}
