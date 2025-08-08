package net.quepierts.animata4j.core.data.accessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WrappedByteBooleanAccessor implements ByteBooleanAccessor {
    private final ByteAccessor delegate;

    public boolean getByteBoolean(long offset) {
        return this.delegate.getByte(offset) != 0;
    }

    public void getByteBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length) {
        if (length == 0) return;

        if (length == 1) {
            booleans[0] = this.getByteBoolean(offset);
            return;
        }

        final byte[] bytes = new byte[length];
        this.delegate.getByte(offset, bytes, 0, length);
        for (int i = 0; i < length; i++) {
            booleans[i + arrayOffset] = bytes[i] != 0;
        }
    }

    public void putByteBoolean(final long offset, final boolean value) {
        this.delegate.putByte(offset, (byte) (value ? 1 : 0));
    }

    public void putByteBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length) {
        if (length == 0) return;

        if (length == 1) {
            this.delegate.putByte(offset, (byte) (booleans[0] ? 1 : 0));
            return;
        }

        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (booleans[i + arrayOffset] ? 1 : 0);
        }
        this.delegate.putByte(offset, bytes, 0, length);
    }
}
