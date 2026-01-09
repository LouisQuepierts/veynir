package net.quepierts.animata4j.core.pipeline.uniform;

import org.jetbrains.annotations.NotNull;

public interface UniformLayoutBehaviour {

    int SCALAR_SIZE = 1;
    int VECTOR_SIZE = 4;

    UniformLayoutBehaviour COMPACT = new UniformLayoutBehaviour() {
        @Override
        public int align(@NotNull UniformType type, int length, int base) {
            return _align(base, 1);
        }

        @Override
        public int size(@NotNull UniformType type, int size, int length) {
            return size * length;
        }
    };

    UniformLayoutBehaviour STD140 = new UniformLayoutBehaviour() {
        @Override
        public int align(@NotNull UniformType type, int length, int base) {
            if (length > 1 || !type.isScalar()) {
                return _align(base, VECTOR_SIZE);
            } else {
                return _align(base, SCALAR_SIZE);
            }
        }

        @Override
        public int size(@NotNull UniformType type, int size, int length) {
            if (length > 1) {
                return _align(size, VECTOR_SIZE) * length;
            }

            return _align(size, type.isScalar() ? SCALAR_SIZE : VECTOR_SIZE);
        }
    };

    static int _align(int value, int alignment) {
        return (value + alignment - 1) & -alignment;
    }

    int align(@NotNull UniformType type, int length, int base);

    int size(@NotNull UniformType type, int size, int length);

}
