package net.quepierts.animata4j.codegen.util;

import com.github.javaparser.ast.expr.AnnotationExpr;

@SuppressWarnings("unused")
public final class TypeTemplateHelper {
    private TypeTemplateHelper() {}

    public static String get(AnnotationExpr annotation) {
        return annotation.isSingleMemberAnnotationExpr() ?
                annotation.asSingleMemberAnnotationExpr()
                        .getNameAsString()
                : "$type$";
    }
}
