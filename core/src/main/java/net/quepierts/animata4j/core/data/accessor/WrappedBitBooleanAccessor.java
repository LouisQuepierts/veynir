package net.quepierts.animata4j.core.data.accessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WrappedBitBooleanAccessor implements BitBooleanAccessor {
    private final ByteAccessor delegate;

    public boolean getBitBoolean(final long offset, final int shift) {
        byte b = this.delegate.getByte(offset);
        return (b & (1 << shift)) != 0;
    }

    public void getBitBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length) {
        if (length == 0) {
            return;
        }

        if (length == 1) {
            booleans[0] = this.getBitBoolean(offset, 0);
            return;
        }

        final int size = (length + 7) / 8;
        final int right = arrayOffset + length;
        final byte[] bytes = new byte[size];
        this.delegate.getByte(offset, bytes);

        int j = arrayOffset;
        for (int i = 0; i < size; i++) {
            byte b = bytes[i];
            while (j < right) {
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

    public void putBitBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length) {
        if (length == 0) return;

        if (length == 1) {
            this.putBitBoolean(offset, 0, booleans[0]);
            return;
        }

        final byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            if (booleans[i + arrayOffset]) {
                bytes[i / 8] |= (byte) (1 << (i % 8));
            }
        }

        this.delegate.putByte(offset, bytes);
    }
}
