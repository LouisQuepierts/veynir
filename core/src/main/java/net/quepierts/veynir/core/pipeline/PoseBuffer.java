package net.quepierts.veynir.core.pipeline;

import org.jspecify.annotations.NonNull;

public interface PoseBuffer extends SkeletonResultView {

    @Override
    PoseView get(int id);

    int size();

    void copy(@NonNull PoseBuffer src);

}
