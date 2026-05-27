package net.quepierts.animata4j.backend.model;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@RequiredArgsConstructor
public final class Timeline {

    public static final byte INTERPOLATION_LINER        = (byte) 0;
    public static final byte INTERPOLATION_CATMULLROM   = (byte) 1;
    public static final byte INTERPOLATION_CONSTANT     = (byte) 2;
    private final float[] starts;
    private final float[] ends;
    private final int[] addr0;
    private final int[] addr1;
    private final byte[] interpolation;
    private final int size;

    public float[] starts() {
        return starts;
    }

    public float[] ends() {
        return ends;
    }

    public int[] addr0() {
        return addr0;
    }

    public int[] addr1() {
        return addr1;
    }

    public byte[] interpolation() {
        return interpolation;
    }

    public int size() {
        return size;
    }

    @UtilityClass
    public static class Address {
        static final int PARAMETER_BIT = 0x80000000;

        public static boolean isParameter(int address) {
            return (address & PARAMETER_BIT) != 0;
        }

        public static int offset(int address) {
            return address & ~PARAMETER_BIT;
        }
    }

}
