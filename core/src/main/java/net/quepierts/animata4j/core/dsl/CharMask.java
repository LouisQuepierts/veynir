package net.quepierts.animata4j.core.dsl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * Used for matching discrete ascii characters. If the char to be matched is discrete and greater than 5, it is recommended to use it.<br>
 * Not recommended sequences of characters are:<br>
 * {@code "12345678"} {@code "abcdef"} {@code "-=[]"}
 * */
@Log
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CharMask {

    private static final boolean COMPILE_WARM = System.getProperty("animata4j.charmask.warm") != null;
    private static final int SUGGESTION_THRESHOLD = 5;
    private static final int MAX_MASK_SIZE = 2;

    public static CharMask compile(String mask) {
        final long[] bits = new long[MAX_MASK_SIZE];

        short charCount = 0;
        char min = 128;
        char max = 0;

        for (int i = 0; i < mask.length(); i++) {
            final char c = mask.charAt(i);
            if (isNotAscii(c)) {
                throw new IllegalArgumentException("CharMask only supports ASCII characters.");
            }

            final long bitMask = 1L << (i % 64);
            final int idx = i / 64;
            if ((bits[idx] & bitMask) != 1) {
                bits[idx] |= bitMask;
                charCount ++;
            } else {
                continue;
            }

            if (c < min) {
                min = c;
            }

            if (c > max) {
                max = c;
            }
        }

        if (COMPILE_WARM) {
            // too small
            if (charCount < SUGGESTION_THRESHOLD) {
                log.warning("Compiled mask is too small. Suggestion: " + SUGGESTION_THRESHOLD);
            }

            // seem in a linear range
            else if ((max - min + 1) == charCount) {
                log.warning("Compiled mask seems in a linear range. Suggestion: " + (max - min + 1));
            }
        }

        return new CharMask(bits);
    }

    public static CharMask copy(final long[] mask) {
        final int size = Math.min(mask.length, MAX_MASK_SIZE);
        final long[] bits = new long[size];
        System.arraycopy(mask, 0, bits, 0, size);
        return new CharMask(bits);
    }

    private static boolean isNotAscii(char c) {
        return c >= 128;
    }

    private final long[] bits;

    public boolean matches(char c) {
        if (isNotAscii(c)) {
            return false;
        }

        return (bits[c / 64] & (1L << (c % 64))) != 0;
    }
}
