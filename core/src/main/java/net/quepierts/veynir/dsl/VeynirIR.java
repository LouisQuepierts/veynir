package net.quepierts.veynir.dsl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VeynirIR {
    private byte opcode;
    private String dst;
    private String op1;
    private String op2;

    @Override
    public String toString() {
        return OpCodes.getName(this.opcode) + "\t" + this.dst + "\t" + this.op1 + "\t" + this.op2;
    }
}
