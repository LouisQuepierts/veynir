package net.quepierts.animata4j.core.adapter;

import net.quepierts.animata4j.backend.skeleton.pipeline.PoseView;

public interface TransformProvider {
    
    void write(final PoseView target);
    
}
