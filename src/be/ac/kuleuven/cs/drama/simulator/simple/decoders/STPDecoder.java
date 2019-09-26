/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/STPDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class STPDecoder extends OpcodeDecoder {

    public STPDecoder() {
    }


    protected final void decodeImpl(Instruction instruction, InternalMachine internalMachine) {
        internalMachine.finish();
        internalMachine.halt();
    }

    public String mnemocode() {
        return "STP";
    }

    public int opcode() {
        return 99;
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
        return false;
    }

    protected boolean isPrivileged() {
        return true;
    }

}
