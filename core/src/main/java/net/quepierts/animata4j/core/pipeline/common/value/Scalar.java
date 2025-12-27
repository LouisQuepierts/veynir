package net.quepierts.animata4j.core.pipeline.common.value;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.pipeline.common.state.SourceState;

public interface Scalar {
    float get(SourceState state);

    @RequiredArgsConstructor
    final class Constant implements Scalar {
        private final float value;

        @Override
        public float get(SourceState state) {
            return value;
        }
    }

    @RequiredArgsConstructor
    final class Dynamic implements Scalar {
        private final int index;

        @Override
        public float get(SourceState state) {
            return state.readBufferValue(index);
        }
    }
}
