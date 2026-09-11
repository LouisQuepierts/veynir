package net.quepierts.veynir.core.pipeline;

import org.jspecify.annotations.NonNull;

public interface SkeletonOutput {

    void accept(@NonNull SkeletonResultView view);

}
