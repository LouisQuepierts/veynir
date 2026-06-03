package net.quepierts.animata4j.backend.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnboundUniformBufferException extends RuntimeException {

    private final int location;

    @Override
    public String getMessage() {
        return "Unbound uniform buffer at " + this.location;
    }
}
