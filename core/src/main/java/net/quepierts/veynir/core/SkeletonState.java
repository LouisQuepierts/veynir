package net.quepierts.veynir.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkeletonState {

    private boolean[] mask;

    public boolean getMask(int bone) {
        return this.mask == null || this.mask[bone];
    }
}
