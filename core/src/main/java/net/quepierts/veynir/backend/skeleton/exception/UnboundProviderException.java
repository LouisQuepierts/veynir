package net.quepierts.veynir.backend.skeleton.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnboundProviderException extends RuntimeException {

    private final int location;

    @Override
    public String getMessage() {
        return "Unbound provider at " + this.location;
    }

}
