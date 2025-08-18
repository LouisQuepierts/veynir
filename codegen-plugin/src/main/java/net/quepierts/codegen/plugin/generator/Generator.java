package net.quepierts.codegen.plugin.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;
import net.quepierts.codegen.plugin.PlaceholderReplacer;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public abstract class Generator {
    public abstract void generate(final CompilationUnit source, AnnotationExpr annotation, final Path destination, Path relativePath);

    public abstract boolean shouldRemoveAnnotation();

    protected void write(
            final @NotNull CompilationUnit source,
            final @NotNull Path target
    ) {
        try {
            Files.createDirectories(target.getParent());
            Files.write(target, source.toString()
                    .replace("_TEMPLATE_", "")
                    .getBytes()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void write(
            final @NotNull CompilationUnit source,
            final @NotNull Path target,
            final @NotNull Map<String, String> replacements
    ) {
        try {
            Files.createDirectories(target.getParent());
            String content = PlaceholderReplacer.replace(
                    source.toString().replace("_TEMPLATE_", ""),
                    replacements
            );
            Files.write(target, content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
