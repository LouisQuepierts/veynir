package net.quepierts.animata4j.core.program.driver.control;

import net.quepierts.animata4j.core.pipeline.uniform.UniformReader;
import net.quepierts.animata4j.core.program.buffer.RuntimeStateBuffer;
import net.quepierts.animata4j.core.program.driver.DriverMask;
import org.jetbrains.annotations.NotNull;

public interface ControlContext {

    @NotNull DriverMask getDriverMask();

    @NotNull UniformReader getUniformReader();

    @NotNull RuntimeStateBuffer getRuntimeStateBuffer();

}
