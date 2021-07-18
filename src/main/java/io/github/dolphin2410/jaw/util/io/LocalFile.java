package io.github.dolphin2410.jaw.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class that includes extra utilities for local files.
 *
 * @author dolphin2410
 */
public class LocalFile {
    private final File file;
    public LocalFile(File file) {
        this.file = file;
    }
    public File getFile() {
        return file;
    }
    private String concentrateLines(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append("\n");
        }
        return builder.toString();
    }
    public String readAllContents() {
        try {
            return concentrateLines(Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeContent(String data) {
        try {
            Files.writeString(Paths.get(file.getAbsolutePath()), data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void appendContent(String data) {
        writeContent(readAllContents() + data);
    }
    public void prependContent(String data) {
        writeContent(data + readAllContents());
    }
}
