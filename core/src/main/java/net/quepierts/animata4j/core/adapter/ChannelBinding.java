package net.quepierts.animata4j.core.adapter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.pipeline.AnimationResultView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChannelBinding {

    private final Entry[] entries;

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public void apply(AnimationResultView view) {
        for (var entry : this.entries) {
            view.read(entry.channel(), entry.consumer());
        }
    }

    static final class Entry {
        private final int channel;
        private final PropertyAccessor consumer;

        Entry(
                int channel,
                PropertyAccessor consumer
        ) {
            this.channel = channel;
            this.consumer = consumer;
        }

        public int channel() {
            return channel;
        }

        public PropertyAccessor consumer() {
            return consumer;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Entry) obj;
            return this.channel == that.channel &&
                    Objects.equals(this.consumer, that.consumer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(channel, consumer);
        }

        @Override
        public String toString() {
            return "Entry[" +
                    "channel=" + channel + ", " +
                    "consumer=" + consumer + ']';
        }
    }

    public static final class Builder {
        private final List<Entry> entries = new ArrayList<>();

        public @NotNull Builder bind(int channel, @NotNull PropertyAccessor accessor) {
            if (channel > -1) {
                this.entries.add(new Entry(channel, accessor));
            }
            return this;
        }

        public @NotNull Builder bind(int channel, @NotNull Supplier<PropertyAccessor> supplier) {
            if (channel > -1) {
                this.entries.add(new Entry(channel, supplier.get()));
            }
            return this;
        }

        public @NotNull ChannelBinding build() {
            return new ChannelBinding(entries.toArray(Entry[]::new));
        }
    }

}
