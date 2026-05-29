package net.quepierts.animata4j.backend.skeleton.pipeline;

import net.quepierts.animata4j.backend.skeleton.SkeletonLayout;
import net.quepierts.animata4j.backend.uniform.UniformReader;
import net.quepierts.animata4j.core.SkeletonState;
import org.jspecify.annotations.NonNull;

public interface SkeletonContext {

    @NonNull SkeletonLayout         getLayout();

    @NonNull SkeletonState          getState();

    @NonNull SkeletonPoseProvider   getProvider(int location);

    @NonNull SkeletonPoseBuffer     getPoseBuffer(int location);

    @NonNull UniformReader          getUniform();

    @NonNull UniformReader          getUniformBuffer(int location);

}
