package net.quepierts.veynir.core.fsm;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

@Getter
public final class FSMState {

    float           elapsed;
    float           blendElapsed;
    float           blendDuration;

    float           normalizedElapsed;
    float           normalizedBlendElapsed;

    int             lastState;
    int             currentState;

    boolean         blending;
    boolean         finished;

    @Setter
    @Getter
    FSMParameter    uniform;

    @Getter
    @Setter
    Object          attachment;

    @Contract(value = "-> new", pure = true)
    public @NonNull FSMState duplicate() {
        final var duplicate                 = new FSMState();
        duplicate.elapsed                   = this.elapsed;
        duplicate.blendElapsed              = this.blendElapsed;
        duplicate.blendDuration             = this.blendDuration;
        duplicate.normalizedElapsed         = this.normalizedElapsed;
        duplicate.normalizedBlendElapsed    = this.normalizedBlendElapsed;
        duplicate.lastState                 = this.lastState;
        duplicate.currentState              = this.currentState;
        duplicate.blending                  = this.blending;
        duplicate.finished                  = this.finished;
        duplicate.uniform                   = this.uniform;
        duplicate.attachment                = this.attachment;

        return duplicate;
    }

    public void copyData(final @NonNull FSMState state) {
        this.elapsed                        = state.elapsed;
        this.blendElapsed                   = state.blendElapsed;
        this.blendDuration                  = state.blendDuration;
        this.normalizedElapsed              = state.normalizedElapsed;
        this.normalizedBlendElapsed         = state.normalizedBlendElapsed;
        this.lastState                      = state.lastState;
        this.currentState                   = state.currentState;
        this.blending                       = state.blending;
        this.finished                       = state.finished;
    }

    public static FSMState direct(
            final float                     elapsed,
            final float                     blendElapsed,
            final float                     blendDuration,
            final float                     normalizedElapsed,
            final float                     normalizedBlendElapsed,
            final int                       lastState,
            final int                       currentState,
            final boolean                   blending,
            final boolean                   finished
    ) {
        final var instance                 = new FSMState();

        instance.elapsed                   = elapsed;
        instance.blendElapsed              = blendElapsed;
        instance.blendDuration             = blendDuration;
        instance.normalizedElapsed         = normalizedElapsed;
        instance.normalizedBlendElapsed    = normalizedBlendElapsed;
        instance.lastState                 = lastState;
        instance.currentState              = currentState;
        instance.blending                  = blending;
        instance.finished                  = finished;

        return instance;
    }

}
