package net.quepierts.animata4j.codegen.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.google.common.collect.ImmutableMap;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

@SuppressWarnings("unchecked")
public class GeneratorManager {
    private final Map<String, Generator> generators;
    private final Path dstPath;

    public GeneratorManager(String dstPath) {
        this.dstPath = Path.of(dstPath);
        ImmutableMap.Builder<String, Generator> builder = ImmutableMap.builder();
        // load from services
        System.out.println("Build Generator Map");
        for (Generator generator : ServiceLoader.load(Generator.class)) {
            SupportedAnnotationTypes types = generator.getClass().getAnnotation(SupportedAnnotationTypes.class);

            if (types != null) {
                for (String type : types.value()) {
                    builder.put(type, generator);
                }
            }
        }

        this.generators = builder.build();
    }


    public void apply(final CompilationUnit unit, Path relativePath) {
        unit.getImports().removeIf(importDecl -> {
            final String name = importDecl.getNameAsString();
            return name.startsWith("net.quepierts.animata4j.codegen");
        });

        Optional<ClassOrInterfaceDeclaration> first = unit.findFirst(ClassOrInterfaceDeclaration.class);
        if (first.isPresent()) {
            final ClassOrInterfaceDeclaration decl = first.get();

            AnnotationExpr anno = null;

            Optional<AnnotationExpr> template = decl.getAnnotationByName("Template");
            template.ifPresent(decl::remove);

            for (AnnotationExpr annotation : decl.getAnnotations()) {
                final String name = annotation.getNameAsString();

                if (this.generators.containsKey(name)) {
                    anno = annotation;
                    break;
                }
            }

            if (anno != null) {
                final Generator generator = this.generators.get(anno.getNameAsString());

                if (generator.shouldRemoveAnnotation()) {
                    decl.remove(anno);
                }

                generator.generate(unit, anno, this.dstPath, relativePath);
            }
        }
    }
}
