package net.quepierts.animata4j.core.pipeline.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;

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

    static ValueRef dynamic(AnimationReadableTarget buffer, int offset) {
        return new ValueRef.Dynamic(buffer, offset);
    }

    default float getX() {
        return 0;
    }

    default float getY() {
        return 0;
    }

    default float getZ() {
        return 0;
    }

    default float getW() {
        return 0;
    }

    float get(int index);

    @RequiredArgsConstructor
    class Const1 implements ValueRef {

        @Getter
        private final float x;

        @Override
        public float get(int index) {
            return index == 0 ? x : 0;
        }
    }

    @RequiredArgsConstructor
    class Const2 implements ValueRef {

        @Getter
        private final float x, y;

        @Override
        public float get(int index) {
            switch (index) {
                case 0:
                    return x;
                case 1:
                    return y;
                default:
                    return 0;
            }
        }
    }

    @RequiredArgsConstructor
    class Const3 implements ValueRef {

        @Getter
        private final float x, y, z;

        @Override
        public float get(int index) {
            switch (index) {
                case 0:
                    return x;
                case 1:
                    return y;
                case 2:
                    return z;
                default:
                    return 0;
            }
        }
    }

    @RequiredArgsConstructor
    class Const4 implements ValueRef {

        @Getter
        private final float x, y, z, w;

        @Override
        public float get(int index) {
            switch (index) {
                case 0:
                    return x;
                case 1:
                    return y;
                case 2:
                    return z;
                case 3:
                    return w;
                default:
                    return 0;
            }
        }
    }

    @RequiredArgsConstructor
    class Dynamic implements ValueRef {

        private final AnimationReadableTarget buffer;
        private final int offset;

        @Override
        public float getX() {
            return this.buffer.read(offset);
        }

        @Override
        public float getY() {
            return this.buffer.read(offset + 1);
        }

        @Override
        public float getZ() {
            return this.buffer.read(offset + 2);
        }

        @Override
        public float getW() {
            return this.buffer.read(offset + 3);
        }

        @Override
        public float get(int index) {
            return this.buffer.read(offset + index);
        }

    }

    class Empty implements ValueRef {
        @Override
        public float get(int index) {
            return 0;
        }
    }
}
