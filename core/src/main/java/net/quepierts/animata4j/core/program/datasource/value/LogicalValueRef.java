package net.quepierts.animata4j.core.program.datasource.value;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.program.datasource.SourceState;

public interface LogicalValueRef {
    float get(int index, SourceState state);

    @RequiredArgsConstructor
    final class Symbolic implements LogicalValueRef {

        private final Scalar[] values;

        @Override
        public float get(int index, SourceState state) {
            return values[index].get(state);
        }
    }

    @RequiredArgsConstructor
    final class Constant implements LogicalValueRef {

        private final float[] values;

        @Override
        public float get(int index, SourceState state) {
            return values[index];
        }
    }

    @RequiredArgsConstructor
    final class Dynamic implements LogicalValueRef {

        private final int[] references;

        @Override
        public float get(int index, SourceState state) {
            return state.readBufferValue(references[index]);
        }
    }

    @RequiredArgsConstructor
    final class Baked implements LogicalValueRef {

        private final float[] values;
        private final boolean[] dynamic;
        private final int[] references;

        @Override
        public float get(int index, SourceState state) {
            return dynamic[index] ? values[index] : state.readBufferValue(references[index]);
        }
    }
}
