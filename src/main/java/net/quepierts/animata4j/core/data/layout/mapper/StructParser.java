package net.quepierts.animata4j.core.data.layout.mapper;

import net.quepierts.animata4j.core.data.layout.AnimataDataType;
import net.quepierts.animata4j.core.data.layout.FieldDefinition;
import net.quepierts.animata4j.core.data.layout.StructDefinition;

import java.lang.reflect.*;

public class StructParser {
    public static StructDefinition parse(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(AnimataStruct.Mapper.class)) {
                continue;
            }

            if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            if (method.getParameterCount() != 0) {
                continue;
            }

            if (!method.getReturnType().equals(StructDefinition.class)) {
                continue;
            }

            try {
                return (StructDefinition) method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException ignored) {

            }
        }

        String name = clazz.getName();

        if (clazz.isAnnotationPresent(AnimataStruct.class)) {
            AnimataStruct animataStruct = clazz.getAnnotation(AnimataStruct.class);
            if (!animataStruct.value().isBlank()) {
                name = animataStruct.value();
            }
        }

        StructDefinition.Builder structBuilder = StructDefinition.builder(name)
                .enableEmptyCheck();

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!field.isAnnotationPresent(AnimataField.class)) {
                continue;
            }

            AnimataField fieldAnnotation = field.getAnnotation(AnimataField.class);
            Class<?> type = field.getType();
            boolean isArray = type.isArray();
            int length = 1;

            if (isArray) {
                type = type.getComponentType();
                length = fieldAnnotation.length();
            }

            String typeName = type.getName();
            AnimataDataType primitiveType = AnimataDataType.getType(typeName);

            FieldDefinition.Builder fieldBuilder;
            if (primitiveType == AnimataDataType.STRUCT) {
                AnimataField.Type typeOverride = fieldAnnotation.type();

                if (typeOverride.value() == AnimataDataType.STRUCT) {
                    fieldBuilder = FieldDefinition.struct(typeOverride.typename().isBlank() ? typeName : typeOverride.typename());
                } else {
                    fieldBuilder = FieldDefinition.primitive(typeOverride.value());
                    length *= typeOverride.count();
                }
            } else {
                fieldBuilder = FieldDefinition.primitive(primitiveType);
            }

            fieldBuilder.name(fieldAnnotation.value().isBlank() ? field.getName() : fieldAnnotation.value())
                    .length(length);

            structBuilder.field(fieldBuilder.build());
        }

        return structBuilder.build();
    }
}
