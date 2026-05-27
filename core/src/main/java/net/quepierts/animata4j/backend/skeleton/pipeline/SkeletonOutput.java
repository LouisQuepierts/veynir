package net.quepierts.animata4j.backend.skeleton.pipeline;

import org.jetbrains.annotations.NotNull;

public interface SkeletonOutput {

    void accept(@NotNull SkeletonResultView view);

}
