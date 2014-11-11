package com.raniejaderamiso;

import java.util.List;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Ranie Jade Ramiso
 */
public class BuildConfigPlugin implements Plugin<Project> {
    private BuildConfigExtension extension;

    @Override
    public void apply(Project project) {
        if (project.getPlugins().hasPlugin("java")) {
            extension = project.getExtensions().create("buildConfig", BuildConfigExtension.class);

            final BuildConfigTask buildConfigTask = project.getTasks().create("generateConfig", BuildConfigTask.class);

            final JavaCompile compileJavaTask = ((JavaCompile) project.getTasks().getByName("compileJava"));
            compileJavaTask.dependsOn(buildConfigTask);

            project.afterEvaluate(p -> configureTask(buildConfigTask));
        }
    }

    public void configureTask(BuildConfigTask task) {
        task.setPackageName(getPackageName());
        task.setOutputDirectory(getOutputDirectory(task.getProject()));
        task.setImports(getImports());
        task.setLines(getLines());
    }

    private String getPackageName() {
        String packageName = extension.getPackageName();

        if (packageName == null) {
            packageName = "";
        }
        return packageName;
    }

    private String getOutputDirectory(Project project) {
        String outputDirectory = extension.getOutputDirectory();

        if (outputDirectory == null || outputDirectory.isEmpty()) {
            outputDirectory = project.getProjectDir().toPath().resolve("generated").resolve("java").toString();
        }

        return outputDirectory;
    }

    private List<List<String>> getLines() {
        return extension.getLines();
    }

    private List<String> getImports() {
        return extension.getImports();
    }
}
