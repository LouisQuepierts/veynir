package net.quepierts.animata4j.core.pipeline.animator;

import net.quepierts.animata4j.core.data.layout.MemoryLayout;
import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.pipeline.common.buffer.AnimationFrameBuffer;
import net.quepierts.animata4j.core.pipeline.common.buffer.FloatArrayBuffer;
import net.quepierts.animata4j.core.pipeline.control.AnimationControlBlock;
import net.quepierts.animata4j.core.pipeline.control.GeneralAnimationControlBlock;
import net.quepierts.animata4j.core.pipeline.drive.GenericAnimationDriver;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SerialGeneralAnimator implements GeneralAnimator {

    private final AnimationFrameBuffer outputBuffer;

    private final Map<GenericAnimationDriver<?>, AnimationControlBlock> drivers;

    private final MemoryOffsetResolver memoryOffsetResolver;

    public SerialGeneralAnimator(MemoryLayout layout) {
        this.outputBuffer = FloatArrayBuffer.create(layout.getSize());
        this.drivers = new HashMap<>();

        this.memoryOffsetResolver = (path) -> MemoryLayout.resolveStatic(layout, path);
    }

    @Override
    public void update(float delta) {
        for (AnimationControlBlock block : this.drivers.values()) {
            block.update(delta);
        }
    }

    @Override
    public void process() {

    }

    @Override
    public void apply() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public AnimationHandle play(@NotNull GenericAnimationDriver<?> driver) {
        AnimationControlBlock block = this.drivers.get(driver);
        AnimationInitializationContext context = new AnimationInitializationContext(
                driver,
                this.outputBuffer,
                this.memoryOffsetResolver
        );

        if (block == null) {
            block = new GeneralAnimationControlBlock(context);
            this.drivers.put(driver, block);
        }

        return block;
    }

    @Override
    public void stop() {

    }

    @Override
    public void terminate() {

    }
}
