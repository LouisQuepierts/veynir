package net.quepierts.animata4j.core.dsl.source;

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
        return new SourceProvider(source);
    }

    public static SourceProvider of(@NotNull final File file) {
        try (final FileReader fileReader = new FileReader(file)) {
            return SourceProvider.of(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SourceProvider of(@NotNull final Reader reader) {
        int length = 0;
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                String line = br.readLine();
                lines.add(line);
                length += line.length();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new SourceProvider(lines, length);
    }

    @Getter
    private final String source;
    private final List<String> lines;
    private final int length;

    SourceProvider(String source) {
        this.source = source;
        this.lines = List.of(source.split("\n"));
        this.length = source.length();
    }

    SourceProvider(List<String> lines, int length) {
        this.source = "";
        this.lines = lines;
        this.length = length;
    }

    public String getLine(int line) {
        return this.lines.get(line - 1);
    }

    public char charAt(int line, int col) {
        String content = this.getLine(line);
        return col == content.length() + 1 ? '\n' : content.charAt(col - 1);
    }

    public int length() {
        return this.length;
    }
}
