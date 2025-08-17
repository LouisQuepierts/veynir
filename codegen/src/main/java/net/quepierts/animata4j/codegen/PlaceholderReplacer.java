package net.quepierts.animata4j.codegen;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;

public class PlaceholderReplacer {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("^[a-zA-Z_]+$");

    public static @NotNull String replace(
            final @NotNull String template,
            final @NotNull Map<String, String> placeholders
    ) {
        for (String placeholder : placeholders.keySet()) {
            if (!isPlaceholder(placeholder)) {
                throw new IllegalArgumentException("Invalid placeholder: " + placeholder);
            }
        }

        final StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < template.length()) {
            char c = template.charAt(i);
            if (c == '$') {
                final int end = template.indexOf('$', i + 1);
                if (end > 1) {
                    final String placeholder = template.substring(i + 1, end);
                    final String replacement = placeholders.getOrDefault(placeholder, "$" + placeholder + "$");
                    builder.append(replacement);
                    i = end + 1;
                    continue;
                }
            }
            builder.append(c);
            i++;
        }

        return builder.toString();
    }

    private static boolean isPlaceholder(final String text) {
        return PLACEHOLDER_PATTERN.matcher(text).matches();
    }
}
