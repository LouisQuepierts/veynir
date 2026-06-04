package net.quepierts.veynir.backend.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnboundUniformBufferException extends RuntimeException {

    private final int location;

    @Override
    public String getMessage() {
        return "Unbound uniform buffer at " + this.location;
    }
}
