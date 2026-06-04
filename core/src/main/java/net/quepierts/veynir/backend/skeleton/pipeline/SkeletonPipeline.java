package net.quepierts.veynir.backend.skeleton.pipeline;

import net.quepierts.veynir.backend.execution.ExecutionReflection;
import net.quepierts.veynir.backend.skeleton.SkeletonLayout;
import net.quepierts.veynir.backend.uniform.UniformBuffer;
import net.quepierts.veynir.core.SkeletonState;
import net.quepierts.veynir.core.adapter.AnimationOutput;
import org.jspecify.annotations.NonNull;

public interface SkeletonPipeline {

    String INPUT_BUFFER = "Buffer#Input";
    String OUTPUT_BUFFER = "Buffer#Output";

    static DefaultSkeletonPipelineImpl.Compiler compiler() {
        return DefaultSkeletonPipelineImpl.compiler();
    }

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
            UniformBuffer buffer
    );

    void bindUbo(
            int location,
            UniformBuffer buffer
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

    UniformBuffer getUniform();

    AnimationOutput getAdapter();

    ExecutionReflection getReflection();

}
