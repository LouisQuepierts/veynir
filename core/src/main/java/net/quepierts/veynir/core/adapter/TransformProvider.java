package net.quepierts.veynir.core.adapter;

import net.quepierts.veynir.core.pipeline.PoseView;

public interface TransformProvider {
    
    void write(final PoseView target);
    
}
