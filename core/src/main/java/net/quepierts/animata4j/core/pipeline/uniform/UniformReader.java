package net.quepierts.animata4j.core.pipeline.uniform;

import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;

@SuppressWarnings("unused")
public interface UniformReader {
    // float

    /**
     * Get uniform value
     * @param location location
     * @return float value
     */
    float getUniform1f(UniformLocation location);

    void getUniformf(UniformLocation location, AnimationWritableTarget dst);

    void memcpy(AnimationWritableTarget dst);

    void memcpy(AnimationWritableTarget dst, int offset);

    // int
    default int getUniform1i(UniformLocation location) {
        return (int) getUniform1f(location);
    }

    // boolean
    default boolean getUniform1b(UniformLocation location) {
        return getUniform1f(location) == 1.0f;
    }

    // string api
    UniformLocation getUniformLocation(String name);
}
