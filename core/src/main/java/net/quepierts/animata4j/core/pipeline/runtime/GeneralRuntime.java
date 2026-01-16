package net.quepierts.animata4j.core.pipeline.runtime;

import net.quepierts.animata4j.core.pipeline.drive.AnimationDriver;
import net.quepierts.animata4j.core.pipeline.uniform.UniformWriter;
import org.jetbrains.annotations.NotNull;

public interface GeneralRuntime extends AnimationRuntime, UniformWriter {

    AnimationHandle play(@NotNull AnimationDriver driver);

    void stop();

    void terminate();

}
