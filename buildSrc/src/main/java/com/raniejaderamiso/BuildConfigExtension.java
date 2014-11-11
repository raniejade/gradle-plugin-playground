package com.raniejaderamiso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ranie Jade Ramiso
 */
public class BuildConfigExtension {
    private String packageName;
    private String outputDirectory;

    private List<List<String>> lines = new ArrayList<>();
    private List<String> imports = new ArrayList<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void importPackage(String packageName) {
        imports.add(packageName);
    }

    public void config(String type, String name, String value) {
        lines.add(Arrays.asList(type, name, value));
    }

    List<List<String>> getLines() {
        return lines;
    }

    List<String> getImports() {
        return imports;
    }


}
