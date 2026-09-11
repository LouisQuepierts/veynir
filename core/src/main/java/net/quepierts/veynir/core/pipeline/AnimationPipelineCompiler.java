package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.channel.ChannelFormat;
import net.quepierts.veynir.core.channel.ChannelLayout;
import net.quepierts.veynir.core.uniform.UboDefinition;
import net.quepierts.veynir.core.uniform.UniformType;
import org.jspecify.annotations.NonNull;

public interface AnimationPipelineCompiler {

    @NonNull AnimationPipelineCompiler withChannelLayout(@NonNull ChannelLayout layout);

    @NonNull AnimationPipelineCompiler withChannelFormat(@NonNull ChannelFormat format);

    @NonNull AnimationPipelineCompiler withPass(@NonNull PassDefinition pass);

    @NonNull AnimationPipelineCompiler withSampler(@NonNull String name);

    @NonNull AnimationPipelineCompiler withBuffer(@NonNull String name);

    @NonNull AnimationPipelineCompiler withOutput(@NonNull String name);

    @NonNull AnimationPipelineCompiler withUniform(
            @NonNull String name,
            @NonNull UniformType type
    );

    @NonNull AnimationPipelineCompiler withUbo(
            @NonNull String name,
            @NonNull UboDefinition definition
    );

    @NonNull AnimationPipeline compile();

}
