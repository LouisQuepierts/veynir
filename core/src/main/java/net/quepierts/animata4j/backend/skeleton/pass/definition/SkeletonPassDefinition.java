package net.quepierts.animata4j.backend.skeleton.pass.definition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.skeleton.pass.SkeletonPass;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonPipelineCompileContext;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SkeletonPassDefinition {

    private final String name;

    public abstract SkeletonPass compile(@NotNull SkeletonPipelineCompileContext context);

}
