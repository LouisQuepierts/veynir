package net.quepierts.animata4j.core.pipeline.drive;

import org.jetbrains.annotations.NotNull;

public interface ControlDriver {

    void execute(@NotNull ControlContext context);

}
