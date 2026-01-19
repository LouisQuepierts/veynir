package net.quepierts.animata4j.core.program.driver.compute;

import org.jetbrains.annotations.NotNull;

public interface ComputeDriver {

    void execute(@NotNull ComputeContext context);

}
