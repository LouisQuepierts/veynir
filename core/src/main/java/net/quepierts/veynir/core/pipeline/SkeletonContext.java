package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.SkeletonState;
import org.jspecify.annotations.NonNull;

public interface SkeletonContext {

    @NonNull SkeletonLayout         getLayout();

    @NonNull SkeletonState          getState();

    boolean                         getMask(int bone);

    @NonNull SkeletonPoseProvider   getProvider(int location);

    @NonNull PoseBuffer             getPoseBuffer(int location);

    @NonNull UniformReader          getUniform();

    @NonNull UniformReader          getUniformBuffer(int location);

}
