package net.quepierts.veynir.backend.skeleton.pass.definition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.backend.skeleton.pass.SkeletonPass;
import net.quepierts.veynir.backend.skeleton.pipeline.SkeletonPipelineCompileContext;
import net.quepierts.veynir.core.pipeline.PassDefinition;
import org.jspecify.annotations.NonNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SkeletonPassDefinition implements PassDefinition {

    private final String name;

    public abstract SkeletonPass compile(@NonNull SkeletonPipelineCompileContext context);

}
