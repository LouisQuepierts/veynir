package net.quepierts.veynir.dsl.source;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class ProcessesSource implements SourceProvider {
    public static ProcessesSource of(@NotNull final String source) {
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

        return new ProcessesSource(
                lines.toArray(String[]::new),
                lineMapping.toIntArray()
        );
    }

    public static ProcessesSource of(@NotNull final File file) {
        try (final FileReader fileReader = new FileReader(file)) {
            return ProcessesSource.of(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProcessesSource of(@NotNull final Reader reader) {
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

        return new ProcessesSource(
                lines.toArray(String[]::new),
                lineMapping.toIntArray()
        );
    }

    private final String[] lines;
    private final int[] lineMapping;

    public ProcessesSource(String[] lines, int[] lineMapping) {
        this.lines = lines;
        this.lineMapping = lineMapping;
    }

    @Override
    public String getLine(int line) {
        return this.lines[line];
    }

    @Override
    public int getLineNumber(int line) {
        return this.lineMapping[line];
    }

    @Override
    public char charAt(int line, int col) {
        String content = this.getLine(line);
        return col == content.length() ? '\n' : content.charAt(col);
    }

    @Override
    public boolean isEof(int line) {
        return line == this.lines.length;
    }

    @Override
    public boolean isEol(int line, int col) {
        return this.isEof(line) || col == this.getLine(line).length();
    }
}
