package net.quepierts.veynir.backend.uniform;

public record UniformDescription(
        String      name,
        UniformType type,
        int         length
) {
}
