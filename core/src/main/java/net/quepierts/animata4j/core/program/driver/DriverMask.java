package net.quepierts.animata4j.core.program.driver;

public interface DriverMask {

    boolean isEnabled(int index);

    void enable(int index);

    void disable(int index);

    void enableRange(int start, int end);

    void disableRange(int start, int end);


    final class Impl {
        private final boolean[] mask;

        public Impl(int size) {
            this.mask = new boolean[size];
        }

        public boolean isEnabled(int index) {
            return mask[index];
        }

        public void enableRange(int start, int end) {
            for (int i = start; i < end; i++) {
                mask[i] = true;
            }
        }

        public void disableRange(int start, int end) {
            for (int i = start; i < end; i++) {
                mask[i] = false;
            }
        }

        public void enable(int index) {
            mask[index] = true;
        }

        public void disable(int index) {
            mask[index] = false;
        }
    }

    final class View implements DriverMask {
        private final DriverMask mask;
        private final int start;

        public View(DriverMask mask, int start) {
            this.mask = mask;
            this.start = start;
        }

        public boolean isEnabled(int index) {
            return mask.isEnabled(start + index);
        }

        @Override
        public void enable(int index) {
            mask.enable(start + index);
        }

        @Override
        public void disable(int index) {
            mask.disable(start + index);
        }

        public void enableRange(int start, int end) {
            mask.enableRange(this.start + start, this.start + end);
        }

        @Override
        public void disableRange(int start, int end) {
            mask.disableRange(this.start + start, this.start + end);
        }
    }

}
