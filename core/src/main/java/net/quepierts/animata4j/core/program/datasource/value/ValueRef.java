package net.quepierts.animata4j.core.program.datasource.value;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.program.datasource.SourceState;
import org.jetbrains.annotations.NotNull;

public interface ValueRef {

    static ValueRef create(float[] value) {
        switch (value.length) {
            case 1:
                return new ValueRef.Const1(value[0]);
            case 2:
                return new ValueRef.Const2(value[0], value[1]);
            case 3:
                return new ValueRef.Const3(value[0], value[1], value[2]);
            case 4:
                return new ValueRef.Const4(value[0], value[1], value[2], value[3]);
            default:
                throw new UnsupportedOperationException();
        }
    }

    static ValueRef empty() {
        return new ValueRef.Empty();
    }

    static ValueRef dynamic(int offset) {
        return new ValueRef.Dynamic(offset);
    }

    default float getX(@NotNull SourceState state) {
        return 0;
    }

    default float getY(@NotNull SourceState state) {
        return 0;
    }

    default float getZ(@NotNull SourceState state) {
        return 0;
    }

    default float getW(@NotNull SourceState state) {
        return 0;
    }

    @RequiredArgsConstructor
    class Const1 implements ValueRef {

        private final float x;

        @Override
        public float getX(@NotNull SourceState state) {
            return x;
        }
    }

    @RequiredArgsConstructor
    class Const2 implements ValueRef {

        private final float x, y;

        @Override
        public float getX(@NotNull SourceState state) {
            return x;
        }

        @Override
        public float getY(@NotNull SourceState state) {
            return y;
        }
    }

    @RequiredArgsConstructor
    class Const3 implements ValueRef {

        private final float x, y, z;

        @Override
        public float getX(@NotNull SourceState state) {
            return x;
        }

        @Override
        public float getY(@NotNull SourceState state) {
            return y;
        }

        @Override
        public float getZ(@NotNull SourceState state) {
            return z;
        }
    }

    @RequiredArgsConstructor
    class Const4 implements ValueRef {

        private final float x, y, z, w;

        @Override
        public float getX(@NotNull SourceState state) {
            return x;
        }

        @Override
        public float getY(@NotNull SourceState state) {
            return y;
        }

        @Override
        public float getZ(@NotNull SourceState state) {
            return z;
        }

        @Override
        public float getW(@NotNull SourceState state) {
            return w;
        }
    }

    @RequiredArgsConstructor
    class Dynamic implements ValueRef {

        private final int offset;

        @Override
        public float getX(@NotNull SourceState state) {
            return state.readBufferValue(offset);
        }

        @Override
        public float getY(@NotNull SourceState state) {
            return state.readBufferValue(offset + 1);
        }

        @Override
        public float getZ(@NotNull SourceState state) {
            return state.readBufferValue(offset + 2);
        }

        @Override
        public float getW(@NotNull SourceState state) {
            return state.readBufferValue(offset + 3);
        }
    }

    class Empty implements ValueRef {
    }
}
