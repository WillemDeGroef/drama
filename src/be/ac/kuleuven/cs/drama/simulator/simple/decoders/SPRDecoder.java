/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/SPRDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   SPRDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.Instruction;
import be.ac.kuleuven.cs.drama.simulator.simple.InternalMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.OpcodeDecoder;

public class SPRDecoder extends OpcodeDecoder {

    public SPRDecoder() {
    }


    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().ptw().setBT(getOperandValue(instruction, internalmachine));
    }

    public String mnemocode() {
        return "SPR";
    }

    public int opcode() {
        return 32;
    }

    protected boolean usesAcc() {
        return false;
    }

    protected boolean usesAddressing() {
        return true;
    }

    protected boolean usesIndexation() {
        return true;
    }

    protected boolean usesOperand() {
        return true;
    }

    protected boolean isPrivileged() {
        return false;
    }

}
