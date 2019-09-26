/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/MathDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   MathDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;
import be.ac.kuleuven.cs.drama.simulator.simple.*;

import java.math.BigInteger;

public abstract class MathDecoder extends OpcodeDecoder {

    public MathDecoder() {
    }


    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        long l = getOperandValue(instruction, internalmachine);
        long l1 = internalmachine.cpu().register(instruction.acc());
        long l2 = operate(BigInteger.valueOf(l1), BigInteger.valueOf(l)).mod(MODULO).longValue();
        l2 = NumberFormat.toDramaNumber(l2);
        setCC(internalmachine, l2);
        setOVI(internalmachine, hasOverflow(l1, l, l2));
        internalmachine.cpu().setRegister(instruction.acc(), l2);
    }

    protected abstract BigInteger operate(BigInteger biginteger, BigInteger biginteger1);

    protected abstract boolean hasOverflow(long registerValue, long operand, long result);

    protected final boolean usesAcc() {
        return true;
    }

    protected final boolean usesAddressing() {
        return true;
    }

    protected final boolean usesIndexation() {
        return true;
    }

    protected final boolean usesOperand() {
        return true;
    }

    protected final boolean isPrivileged() {
        return false;
    }

    private static final BigInteger MODULO = BigInteger.valueOf(0x2540be400L);

}
