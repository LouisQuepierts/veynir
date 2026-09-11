package net.quepierts.veynir.backend.pass.definition;

import lombok.Getter;
import net.quepierts.veynir.backend.Patterns;
import net.quepierts.veynir.backend.pass.AnimationPass;
import net.quepierts.veynir.backend.pipeline.AnimationPipelineCompileContext;
import net.quepierts.veynir.core.pipeline.PassDefinition;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AnimationPassDefinition implements PassDefinition {

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
