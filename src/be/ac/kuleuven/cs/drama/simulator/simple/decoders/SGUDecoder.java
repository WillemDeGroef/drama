/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/SGUDecoder.java,v 1.1.1.1 2014/10/14 19:13:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   SGUDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class SGUDecoder extends OpcodeDecoder {

    public SGUDecoder() {
    }


    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().turnGBE(false);
        internalmachine.cpu().ptw().setBT(getOperandValue(instruction, internalmachine));
    }

    public String mnemocode() {
        return "SGU";
    }

    public int opcode() {
        return 93;
    }

    protected boolean usesAcc() {
        return false;
    }

    protected boolean usesAddressing() {
        return false;
    }

    protected boolean usesIndexation() {
        return false;
    }

    protected boolean usesOperand() {
        return true;
    }

    protected boolean isPrivileged() {
        return true;
    }

}
