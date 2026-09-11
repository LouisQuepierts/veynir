package net.quepierts.veynir.core.pipeline;

public enum SamplingMode {
    DEFAULT,
    FREEZE_START,
    FREEZE_END;

    private static final SamplingMode[] VALUES = values();

    public static SamplingMode byId(int id) {
        return VALUES[id];
    }

}
