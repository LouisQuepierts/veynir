package net.quepierts.animata4j.backend.skeleton.pipeline;

import org.jetbrains.annotations.NotNull;

public interface SkeletonPoseProvider {

    void fetch(
            @NotNull SkeletonContext    context,
            @NotNull SkeletonPoseBuffer target
    );

}
