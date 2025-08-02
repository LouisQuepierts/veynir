package net.quepierts.animata4j.core.data.layout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum AnimataDataType {
    BYTE(1, true, "byte"),
    SHORT(2, true, "short"),
    INT(4, true, "int"),
    LONG(8, true, "long"),
    FLOAT(4, true, "float"),
    DOUBLE(8, true, "double"),
    BOOLEAN(1, false, "boolean"),
    STRUCT(0, false, "struct"),
    ;

    private static final AnimataDataType[] VALUES = values();
    private static final Set<String> PRIMITIVE_NAMES = Arrays.stream(VALUES)
            .map(AnimataDataType::getName)
            .collect(Collectors.toSet());

    public static boolean isPrimitiveType(String name) {
        return PRIMITIVE_NAMES.contains(name.toUpperCase(Locale.ROOT));
    }

    public static boolean isAvailableStructName(String name) {
        return !isPrimitiveType(name);
    }

    private final int size;
    private final boolean primitive;
    private final String name;
}
