package net.quepierts.animata4j.core.program.driver.control;

import org.jetbrains.annotations.NotNull;

public interface ControlDriver {

    void execute(@NotNull ControlContext context);

}
