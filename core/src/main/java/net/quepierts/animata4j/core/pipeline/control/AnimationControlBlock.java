package net.quepierts.animata4j.core.pipeline.control;

import net.quepierts.animata4j.core.pipeline.animator.AnimationHandle;

public interface AnimationControlBlock extends AnimationHandle {

    void update(float delta);

    void stop();

}
