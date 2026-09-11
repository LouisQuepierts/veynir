package net.quepierts.veynir.dsl;

import lombok.Getter;

public class StringSplitter {

    private final char[] chars;

    @Getter private final int end;
    @Getter private int ptr;

    public static StringSplitter of(String string) {
        return new StringSplitter(string.toCharArray());
    }

    public static StringSplitter of(char[] chars) {
        return new StringSplitter(chars);
    }

    public static StringSplitter of(StringBuilder builder) {
        char[] chars = new char[builder.length()];
        builder.getChars(0, builder.length(), chars, 0);
        return new StringSplitter(chars);
    }

    private StringSplitter(char[] chars) {
        this.chars = chars;
        this.ptr = 0;

        while (this.ptr < chars.length && Character.isWhitespace(chars[this.ptr])) {
            this.ptr++;
        }

        int end = chars.length - 1;
        while (end > this.ptr && Character.isWhitespace(chars[end])) {
            end--;
        }
        this.end = end;
    }

    public String next() {
        if (!hasNext()) {
            return "";
        }

        int left = this.ptr;
        int amount = 0;
        while (this.ptr <= this.end && !Character.isWhitespace(this.chars[this.ptr])) {
            this.ptr++;
            amount++;
        }
        this.skipWhitespaces();
        return String.valueOf(this.chars, left, amount);
    }

    public String remain() {
        if (!hasNext()) {
            return "";
        }
        return String.valueOf(this.chars, this.ptr, this.end - this.ptr + 1);
    }

    public String[] auto(int amount) {
        String[] result = new String[amount + 1];
        for (int i = 0; i < amount; i++) {
            String next = this.next();
            if (next.isEmpty()) {
                throw new IllegalArgumentException("Not enough arguments");
            }
            result[i] = next;
        }
        result[amount] = this.remain();
        return result;
    }

    private void skipWhitespaces() {
        while (this.ptr <= this.end && Character.isWhitespace(this.chars[this.ptr])) {
            this.ptr++;
        }
    }

    public boolean hasNext() {
        return this.ptr <= this.end;
    }
}
