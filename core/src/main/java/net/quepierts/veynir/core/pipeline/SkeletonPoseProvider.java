package net.quepierts.veynir.core.pipeline;

import org.jspecify.annotations.NonNull;

public interface SkeletonPoseProvider {

    void fetch(
            @NonNull SkeletonContext    context,
            @NonNull PoseBuffer         target
    );

}
