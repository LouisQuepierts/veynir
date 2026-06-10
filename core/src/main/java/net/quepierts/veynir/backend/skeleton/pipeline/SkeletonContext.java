package net.quepierts.veynir.backend.skeleton.pipeline;

import net.quepierts.veynir.backend.skeleton.SkeletonLayout;
import net.quepierts.veynir.backend.uniform.UniformReader;
import net.quepierts.veynir.core.SkeletonState;
import org.jspecify.annotations.NonNull;

public interface SkeletonContext {

    @NonNull SkeletonLayout         getLayout();

    @NonNull SkeletonState          getState();

    boolean                         getMask(int bone);

    @NonNull SkeletonPoseProvider   getProvider(int location);

    @NonNull SkeletonPoseBuffer     getPoseBuffer(int location);

    @NonNull UniformReader          getUniform();

    @NonNull UniformReader          getUniformBuffer(int location);

}
