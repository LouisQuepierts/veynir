package net.quepierts.animata4j.backend.pass.definition;

import lombok.Getter;
import net.quepierts.animata4j.backend.Patterns;
import net.quepierts.animata4j.backend.pass.AnimationPass;
import net.quepierts.animata4j.backend.pipeline.AnimationPipelineCompileContext;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AnimationPassDefinition {

    private final String    name;
    private final PassType  type;

    protected AnimationPassDefinition(
            final String name,
            final PassType type
    ) {
        if (!Patterns.PATTERN_IDENTIFIER
                .matcher(name)
                .matches()) {
            throw new IllegalArgumentException("Invalid pass name: " + name);
        }

        this.name = name;
        this.type = type;
    }

    public static OperationComputePassDefinition compute(String name) {
        return new OperationComputePassDefinition(name);
    }

    public abstract AnimationPass compile(@NotNull AnimationPipelineCompileContext context);

}
