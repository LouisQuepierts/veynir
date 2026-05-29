package net.quepierts.animata4j.backend.skeleton.pipeline;

import org.jspecify.annotations.NonNull;

public interface SkeletonOutput {

    void accept(@NonNull SkeletonResultView view);

}
