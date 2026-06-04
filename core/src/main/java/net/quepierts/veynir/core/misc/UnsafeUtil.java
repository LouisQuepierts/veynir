package net.quepierts.veynir.core.misc;

import lombok.experimental.UtilityClass;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@UtilityClass
@SuppressWarnings("unused")
public class UnsafeUtil {
    public static final Unsafe UNSAFE;
    public static final long BYTE_ARRAY_OFFSET;
    public static final long SHORT_ARRAY_OFFSET;
    public static final long INT_ARRAY_OFFSET;
    public static final long LONG_ARRAY_OFFSET;
    public static final long FLOAT_ARRAY_OFFSET;
    public static final long DOUBLE_ARRAY_OFFSET;

    public static long malloc(long size) {
        return UnsafeUtil.malloc(size, (byte) 0);
    }

    public static long malloc(long size, byte value) {
        long address = UNSAFE.allocateMemory(size);
        UNSAFE.setMemory(address, size, value);
        return address;
    }

    public static void free(long address) {
        UNSAFE.freeMemory(address);
    }

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);

            BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
            SHORT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(short[].class);
            INT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(int[].class);
            LONG_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(long[].class);
            FLOAT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(float[].class);
            DOUBLE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(double[].class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
