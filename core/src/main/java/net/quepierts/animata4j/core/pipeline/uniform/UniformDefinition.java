package net.quepierts.animata4j.core.pipeline.uniform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.layout.MemoryLayout;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UniformDefinition {

    @Getter
    private final int size;
    final Entry[] entries;

    public @NotNull UniformLayout resolve(UniformLayoutBehaviour behaviour) {
        UniformLocation[] locations = new UniformLocation[entries.length];
        int totalSize = 0;

        for (Entry entry : entries) {
            int offset = behaviour.align(entry.type, entry.length, totalSize);
            int size = behaviour.size(entry.type, entry.size, entry.length);

            locations[entry.location] = new UniformLocation(
                    entry.name,
                    entry.location,
                    offset,
                    size,
                    entry.length,
                    entry.type
            );

            totalSize = offset + size;
        }

        return new UniformLayout(locations, totalSize);
    }

    public static final class Builder {

        private final Set<String> names = new HashSet<>();
        private final List<Entry> entries = new ArrayList<>();

        public Builder simple(UniformType type, String name) {
            return this.simple(-1, type, name);
        }

        public Builder simple(int location, UniformType type, String name) {
            if (type == UniformType.STRUCT) {
                throw new IllegalArgumentException("Array / Struct uniform must use specified function");
            }

            if (this.names.contains(name)) {
                throw new IllegalArgumentException("Duplicate uniform name: " + name);
            }

            if (location < 0) {
                throw new IllegalArgumentException("Location must be specified for non-array uniform");
            }

            this.names.add(name);
            this.entries.add(new Entry(type, name, location, type.getSize(), 1));
            return this;
        }

        public Builder array(UniformType componentType, String name, int length) {
            return this.array(-1, name, componentType, length);
        }

        public Builder array(int location, String name, UniformType componentType, int length) {
            if (componentType == UniformType.STRUCT) { // only simple component
                throw new IllegalArgumentException("Array component must be simple type");
            }

            if (this.names.contains(name)) {
                throw new IllegalArgumentException("Duplicate uniform name: " + name);
            }

            if (location < 0) {
                throw new IllegalArgumentException("Location must be specified for non-array uniform");
            }

            this.names.add(name);
            this.entries.add(new Entry(componentType, name, -1, componentType.getSize(), length));
            return this;
        }

        public Builder struct(MemoryLayout struct, String name, int location) {
            if (this.names.contains(name)) {
                throw new IllegalArgumentException("Duplicate uniform name: " + name);
            }

            if (location < 0) {
                throw new IllegalArgumentException("Location must be specified for non-array uniform");
            }

            this.names.add(name);
            this.entries.add(new Entry(UniformType.STRUCT, name, location, struct.getSize(), 1));
            return this;
        }

        public UniformDefinition build() {
            int size = 0;
            for (Entry entry : this.entries) {
                size += entry.type.getSize();
            }

            // sort by location
            // if location != -1, then index is location
            // or else, should be in order

            // all location == -1 in nonSpecified
            // location != -1 in array
            List<Entry> nonSpecified = new ArrayList<>();
            Entry[] array = new Entry[this.entries.size()];
            for (Entry entry : this.entries) {
                int location = entry.location;
                if (location == -1) {
                    nonSpecified.add(entry);
                } else if (array[location] == null) {
                    array[location] = entry.duplicate();
                } else {
                    throw new IllegalArgumentException("Duplicate uniform location: " + location);
                }
            }

            int idx = 0;
            for (Entry entry : nonSpecified) {
                while (array[idx] != null) {
                    idx++;
                }
                array[idx] = entry.location(idx);
                idx++;
            }

            return new UniformDefinition(size, array);
        }

    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Entry {
        @Getter private final UniformType type;
        private final String name;

        private final int location;

        @Getter private final int size;
        @Getter private final int length;

        public Entry duplicate() {
            return new Entry(type, name, location, size, length);
        }

        public Entry location(int location) {
            return new Entry(type, name, location, size, length);
        }

        public boolean isArray() {
            return length > 1;
        }
    }

}
