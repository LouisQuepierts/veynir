package net.quepierts.codegen.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CodegenPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {
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

        project.getPlugins().withId("java", plugin -> {
            project.getExtensions().configure(SourceSetContainer.class, sourceSets -> {
                SourceSet main = sourceSets.getByName("main");
                main.getJava().srcDir("src/generated/java");
                main.getResources().srcDir("src/generated/resources");
            });

            project.getTasks().withType(JavaCompile.class).named("compileJava", task -> {
                task.exclude("**/_TEMPLATE_*.java");
            });

            project.getTasks().named("build").configure(task -> {
                task.dependsOn("codegen");
            });
        });

        project.getPlugins().withId("idea", plugin -> {
            project.getExtensions().configure(IdeaModel.class, idea -> {
                idea.getModule().getGeneratedSourceDirs()
                        .add(new File(project.getProjectDir(), "src/generated/java"));
            });
        });
    }
}
