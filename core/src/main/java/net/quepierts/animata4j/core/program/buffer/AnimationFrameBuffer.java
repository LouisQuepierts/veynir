package net.quepierts.animata4j.core.program.buffer;

import net.quepierts.animata4j.core.buffer.*;

/**
 * A buffer for animation data in Standard Animation Pipeline.
 * The basic data type is float.
 */
@SuppressWarnings("unused")
public final class AnimationFrameBuffer
        extends BufferView
        implements AnimationReadableTarget, AnimationWritableTarget {

    public AnimationFrameBuffer(int size) {
        super(size);
    }

    public static AnimationFrameBuffer create(int size) {
        return new AnimationFrameBuffer(size);
    }

    public void add(AnimationFrameBuffer other, float scale) {
        if (other == this) {
            this.mul(1.0f + scale);
        }
        else {
            for (int i = 0; i < this.getSize(); i++) {
                final float v = this.read(i) + other.read(i) * scale;
                this.write(i, v);
            }
        }
    }

    public void mul(float value) {
        for (int i = 0; i < this.getSize(); i++) {
            final float v = this.read(i) * value;
            this.write(i, v);
        }
    }

    public void mix(AnimationFrameBuffer other, float weight) {
        if (other != this) {
            if (weight >= 1) {
                this.copy(other);
            }
            else if (weight > 0) {
                var inv = 1 - weight;
                for (int i = 0; i < this.getSize(); i++) {
                    final float v = this.read(i) * inv + other.read(i) * weight;
                    this.write(i, v);
                }
            }
        }
    }

    public void copy(AnimationFrameBuffer other) {
        if (other != this) {
            TargetHelper.memcpy(other, 0, this, 0, this.getSize());
        }
    }

}
