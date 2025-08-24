package net.quepierts.animata4j.core.data.layout;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface MemoryLayoutBehaviour {
    MemoryLayoutBehaviour CPP = new Cpp();
    
    MemoryLayoutBehaviour SINGLE = new Single();

    int align(
            @NotNull final FieldDefinition field,
            @NotNull final MemoryLayoutProvider provider
    );
    
    int size(
            @NotNull final FieldDefinition field,
            @NotNull final MemoryLayoutProvider provider
    );
    
    final class Single implements MemoryLayoutBehaviour {
        @Override
        public int align(@NotNull FieldDefinition field, @NotNull MemoryLayoutProvider provider) {
            return 1;
        }

        @Override
        public int size(@NotNull FieldDefinition field, @NotNull MemoryLayoutProvider provider) {
            final AnimataDataType type = field.getPrimaryType();
            if (type.isPrimitive()) {
                return 1;
            } else {
                final MemoryLayout layout = getMemoryLayout(field, provider);
                return layout.getSize();
            }
        }
    }
    
    final class Cpp implements MemoryLayoutBehaviour {
        @Override
        public int align(@NotNull FieldDefinition field, @NotNull MemoryLayoutProvider provider) {
            final AnimataDataType type = field.getPrimaryType();
            if (type.isPrimitive()) {
                return type.getSize();
            } else {
                final MemoryLayout layout = getMemoryLayout(field, provider);
                return layout.getAlignment();
            }
        }

        @Override
        public int size(@NotNull FieldDefinition field, @NotNull MemoryLayoutProvider provider) {
            final AnimataDataType type = field.getPrimaryType();
            if (type.isPrimitive()) {
                return type.getSize();
            } else {
                final MemoryLayout layout = getMemoryLayout(field, provider);
                return layout.getSize();
            }
        }
    }

    default @NotNull MemoryLayout getMemoryLayout(
            @NotNull FieldDefinition field,
            @NotNull MemoryLayoutProvider provider
    ) {
        final MemoryLayout layout = provider.getLayout(field.getType());
        if (layout == null) {
            throw new IllegalArgumentException("Unknown struct type: " + field.getType());
        }
        return layout;
    }
}
