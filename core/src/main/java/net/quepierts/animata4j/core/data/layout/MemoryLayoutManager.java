package net.quepierts.animata4j.core.data.layout;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unused")
public final class MemoryLayoutManager {
    private final MemoryLayoutBehaviour layoutBehaviour;
    private final Map<String, MemoryLayout> layouts;

    public static MemoryLayoutManager of(@NotNull final MemoryLayoutBehaviour behaviour) {
        return new MemoryLayoutManager(behaviour);
    }

    private MemoryLayoutManager(@NotNull final MemoryLayoutBehaviour alignFunction) {
        this.layoutBehaviour = alignFunction;
        this.layouts = Maps.newHashMap();
    }

    @Contract(value = "_ -> _", pure = true)
    public MemoryLayout getLayout(@NotNull final StructDefinition definition) {
        MemoryLayout layout = this.layouts.get(definition.getName());
        if (layout == null) {
            layout = this.buildLayout(definition);
            this.layouts.put(definition.getName(), layout);
        }
        return layout;
    }

    @Contract(value = "_ -> _", pure = true)
    private @Nullable MemoryLayout getLayout(@NotNull final String type) {
        return this.layouts.get(type);
    }

    @Contract(value = "_ -> new", pure = true)
    private MemoryLayout buildLayout(@NotNull final StructDefinition definition) {
        List<FieldDefinition> fields = new ArrayList<>(Arrays.asList(definition.getFields()));

        if (definition.isOptimize()) {
            fields.sort(Comparator.comparingInt(a -> a.getPrimaryType().ordinal()));
        }

        List<MemoryLayout.FieldOffset> fieldOffsets = new ArrayList<>();
        int offset = 0;
        int maxAlignment = 1;

        String firstType = fields.get(fields.size() - 1).getType();
        boolean arrayLike = fields.size() > 1;

        for (final FieldDefinition field : fields) {
            final int baseSize = this.getTypeSize(field);
            final int alignment = this.getAlignment(field);

            final int arrayLength = Math.max(1, field.getLength());
            final int size = this.alignUp(baseSize, alignment) * arrayLength;

            offset = this.alignUp(offset, alignment);

            MemoryLayout.FieldOffset fieldOffset;
            if (field.getPrimaryType().isPrimitive()) {
                fieldOffset = MemoryLayout.FieldOffset.of(
                        field.getName(),
                        offset,
                        size,
                        alignment,
                        arrayLength,
                        null
                );
            } else {
                final MemoryLayout nestedLayout = this.getLayout(field.getType());

                if (nestedLayout == null) {
                    throw new IllegalArgumentException("Unknown type: " + field.getType());
                }

                if (nestedLayout.isWrapped()) {
                    fieldOffset = nestedLayout.unwrap();
                } else {
                    fieldOffset = MemoryLayout.FieldOffset.of(
                            field.getName(),
                            offset,
                            size,
                            alignment,
                            arrayLength,
                            nestedLayout
                    );
                }
            }
            fieldOffsets.add(fieldOffset);

            maxAlignment = Math.max(maxAlignment, alignment);

            if (arrayLike && !field.getType().equals(firstType)) {
                arrayLike = false;
            }
        }

        final int totalSize = this.alignUp(offset, maxAlignment);

        return MemoryLayout.of(
                fieldOffsets.toArray(MemoryLayout.FieldOffset[]::new),
                arrayLike,
                definition.isWrapped(),
                totalSize,
                maxAlignment
        );
    }

    private int getTypeSize(@NotNull final FieldDefinition definition) {
        return this.layoutBehaviour.size(definition, this.layouts::get);
    }

    private int getAlignment(@NotNull final FieldDefinition definition) {
        return this.layoutBehaviour.align(definition, this.layouts::get);
    }

    private int alignUp(final int offset, final int align) {
        return (offset + align - 1) & -align;
    }
}
