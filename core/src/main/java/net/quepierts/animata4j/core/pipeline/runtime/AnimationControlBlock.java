package net.quepierts.animata4j.core.pipeline.runtime;

public interface AnimationControlBlock {

    void update(float delta);

    void pause();

    void stop();

    void resume();

    boolean isPaused();

    boolean isStopped();

    boolean isRunning();

    void setSpeed(float speed);

    float getSpeed();

}
