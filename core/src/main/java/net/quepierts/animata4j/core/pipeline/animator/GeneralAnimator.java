package net.quepierts.animata4j.core.pipeline.animator;

import net.quepierts.animata4j.core.pipeline.drive.GenericAnimationDriver;
import org.jetbrains.annotations.NotNull;

public interface GeneralAnimator extends Animator {

    AnimationHandle play(@NotNull GenericAnimationDriver<?> driver);

    void stop();

    void terminate();

}
