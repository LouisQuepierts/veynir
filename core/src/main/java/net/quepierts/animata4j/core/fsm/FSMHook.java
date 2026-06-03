package net.quepierts.animata4j.core.fsm;

@SuppressWarnings("unused")
public interface FSMHook {

    int TRIGGER_TYPE_AUTO = 0;
    int TRIGGER_TYPE_EVENT = 1;

    void onTransitionStart(
            final FSMState  fsmState,
            final int       fromState,
            final int       toState,
            final int       triggerType,
            final int       triggerId
    );

    void onTransitionEnd(
            final FSMState  fsmState,
            final int       fromState,
            final int       toState
    );

    void onLoop(
            final FSMState  fsmState,
            final int       state
    );

}
