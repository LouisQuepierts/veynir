package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.SkeletonState;
import net.quepierts.veynir.core.adapter.AnimationOutput;
import org.jspecify.annotations.NonNull;

public interface SkeletonPipeline {

    String INPUT_BUFFER = "Buffer#Input";
    String OUTPUT_BUFFER = "Buffer#Output";

    void submit(@NonNull SkeletonState state);

    void bindProvider(
            String name,
            SkeletonPoseProvider poseProvider
    );

    void bindProvider(
            int location,
            SkeletonPoseProvider poseProvider
    );

    void bindUbo(
            String name,
            UniformBufferObject buffer
    );

    void bindUbo(
            int location,
            UniformBufferObject buffer
    );

    void bindTarget(
            String name,
            SkeletonOutput target
    );

    void bindTarget(
            int location,
            SkeletonOutput target
    );

    SkeletonLayout getLayout();

    UniformBufferObject getUniform();

    AnimationOutput getAdapter();

    ExecutionReflection getReflection();

}
