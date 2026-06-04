package net.quepierts.veynir.core.data.layout.mapper;

import net.quepierts.veynir.core.data.layout.VeynirDataType;
import net.quepierts.veynir.core.data.layout.FieldDefinition;
import net.quepierts.veynir.core.data.layout.StructDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StructParser {
    public static StructDefinition parse(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(VeynirStruct.Mapper.class)) {
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

        if (clazz.isAnnotationPresent(VeynirStruct.class)) {
            VeynirStruct veynirStruct = clazz.getAnnotation(VeynirStruct.class);
            if (!veynirStruct.value().isBlank()) {
                name = veynirStruct.value();
            }
        }

        StructDefinition.Builder structBuilder = StructDefinition.builder(name)
                .enableEmptyCheck();

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!field.isAnnotationPresent(VeynirField.class)) {
                continue;
            }

            VeynirField fieldAnnotation = field.getAnnotation(VeynirField.class);
            Class<?> type = field.getType();
            boolean isArray = type.isArray();
            int length = 1;

            if (isArray) {
                type = type.getComponentType();
                length = fieldAnnotation.length();
            }

            String typeName = type.getName();
            VeynirDataType primitiveType = VeynirDataType.getType(typeName);

            FieldDefinition.Builder fieldBuilder;
            if (primitiveType == VeynirDataType.STRUCT) {
                VeynirField.Type typeOverride = fieldAnnotation.type();

                if (typeOverride.value() == VeynirDataType.STRUCT) {
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
