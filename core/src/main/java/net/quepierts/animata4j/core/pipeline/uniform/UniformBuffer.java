package net.quepierts.animata4j.core.pipeline.uniform;

import net.quepierts.animata4j.core.buffer.AnimationBuffer;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.buffer.TargetHelper;
import org.jetbrains.annotations.NotNull;

public final class UniformBuffer implements UniformWriter, UniformReader {

    private final AnimationBuffer buffer;
    private final UniformLayout layout;

    private final boolean[] dirty;

    public UniformBuffer(@NotNull UniformLayout layout) {
        this.layout = layout;
        this.buffer = AnimationBuffer.create(layout.getSize());
        
        int locations = layout.getUniformLocationAmount();
        this.dirty = new boolean[locations];
    }

    @Override
    public float getUniform1f(UniformLocation location) {
        int offset = location.getOffset();
        return buffer.read(offset);
    }

    @Override
    public void getUniformf(UniformLocation location, AnimationWritableTarget dst) {
        int offset = location.getOffset();
        TargetHelper.memcpy(buffer, offset, dst, 0, location.getSize());
    }

    @Override
    public void memcpy(AnimationWritableTarget dst) {
        TargetHelper.memcpy(buffer, 0, dst, 0, buffer.getSize());
    }

    @Override
    public void memcpy(AnimationWritableTarget dst, int offset) {
        TargetHelper.memcpy(buffer, 0, dst, offset, buffer.getSize());
    }

    @Override
    public void setUniform1f(UniformLocation location, float x) {
        this.validateLocation(location, 1);
        int offset = location.getOffset();
        buffer.write(offset, x);
    }

    @Override
    public void setUniform2f(UniformLocation location, float x, float y) {
        this.validateLocation(location, 2);
        int offset = location.getOffset();
        buffer.write(offset, x, y);
    }

    @Override
    public void setUniform3f(UniformLocation location, float x, float y, float z) {
        this.validateLocation(location, 3);
        int offset = location.getOffset();
        buffer.write(offset, x, y, z);
    }

    @Override
    public void setUniform4f(UniformLocation location, float x, float y, float z, float w) {
        this.validateLocation(location, 4);
        int offset = location.getOffset();
        buffer.write(offset, x, y, z, w);
    }

    @Override
    public void setUniformMatrix2f(UniformLocation location, float[] values) {
        this.validateLocation(location, values.length);
        int offset = location.getOffset();
        buffer.write(offset, values);
    }

    @Override
    public void setUniformMatrix3f(UniformLocation location, float[] values) {
        this.validateLocation(location, values.length);
        int offset = location.getOffset();
        buffer.write(offset, values);
    }

    @Override
    public void setUniformMatrix4f(UniformLocation location, float[] values) {
        this.validateLocation(location, values.length);
        int offset = location.getOffset();
        buffer.write(offset, values);
    }

    @Override
    public void setUniform(UniformLocation location, float[] values) {
        this.validateLocation(location, values.length);
        int offset = location.getOffset();
        buffer.write(offset, values);
    }

    @Override
    public UniformLocation getUniformLocation(String name) {
        return layout.getUniformLocation(name);
    }

    private void validateLocation(@NotNull UniformLocation location, int length) {
        if (location.getSize() != length) {
            throw new IllegalArgumentException("Invalid uniform length");
        }
    }
}
