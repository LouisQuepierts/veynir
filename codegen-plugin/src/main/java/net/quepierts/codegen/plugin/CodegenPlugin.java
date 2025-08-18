package net.quepierts.codegen.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CodegenPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {
        project.getTasks().named("compileJava", task -> {
            task.getInputs().getFiles().filter(
                    file -> !file.getName().startsWith("_TEMPLATE_")
            );
        });

        project.getTasks().register("codegen", task -> {
            task.setGroup("build");
            task.setDescription("Generate code from template");

            task.doFirst(t -> {
                File dir = new File(project.getProjectDir(), "src/generated/java");
                if (dir.exists()) {
                    dir.delete();
                }

                final String src = project.getProjectDir() + "/src/main/java";
                final String dst = project.getProjectDir() + "/src/generated/java";

                new CodegenPipeline(src, dst).run();
            });
        });
    }
}
