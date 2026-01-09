package net.quepierts.animata4j.core.pipeline.uniform;

import org.joml.*;

public interface UniformWriter {

    // float 1~4
    void setUniform1f(UniformLocation location, float x);

    void setUniform2f(UniformLocation location, float x, float y);

    void setUniform3f(UniformLocation location, float x, float y, float z);

    void setUniform4f(UniformLocation location, float x, float y, float z, float w);

    void setUniformMatrix2f(UniformLocation location, float[] values);

    void setUniformMatrix3f(UniformLocation location, float[] values);

    void setUniformMatrix4f(UniformLocation location, float[] values);

    void setUniform(UniformLocation location, float[] values);


    // int
    default void setUniform1i(UniformLocation location, int x) {
        setUniform1f(location, (float) x);
    }

    default void setUniform2i(UniformLocation location, int x, int y) {
        setUniform2f(location, x, y);
    }

    default void setUniform3i(UniformLocation location, int x, int y, int z) {
        setUniform3f(location, x, y, z);
    }

    default void setUniform4i(UniformLocation location, int x, int y, int z, int w) {
        setUniform4f(location, x, y, z, w);
    }

    // boolean
    default void setUniform1b(UniformLocation location, boolean x) {
        setUniform1f(location, x ? 1.0f : 0.0f);
    }

    default void setUniform2b(UniformLocation location, boolean x, boolean y) {
        setUniform2f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f);
    }

    default void setUniform3b(UniformLocation location, boolean x, boolean y, boolean z) {
        setUniform3f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f, z ? 1.0f : 0.0f);
    }

    default void setUniform4b(UniformLocation location, boolean x, boolean y, boolean z, boolean w) {
        setUniform4f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f, z ? 1.0f : 0.0f, w ? 1.0f : 0.0f);
    }

    // joml
    default void setUniform2f(UniformLocation location, Vector2fc vector) {
        setUniform2f(location, vector.x(), vector.y());
    }

    default void setUniform3f(UniformLocation location, Vector3fc vector) {
        setUniform3f(location, vector.x(), vector.y(), vector.z());
    }

    default void setUniform4f(UniformLocation location, Vector4fc vector) {
        setUniform4f(location, vector.x(), vector.y(), vector.z(), vector.w());
    }

    default void setUniform4f(UniformLocation location, Quaternionfc quaternion) {
        setUniform4f(location, quaternion.x(), quaternion.y(), quaternion.z(), quaternion.w());
    }

    default void setUniformMatrix2f(UniformLocation location, Matrix2fc matrix) {
        setUniformMatrix2f(location, matrix.get(new float[4]));
    }

    default void setUniformMatrix3f(UniformLocation location, Matrix3fc matrix) {
        setUniformMatrix3f(location, matrix.get(new float[9]));
    }

    default void setUniformMatrix4f(UniformLocation location, Matrix4fc matrix) {
        setUniformMatrix4f(location, matrix.get(new float[16]));
    }

    default void setUniform2i(UniformLocation location, Vector2ic vector) {
        setUniform2i(location, vector.x(), vector.y());
    }

    default void setUniform3i(UniformLocation location, Vector3ic vector) {
        setUniform3i(location, vector.x(), vector.y(), vector.z());
    }

    default void setUniform4i(UniformLocation location, Vector4ic vector) {
        setUniform4i(location, vector.x(), vector.y(), vector.z(), vector.w());
    }

    // string api
    UniformLocation getUniformLocation(String name);

    default void setUniform1f(String name, float x) {
        setUniform1f(getUniformLocation(name), x);
    }

    default void setUniform2f(String name, float x, float y) {
        setUniform2f(getUniformLocation(name), x, y);
    }

    default void setUniform3f(String name, float x, float y, float z) {
        setUniform3f(getUniformLocation(name), x, y, z);
    }

    default void setUniform4f(String name, float x, float y, float z, float w) {
        setUniform4f(getUniformLocation(name), x, y, z, w);
    }
}
