package net.quepierts.veynir.backend.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnboundSamplerException extends RuntimeException {

    private final int location;

    @Override
    public String getMessage() {
        return "Unbound sampler at location " + location;
    }

}
