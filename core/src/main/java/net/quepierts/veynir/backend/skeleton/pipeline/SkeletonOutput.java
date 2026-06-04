package net.quepierts.veynir.backend.skeleton.pipeline;

import org.jspecify.annotations.NonNull;

public interface SkeletonOutput {

    void accept(@NonNull SkeletonResultView view);

}
