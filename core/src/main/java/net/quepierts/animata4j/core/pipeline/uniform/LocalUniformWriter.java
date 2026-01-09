package net.quepierts.animata4j.core.pipeline.uniform;

import org.joml.*;

public interface LocalUniformWriter {

    // float 1~4
    void setLocalUniform1f(UniformLocation location, float x);

    void setLocalUniform2f(UniformLocation location, float x, float y);

    void setLocalUniform3f(UniformLocation location, float x, float y, float z);

    void setLocalUniform4f(UniformLocation location, float x, float y, float z, float w);

    void setLocalUniformMatrix2f(UniformLocation location, float[] values);

    void setLocalUniformMatrix3f(UniformLocation location, float[] values);

    void setLocalUniformMatrix4f(UniformLocation location, float[] values);

    void setLocalUniform(UniformLocation location, float[] values);


    // int
    default void setLocalUniform1i(UniformLocation location, int x) {
        setLocalUniform1f(location, (float) x);
    }

    default void setLocalUniform2i(UniformLocation location, int x, int y) {
        setLocalUniform2f(location, x, y);
    }

    default void setLocalUniform3i(UniformLocation location, int x, int y, int z) {
        setLocalUniform3f(location, x, y, z);
    }

    default void setLocalUniform4i(UniformLocation location, int x, int y, int z, int w) {
        setLocalUniform4f(location, x, y, z, w);
    }

    // boolean
    default void setLocalUniform1b(UniformLocation location, boolean x) {
        setLocalUniform1f(location, x ? 1.0f : 0.0f);
    }

    default void setLocalUniform2b(UniformLocation location, boolean x, boolean y) {
        setLocalUniform2f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f);
    }

    default void setLocalUniform3b(UniformLocation location, boolean x, boolean y, boolean z) {
        setLocalUniform3f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f, z ? 1.0f : 0.0f);
    }

    default void setLocalUniform4b(UniformLocation location, boolean x, boolean y, boolean z, boolean w) {
        setLocalUniform4f(location, x ? 1.0f : 0.0f, y ? 1.0f : 0.0f, z ? 1.0f : 0.0f, w ? 1.0f : 0.0f);
    }

    // joml
    default void setLocalUniform2f(UniformLocation location, Vector2fc vector) {
        setLocalUniform2f(location, vector.x(), vector.y());
    }

    default void setLocalUniform3f(UniformLocation location, Vector3fc vector) {
        setLocalUniform3f(location, vector.x(), vector.y(), vector.z());
    }

    default void setLocalUniform4f(UniformLocation location, Vector4fc vector) {
        setLocalUniform4f(location, vector.x(), vector.y(), vector.z(), vector.w());
    }

    default void setLocalUniform4f(UniformLocation location, Quaternionfc quaternion) {
        setLocalUniform4f(location, quaternion.x(), quaternion.y(), quaternion.z(), quaternion.w());
    }

    default void setLocalUniformMatrix2f(UniformLocation location, Matrix2fc matrix) {
        setLocalUniformMatrix2f(location, matrix.get(new float[4]));
    }

    default void setLocalUniformMatrix3f(UniformLocation location, Matrix3fc matrix) {
        setLocalUniformMatrix3f(location, matrix.get(new float[9]));
    }

    default void setLocalUniformMatrix4f(UniformLocation location, Matrix4fc matrix) {
        setLocalUniformMatrix4f(location, matrix.get(new float[16]));
    }

    default void setLocalUniform2i(UniformLocation location, Vector2ic vector) {
        setLocalUniform2i(location, vector.x(), vector.y());
    }

    default void setLocalUniform3i(UniformLocation location, Vector3ic vector) {
        setLocalUniform3i(location, vector.x(), vector.y(), vector.z());
    }

    default void setLocalUniform4i(UniformLocation location, Vector4ic vector) {
        setLocalUniform4i(location, vector.x(), vector.y(), vector.z(), vector.w());
    }

    // string api
    UniformLocation getUniformLocation(String name);

    default void setLocalUniform1f(String name, float x) {
        setLocalUniform1f(getUniformLocation(name), x);
    }

    default void setLocalUniform2f(String name, float x, float y) {
        setLocalUniform2f(getUniformLocation(name), x, y);
    }

    default void setLocalUniform3f(String name, float x, float y, float z) {
        setLocalUniform3f(getUniformLocation(name), x, y, z);
    }

    default void setLocalUniform4f(String name, float x, float y, float z, float w) {
        setLocalUniform4f(getUniformLocation(name), x, y, z, w);
    }
}
