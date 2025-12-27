package net.quepierts.animata4j.core.pipeline.animator;

public interface AnimationHandle {
    void pause();

    void stop();

    void resume();

    boolean isPaused();

    boolean isStopped();

    boolean isRunning();

    void setSpeed(float speed);

    float getSpeed();
}
