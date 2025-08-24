package net.quepierts.animata4j.core.data.layout;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Getter
public final class FieldDefinition {
    private final AnimataDataType primaryType;
    private final String type;
    private final String name;
    private final int length;

    public static FieldDefinition.Builder primitive(AnimataDataType primaryType) {
        return new FieldDefinition.Builder(primaryType, primaryType.getName());
    }

    public static FieldDefinition.Builder struct(String type) {
        return new FieldDefinition.Builder(AnimataDataType.STRUCT, type);
    }

    private FieldDefinition(AnimataDataType primaryType, String type, String name, int length) {
        this.length = length;
        if (!AnimataDataType.isAvailableType(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        this.primaryType = primaryType;
        this.type = type;
        this.name = name;
    }

    public boolean isSameType(@Nullable final FieldDefinition other) {
        return other != null && this.type.equals(other.type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(name)
                .append(": ")
                .append(type);
        if (length != 1) {
            builder.append("[").append(length).append("]");
        }
        return builder.toString();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final AnimataDataType primaryType;
        private final String type;
        private String name;
        private int length = 1;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public FieldDefinition build() {
            return new FieldDefinition(primaryType, type, name, length);
        }
    }
}
