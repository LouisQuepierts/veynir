package net.quepierts.codegen.plugin.generator;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.google.auto.service.AutoService;
import net.quepierts.codegen.plugin.PlaceholderReplacer;
import net.quepierts.codegen.plugin.PrimitiveType;
import net.quepierts.codegen.plugin.JavaParserHelper;

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
        final PrimitiveType[] types = this.getTypes(annotation);

        // _TEMPLATE_$Type$Class => $Type$Class
        final String string = relativePath.getFileName().toString().substring(10);
        final Path destinationPath = destination.resolve(relativePath.subpath(0, relativePath.getNameCount() - 1));

        for (PrimitiveType type : types) {
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
            PrimitiveType type
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
            this.replaceType(method, replacements);

            for (Parameter parameter : method.findAll(Parameter.class)) {
                this.replaceType(parameter, replacements);
            }
        }

        this.write(cu, target, replacements);
    }

    private <T extends Node & NodeWithType<? extends Node, Type> & NodeWithAnnotations<? extends Node>> void replaceType(
            final T node,
            final Map<String, String> replacements
    ) {
        final AnnotationExpr annotation = JavaParserHelper.getAndRemoveAnnotation(node, ANNO_TYPE_TEMPLATE);
        final Type type = node.getType();

        if (type.isClassOrInterfaceType() && type.toString().equals("type")) {
            final String typename = replacements.get("type");
            node.setType(typename);
        } else if (type.isArrayType() && type.toString().startsWith("type")){
            final String typename = replacements.get("type") + "[]";
            node.setType(typename);
        } else if (annotation != null) {
            final String template = JavaParserHelper.TypeTemplate.get(annotation);
            final String typename = PlaceholderReplacer.replace(template, replacements);
            node.setType(type.isArrayType() ? typename + "[]" : typename);
        }
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

    private PrimitiveType[] getTypes(AnnotationExpr template) {
        if (template.isSingleMemberAnnotationExpr()) {
            SingleMemberAnnotationExpr single = template.asSingleMemberAnnotationExpr();
            return single.getMemberValue().asArrayInitializerExpr().getValues().stream()
                    .map(value -> value.asStringLiteralExpr().asString())
                    .map(PrimitiveType::valueOf)
                    .toArray(PrimitiveType[]::new);
        }

        return PrimitiveType.values();
    }
}
