package net.quepierts.codegen.plugin;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import net.quepierts.codegen.plugin.generator.GeneratorManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

class CodegenPipeline {
    private final GeneratorManager manager;
    private final File source;
    private final Path sourcePath;
    
    CodegenPipeline(final String src, final String dst) {
        this.source = new File(src);
        this.sourcePath = source.toPath();
        this.manager = new GeneratorManager(dst);
    }
    
    public void run() {
        PackageWalker.walk(
                source,
                this::filter,
                this::process
        );
    }

    private void process(final File sourceFile) {
        try {
            Path path = sourceFile.toPath();
            Path rel = sourcePath.relativize(path);
            System.out.println("Processing " + rel);
            CompilationUnit unit = StaticJavaParser.parse(sourceFile);
            this.manager.apply(unit, rel);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + sourceFile.getAbsolutePath());
        }
    }

    private boolean filter(final String name) {
        return name.endsWith(".java") && name.startsWith("_TEMPLATE_");
    }
}
