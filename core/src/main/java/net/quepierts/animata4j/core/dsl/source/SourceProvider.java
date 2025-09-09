package net.quepierts.animata4j.core.dsl.source;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class SourceProvider {
    public static SourceProvider of(@NotNull final String source) {
        if (source.isBlank()) {
            throw new IllegalArgumentException("Source cannot be blank.");
        }

        List<String> lines = new ArrayList<>();
        IntList lineMapping = new IntArrayList();
        int i = 0;
        for (String string : source.split("\n")) {
            i++;
            if (string.isBlank()) {
                continue;
            }

            lines.add(string);
            lineMapping.add(i);
        }

        return new SourceProvider(
                lines.toArray(String[]::new),
                lineMapping.toIntArray()
        );
    }

    public static SourceProvider of(@NotNull final File file) {
        try (final FileReader fileReader = new FileReader(file)) {
            return SourceProvider.of(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SourceProvider of(@NotNull final Reader reader) {
        List<String> lines = new ArrayList<>();
        IntList lineMapping = new IntArrayList();

        int number = 0;
        try (BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                String line = br.readLine();
                number ++;

                if (line.isBlank()) {
                    continue;
                }

                lines.add(line);
                lineMapping.add(number);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new SourceProvider(
                lines.toArray(String[]::new),
                lineMapping.toIntArray()
        );
    }

    @Getter
    private final String source;
    private final String[] lines;
    private final int[] lineMapping;

    SourceProvider(String[] lines, int[] lineMapping) {
        this.source = "";
        this.lines = lines;
        this.lineMapping = lineMapping;
    }

    public String getLine(int line) {
        return this.lines[line];
    }

    public int getLineNumber(int line) {
        return this.lineMapping[line];
    }

    public char charAt(int line, int col) {
        String content = this.getLine(line);
        return col == content.length() ? '\n' : content.charAt(col);
    }

    public boolean isEof(int line, int col) {
        return line == this.lines.length;
    }
}
