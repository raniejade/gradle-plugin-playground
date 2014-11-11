package com.raniejaderamiso;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.tooling.BuildException;

/**
 * @author Ranie Jade Ramiso
 */
public class AptPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        if (project.getPlugins().hasPlugin("java")) {
            project.getConfigurations().create("apt");
            project.getExtensions().create("apt", AptPluginExtension.class);

            applyToJavaProject(project);
        } else {
            throw new BuildException("Not a java project!", null);
        }
    }

    private void applyToJavaProject(Project project) {

        final JavaCompile compileJavaTask = ((JavaCompile) project.getTasks().getByName("compileJava"));

        final Task applyAptCompilerArgsTask = project.getTasks().create("applyAptCompilerArgs");

        applyAptCompilerArgsTask.doFirst(task -> {
            final File outputDir = project.file(getOutputDir(project));

            final String path = outputDir.getPath();

            final Configuration apt = project.getConfigurations().getByName("apt");

            final List<String> compilerArgs = Arrays.asList(
                "-processorpath", apt.getAsPath(),
                "-s", path
            );

            compileJavaTask.getOptions().getCompilerArgs().addAll(compilerArgs);


            compileJavaTask.setSource(compileJavaTask.getSource().filter(new Spec<File>() {
                @Override
                public boolean isSatisfiedBy(File file) {
                    return !file.getPath().startsWith(path);
                }
            }));

            compileJavaTask.doFirst(task1 -> {
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }
            });
        });

        compileJavaTask.dependsOn(applyAptCompilerArgsTask);
    }

    private String getOutputDir(Project project) {
        String outputDir = ((AptPluginExtension) project.getExtensions().getByName("apt")).getOutputDir();

        if (outputDir == null) {
            outputDir = project.getProjectDir().toPath().resolve("generated").resolve("apt").toString();
        }

        return outputDir;
    }
}
