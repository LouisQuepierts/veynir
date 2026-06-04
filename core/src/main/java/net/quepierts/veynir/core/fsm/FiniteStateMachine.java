package net.quepierts.veynir.core.fsm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class FiniteStateMachine {

    public static final byte    INVALID_STATE   = -1;
    public static final byte    EVENT_EXIT      = -1;

    @Getter
    final LocationLookup        lookup;
    final int[]                 next;

    @Getter
    final int                   initial;

    @Getter
    final int                   terminal;

    final @Nullable FSMHook     hook;

    public void start(@NonNull final FSMState state) {
        this.reset(state);

        if (this.hook != null) {
            this.hook.onStart(state);
        }
    }

    public void exit(@NonNull final FSMState state) {
        this.event(state, EVENT_EXIT);
    }

    public void abort(@NonNull final FSMState state) {
        this.reset(state);
    }

    public void reset(@NonNull final FSMState state) {
        state.currentState              = this.initial;
        state.elapsed                   = 0.0f;
        state.blendElapsed              = 0.0f;
        state.normalizedElapsed         = 0.0f;
        state.normalizedBlendElapsed    = 0.0f;
        state.blendDuration             = 0.0f;
        state.lastState                 = INVALID_STATE;
        state.blending                  = false;
        state.uniform                   = null;
        state.finished                  = false;
    }

    public void update(
            @NonNull final FSMState state,
            float                   delta
    ) {
        if (state.uniform        == null) {
            log.warn("Uniform buffer is not bound");
            return;
        }

        if (state.finished) {
            return;
        }

        if (state.blending) {
            state.blendElapsed          += delta;
            state.normalizedBlendElapsed = Math.min(
                    state.blendElapsed / state.blendDuration,
                    1.0f
            );

            if (state.blendElapsed >= state.blendDuration) {
                state.blending      = false;

                if (this.hook != null) {
                    this.hook.onTransitionEnd(
                            state,
                            state.lastState,
                            state.currentState
                    );
                }
            }

            return;
        }

        final var duration          = state.uniform.duration()[state.currentState];

        state.elapsed               += delta;
        state.normalizedElapsed     = Math.min(state.elapsed / duration, 1.0f);

        if (state.elapsed < duration) {
            return;
        }

        if (state.currentState == this.terminal) {
            state.finished     = true;
            if (this.hook != null) {
                this.hook.onFinish(state);
            }
            return;
        }

        final var next      = this.next[state.currentState];
        this                .transition(state, next, FSMHook.TRIGGER_TYPE_AUTO, 0);
    }

    public void event(
            @NonNull final FSMState state,
            int event
    ) {
        if (state.uniform == null) {
            log.warn("Uniform buffer is not bound");
            return;
        }

        final var next          = event == EVENT_EXIT ?
                                this.terminal :
                                event;

        this                    .transition(state, next, FSMHook.TRIGGER_TYPE_EVENT, event);
    }

    public boolean isLooping(
            @NonNull final FSMState state
    ) {
        return state.currentState == state.lastState;
    }

    public int getNextState(
            final int state
    ) {
        return this.next[state];
    }

    private void transition(
            @NonNull final FSMState state,
            final int               next,
            final int               triggerType,
            final int               event
    ) {

        final var current           = state.currentState;
        if (next == current) { // loop
            final var duration      = state.uniform.duration()[current];
            state.elapsed           %= duration;
            state.normalizedElapsed = Math.min(state.elapsed / duration, 1.0f);
            state.lastState         = current;

            if (this.hook != null) {
                this.hook.onLoop(state, current);
            }
            return;
        }

        if (this.hook != null) {
            this.hook.onTransitionStart(
                    state,
                    current,
                    next,
                    FSMHook.TRIGGER_TYPE_AUTO,
                    0
            );
        }

        state.blendElapsed      = 0.0f;
        state.normalizedElapsed = 0.0f;
        state.blendDuration     = Math.max(
                                    state.uniform.fadeOut()[current],
                                    state.uniform.fadeIn()[next]
                                );
        state.blending          = state.blendDuration > 0.0f;

        state.lastState         = current;
        state.currentState      = next;
        state.elapsed           = 0.0f;
        state.normalizedElapsed = 0.0f;
    }

    public FSMParameter uniform() {
        final var size = this.lookup.size();
        return new FSMParameter(
                new float[size],
                new float[size],
                new float[size]
        );
    }

    public static Compiler compiler() {
        return new Compiler();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Compiler {

        public static final Pattern         PATTERN_STATE   = Pattern.compile("^[a-zA-Z0-9_.#]+$");

        private final List<String>          states          = new ArrayList<>();
        private final Map<String, String>   transitions     = new HashMap<>();

        private String                      initialState;
        private String                      terminalState;

        private FSMHook                     hook;

        private boolean                     sequence;

        public Compiler sequence() {
            this.sequence = true;
            return this;
        }

        public Compiler withState(final @NonNull String state) {

            if (this.states.size() == Byte.MAX_VALUE) {
                throw new IllegalStateException("Too many states");
            }

            if (!PATTERN_STATE
                    .matcher(state)
                    .matches()) {
                throw new IllegalArgumentException("Invalid state name: " + state);
            }

            if (this.states.contains(state)) {
                throw new IllegalArgumentException("State already exists: " + state);
            }

            if (this.sequence && !this.states.isEmpty()) {
                this.transitions.put(
                        this.states.get(this.states.size() - 1),
                        state
                );
            } else {
                this.transitions.put(state, state);
            }
            this.states.add(state);
            return this;
        }

        public Compiler withInitialState(final @NonNull String state) {

            if (!this.states.contains(state)) {
                throw new IllegalArgumentException("State does not exist: " + state);
            }

            this.initialState = state;
            return this;
        }

        public Compiler withTerminalState(final @NonNull String state) {

            if (!this.states.contains(state)) {
                throw new IllegalArgumentException("State does not exist: " + state);
            }

            this.terminalState = state;
            return this;
        }

        public Compiler withTransition(
                final @NonNull  String  from,
                final @NonNull  String  to
        ) {
            if (!this.states.contains(from)) {
                throw new IllegalArgumentException("State does not exist: " + from);
            }

            if (!this.states.contains(to)) {
                throw new IllegalArgumentException("State does not exist: " + to);
            }

            this.transitions.put(from, to);
            return this;
        }

        public Compiler withHook(final @NonNull FSMHook hook) {
            this.hook = hook;
            return this;
        }

        public FiniteStateMachine compile() {

            if (this.initialState   == null) {
                this.initialState   = this.states.get(0);
            }

            if (this.terminalState  == null) {
                this.terminalState  = this.states.get(this.states.size() - 1);
            }

            final var lookup        = LocationLookup.of(this.states);
            final var initial       = lookup.find(this.initialState);
            final var terminal      = lookup.find(this.terminalState);

            final var next          = new int[lookup.size()];

            for (var entry         : this.transitions.entrySet()) {
                next[lookup.find(entry.getKey())] = lookup.find(entry.getValue());
            }

            return new FiniteStateMachine(
                    lookup,
                    next,
                    initial,
                    terminal,
                    this.hook
            );
        }

    }

}
