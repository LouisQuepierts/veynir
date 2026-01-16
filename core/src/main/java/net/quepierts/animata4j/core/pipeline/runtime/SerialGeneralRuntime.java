package net.quepierts.animata4j.core.pipeline.runtime;

import net.quepierts.animata4j.core.data.layout.MemoryLayout;
import net.quepierts.animata4j.core.data.layout.MemoryOffsetResolver;
import net.quepierts.animata4j.core.buffer.AnimationFrameBuffer;
import net.quepierts.animata4j.core.pipeline.drive.AnimationDriver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SerialGeneralRuntime implements GeneralRuntime {

    private final AnimationFrameBuffer outputBuffer;
    private final MemoryOffsetResolver memoryOffsetResolver;

    private @Nullable GeneralAnimationControlBlock acb;

    public SerialGeneralRuntime(MemoryLayout layout) {
        this.outputBuffer = AnimationFrameBuffer.create(layout.getSize());
        this.memoryOffsetResolver = (path) -> MemoryLayout.resolveStatic(layout, path);
    }

    @Override
    public void update(float delta) {
        if (this.acb != null) {
            this.acb.update(delta);
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
        return this.acb != null && this.acb.isRunning();
    }

    @Override
    public AnimationHandle play(@NotNull AnimationDriver driver) {
        if (this.acb != null) {
            if (this.acb.is(driver)) {
                return new AnimationHandle(this.acb);
            }

            this.acb.stop();
        }

        var context = new AnimationInitializationContext(
                driver,
                this.outputBuffer,
                this.memoryOffsetResolver
        );
        this.acb = new GeneralAnimationControlBlock(context);
        return new AnimationHandle(this.acb);
    }

    @Override
    public void stop() {
        if (this.acb != null) {
            this.acb.stop();
        }
    }

    @Override
    public void terminate() {
        if (this.acb != null) {
            this.acb.stop();
        }
    }
}
