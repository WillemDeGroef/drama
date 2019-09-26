/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/VGLDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   VGLDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;
import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class VGLDecoder extends OpcodeDecoder {

    public VGLDecoder() {
    }


    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        long l = getOperandValue(instruction, internalmachine);
        long l1 = internalmachine.cpu().register(instruction.acc());
        long l2 = l1 - l;
        l2 = NumberFormat.toDramaNumber(l2);
        setCC(internalmachine, l2);
    }

    public String mnemocode() {
        return "VGL";
    }

    public int opcode() {
        return 31;
    }

    protected boolean usesAcc() {
        return true;
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
