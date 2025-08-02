package net.quepierts.animata4j.core.data.block.accessor;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.block.DataBlock;

@RequiredArgsConstructor
public class BitBooleanAccessor {
    private final DataBlock delegate;

    public boolean getBitBoolean(final long offset, final int shift) {
        byte b = this.delegate.getByte(offset);
        return (b & (1 << shift)) != 0;
    }

    public void getBitBoolean(final long offset, final boolean[] booleans) {
        final int length = booleans.length;
        if (length == 0) {
            return;
        }

        if (length == 1) {
            booleans[0] = this.getBitBoolean(offset, 0);
            return;
        }

        final int size = (length + 7) / 8;
        final byte[] bytes = new byte[size];
        this.delegate.getByte(offset, bytes);

        int j = 0;
        for (int i = 0; i < size; i++) {
            byte b = bytes[i];
            while (j < length) {
                booleans[j] = (b & 1) != 0;
                j++;
                b >>= 1;
            }
        }
    }

    public void putBitBoolean(final long offset, final int shift, final boolean value) {
        byte b = this.delegate.getByte(offset);
        if (value) {
            b |= (byte) (1 << shift);
        } else {
            b &= (byte) ~(1 << shift);
        }
        this.delegate.putByte(offset, b);
    }

    public void putBitBoolean(final long offset, final boolean[] booleans) {
        final int length = booleans.length;
        if (length == 0) return;

        if (length == 1) {
            this.putBitBoolean(offset, 0, booleans[0]);
            return;
        }

        final byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            if (booleans[i]) {
                bytes[i / 8] |= (byte) (1 << (i % 8));
            }
        }

        this.delegate.putByte(offset, bytes);
    }
}
