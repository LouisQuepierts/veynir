package net.quepierts.animata4j.core.pipeline.drive;

import org.jetbrains.annotations.NotNull;

public interface ComputeDriver {

    void execute(@NotNull ComputeContext context);

}
