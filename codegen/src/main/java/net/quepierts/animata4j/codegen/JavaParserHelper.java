package net.quepierts.animata4j.codegen;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@UtilityClass
public final class JavaParserHelper {
    public static <T extends Node & NodeWithAnnotations<? extends Node>> @Nullable AnnotationExpr getAndRemoveAnnotation(
            final T node,
            final String annotationName
    ) {
        Optional<AnnotationExpr> optional = node.getAnnotationByName(annotationName);
        if (optional.isPresent()) {
            AnnotationExpr expr = optional.get();
            node.remove(expr);
            return expr;
        }
        return null;
    }

    public static boolean isPlaceholderType(final Type type) {
        if (type.isClassOrInterfaceType()) {
            return type.toString().equals("type");
        } else if (type.isArrayType()) {
            return isPlaceholderType(((ArrayType) type).getComponentType());
        }
        return false;
    }

    @UtilityClass
    public static final class TypeTemplate {
        public static String get(AnnotationExpr annotation) {
            return annotation.isSingleMemberAnnotationExpr() ?
                    annotation.asSingleMemberAnnotationExpr()
                            .getNameAsString()
                    : "$type$";
        }
    }
}
