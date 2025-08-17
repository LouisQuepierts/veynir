package net.quepierts.animata4j.codegen.generator;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.google.auto.service.AutoService;
import net.quepierts.animata4j.codegen.PlaceholderReplacer;
import net.quepierts.animata4j.codegen.annotation.PrimitiveTemplate;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AutoService(Generator.class)
@SupportedAnnotationTypes(PrimitiveTemplateGenerator.ANNO_PRIMITIVE_TEMPLATE)
public class PrimitiveTemplateGenerator extends Generator {
    public static final String ANNO_PRIMITIVE_TEMPLATE = "PrimitiveTemplate";
    public static final String ANNO_TYPE_TEMPLATE = "TypeTemplate";

    @Override
    public void generate(CompilationUnit source, AnnotationExpr annotation, Path destination, Path relativePath) {
        final PrimitiveTemplate.Type[] types = this.getTypes(annotation);

        // _TEMPLATE_$Type$Class => $Type$Class
        final String string = relativePath.getFileName().toString().substring(10);
        final Path destinationPath = destination.resolve(relativePath.subpath(0, relativePath.getNameCount() - 1));

        for (PrimitiveTemplate.Type type : types) {
            final String className = string.replace("$Type$", type.getClassName());

            // target path = destination + (relative path).replaceLast(className)
            final Path target = destinationPath.resolve(className);
            CompilationUnit clone = source.clone();
            this.generate(clone, target, type);
        }
    }

    @Override
    public boolean shouldRemoveAnnotation() {
        return true;
    }

    private void generate(
            CompilationUnit cu,
            Path target,
            PrimitiveTemplate.Type type
    ) {
        final Map<String, String> replacements = Map.of(
                "Type", type.getClassName(),
                "type", type.getTypeName()
        );

        /*for (ClassOrInterfaceDeclaration declaration : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            String replacement = PlaceholderReplacer.replace(declaration.getNameAsString().substring(10), replacements);
            declaration.setName(replacement);
        }

        for (SimpleName name : cu.findAll(SimpleName.class)) {
            String replacement = PlaceholderReplacer.replace(name.asString(), replacements);
            name.setIdentifier(replacement);
        }*/

        this.replaceTypeAnnotation(cu.findAll(FieldDeclaration.class), replacements);

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            Optional<AnnotationExpr> retAnnotation = method.getAnnotationByName(ANNO_TYPE_TEMPLATE);
            if (retAnnotation.isPresent()) {
                AnnotationExpr retTypeAnnotation = retAnnotation.get();
                method.remove(retTypeAnnotation);
                final String template = this.getTypeTemplate(retTypeAnnotation);
                method.setType(PlaceholderReplacer.replace(template, replacements));
            }

            for (Parameter parameter : method.findAll(Parameter.class)) {
                Optional<AnnotationExpr> optionalAnnotationExpr = parameter.getAnnotationByName(ANNO_TYPE_TEMPLATE);
                if (optionalAnnotationExpr.isPresent()) {
                    final AnnotationExpr paramTypeAnnotation = optionalAnnotationExpr.get();
                    parameter.remove(paramTypeAnnotation);
                    String template = this.getTypeTemplate(paramTypeAnnotation);

                    if (parameter.getType().isArrayType()) {
                        template += "[]";
                    }

                    parameter.setType(PlaceholderReplacer.replace(template, replacements));
                }
            }
        }

        this.write(cu, target, replacements);
    }

    private void replaceTypeAnnotation(
            final List<? extends Node> nodes,
            final Map<String, String> replacements
    ) {
        for (Node node : nodes) {
            for (ClassOrInterfaceType type : node.findAll(ClassOrInterfaceType.class)) {
                Optional<AnnotationExpr> optional = type.getAnnotationByName("TypeTemplate");
                if (optional.isPresent()) {
                    AnnotationExpr annotation = optional.get();
                    type.remove(annotation);

                    String template = "$type$";

                    if (annotation.isSingleMemberAnnotationExpr()) {
                        template = annotation.asSingleMemberAnnotationExpr()
                                .getNameAsString();
                    }

                    if (type.isArrayType()) {
                        template = template + "[]";
                    }

                    final String result = PlaceholderReplacer.replace(template, replacements);
                    type.replace(StaticJavaParser.parseType(result));
                }
            }
        }
    }

    private String getTypeTemplate(AnnotationExpr annotation) {
        return annotation.isSingleMemberAnnotationExpr() ?
                annotation.asSingleMemberAnnotationExpr()
                        .getNameAsString()
                : "$type$";
    }

    private PrimitiveTemplate.Type[] getTypes(AnnotationExpr template) {
        if (template.isSingleMemberAnnotationExpr()) {
            SingleMemberAnnotationExpr single = template.asSingleMemberAnnotationExpr();
            return single.getMemberValue().asArrayInitializerExpr().getValues().stream()
                    .map(value -> value.asStringLiteralExpr().asString())
                    .map(PrimitiveTemplate.Type::valueOf)
                    .toArray(PrimitiveTemplate.Type[]::new);
        }

        return PrimitiveTemplate.Type.values();
    }
}
