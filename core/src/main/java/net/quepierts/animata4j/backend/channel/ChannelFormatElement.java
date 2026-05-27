package net.quepierts.animata4j.backend.channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

public final class ChannelFormatElement {

    public static final ChannelFormatElement CURSOR = new ChannelFormatElement(0, Type.INT, 1);
    public static final ChannelFormatElement WEIGHT = new ChannelFormatElement(1, Type.FLOAT, 1);
    public static final ChannelFormatElement ENABLED = new ChannelFormatElement(2, Type.BOOL, 1);
    private final int id;
    private final Type type;
    private final int count;

    public ChannelFormatElement(
            int id,
            Type type,
            int count
    ) {
        this.id = id;
        this.type = type;
        this.count = count;
    }

    public int size() {
        return this.type.getSize() * this.count;
    }

    public int id() {
        return id;
    }

    public Type type() {
        return type;
    }

    public int count() {
        return count;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ChannelFormatElement) obj;
        return this.id == that.id &&
                Objects.equals(this.type, that.type) &&
                this.count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, count);
    }

    @Override
    public String toString() {
        return "ChannelFormatElement[" +
                "id=" + id + ", " +
                "type=" + type + ", " +
                "count=" + count + ']';
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        INT(1),
        LONG(2),
        FLOAT(1),
        DOUBLE(2),
        BOOL(1);

        private final int size;
    }
}
