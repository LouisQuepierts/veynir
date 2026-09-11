package net.quepierts.veynir.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

@Slf4j
@UtilityClass
public class Services {

    /**
     * Loads the highest priority provider that can be instantiated.
     * <p>
     * A provider that fails to load (e.g. a native backend whose library is missing) is skipped
     * so that a lower priority provider can be used as a fallback.
     */
    public static <T> T load(final @NonNull Class<T> clazz) {
        final var loader = ServiceLoader.load(clazz, Services.class.getClassLoader());

        T selected  = null;
        int highest = Integer.MIN_VALUE;

        for (final var iterator = loader.iterator(); iterator.hasNext(); ) {
            T candidate;

            try {
                candidate = iterator.next();
            } catch (ServiceConfigurationError | LinkageError e) {
                log.warn("Failed to load service provider of {}", clazz.getName(), e);
                continue;
            }

            final var priority = candidate instanceof Prioritized prioritized
                    ? prioritized.priority()
                    : Prioritized.DEFAULT_PRIORITY;

            if (priority > highest) {
                selected    = candidate;
                highest     = priority;
            }
        }

        if (selected == null) {
            throw new IllegalStateException(
                    "No service provider found for " + clazz.getName() +
                    ". Is a backend implementation on the classpath?"
            );
        }

        log.debug("Loaded {} for service {}", selected, clazz);

        return selected;
    }

}
