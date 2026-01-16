package net.quepierts.animata4j.core.pipeline.runtime;

import lombok.Getter;
import lombok.Setter;
import net.quepierts.animata4j.core.pipeline.common.AnimationContext;
import net.quepierts.animata4j.core.pipeline.common.buffer.AnimationFrameBuffer;
import net.quepierts.animata4j.core.pipeline.common.buffer.BufferManager;
import net.quepierts.animata4j.core.pipeline.common.state.RuntimeState;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;
import net.quepierts.animata4j.core.pipeline.drive.AnimationDriver;
import org.jetbrains.annotations.NotNull;

public class GeneralAnimationControlBlock implements AnimationControlBlock {

    @Getter
    private final AnimationFrameBuffer output;
    private final BufferManager manager;

    private final Context context;

    private final AnimationDriver driver;
    private final RuntimeState state;

    @Getter
    @Setter
    private float speed;

    @Getter
    private boolean paused;

    @Getter
    private boolean stopped;

    public GeneralAnimationControlBlock(AnimationInitializationContext context) {
        this.output = context.getBuffer();
        this.manager = new BufferManager(context.getBuffer().getSize());

        this.driver = context.getDriver();
        this.state = driver._createState(context.getResolver());

        this.context = new Context();
    }

    @Override
    public void update(float delta) {
        this.context.update(delta);
        this.driver._update(this.output, this.context, this.state);
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void stop() {
        this.stopped = true;
    }

    @Override
    public void resume() {
        this.paused = false;
    }

    @Override
    public boolean isRunning() {
        return !this.paused && !this.stopped;
    }

    public boolean is(AnimationDriver driver) {
        return this.driver == driver;
    }

    public AnimationFrameBuffer requestBuffer() {
        return this.manager.request();
    }

    public void releaseBuffer(AnimationFrameBuffer buffer) {
        this.manager.release(buffer, true);
    }

    private static class Context implements AnimationContext {

        @Getter
        private float localTime;

        @Getter
        private float deltaTime;

        public void update(float delta) {
            this.localTime += delta;
            this.deltaTime = delta;
        }

        @Override
        public int getDirection() {
            return 0;
        }

        @Override
        public @NotNull AnimationReadableTarget getInternalBuffer() {
            return null;
        }

    }
}
