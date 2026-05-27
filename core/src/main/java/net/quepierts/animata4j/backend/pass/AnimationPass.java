package net.quepierts.animata4j.backend.pass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.pipeline.AnimationContext;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AnimationPass {

    private final String name;

    public abstract void execute(@NotNull AnimationContext context);

}
