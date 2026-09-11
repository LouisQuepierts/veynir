package net.quepierts.veynir.core.pipeline;

/**
 * Marker for a pipeline pass descriptor.
 * <p>
 * A descriptor carries the pass configuration in a backend neutral form; each backend supplies
 * its own implementation that knows how to turn the descriptor into an executable pass.
 */
public interface PassDefinition {
}
