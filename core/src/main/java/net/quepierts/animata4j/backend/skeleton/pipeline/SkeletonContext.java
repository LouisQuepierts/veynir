package net.quepierts.animata4j.backend.skeleton.pipeline;

import net.quepierts.animata4j.backend.skeleton.SkeletonLayout;
import net.quepierts.animata4j.backend.uniform.UniformReader;
import net.quepierts.animata4j.core.SkeletonState;
import org.jetbrains.annotations.NotNull;

public interface SkeletonContext {

    @NotNull SkeletonLayout         getLayout();

    @NotNull SkeletonState          getState();

    @NotNull SkeletonPoseProvider   getProvider(int location);

    @NotNull SkeletonPoseBuffer     getPoseBuffer(int location);

    @NotNull UniformReader          getUniform();

    @NotNull UniformReader          getUniformBuffer(int location);

}
