package net.quepierts.animata4j.core.pipeline.runtime;

import java.lang.ref.WeakReference;

public final class AnimationHandle {

    private final WeakReference<AnimationControlBlock> acb;

    AnimationHandle(AnimationControlBlock acb) {
        this.acb = new WeakReference<>(acb);
    }

    public void pause() {
        final var block = acb.get();
        if (block != null) {
            block.pause();
        }
    }

    public void stop() {
        final var block = acb.get();
        if (block != null) {
            block.stop();
        }
    }

    public void resume() {
        final var block = acb.get();
        if (block != null) {
            block.resume();
        }
    }

    public boolean isPaused() {
        final var block = acb.get();
        return block != null && block.isPaused();
    }

    public boolean isStopped() {
        final var block = acb.get();
        return block != null && block.isStopped();
    }

    public boolean isRunning() {
        final var block = acb.get();
        return block != null && block.isRunning();
    }

    public void setSpeed(float speed) {
        final var block = acb.get();
        if (block != null) {
            block.setSpeed(speed);
        }
    }

    public float getSpeed() {
        final var block = acb.get();
        return block != null ? block.getSpeed() : 0;
    }
}
