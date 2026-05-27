package net.quepierts.animata4j.backend.skeleton.pass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonContext;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SkeletonPass {

    private final String name;

    public abstract void execute(@NotNull SkeletonContext context);

}
