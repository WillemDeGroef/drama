/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/MKHDecoder.java,v 1.1.1.1 2015/02/19 19:13:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class TSMDecoder extends OpcodeDecoder {

    public TSMDecoder() {
    }

    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().ptw().setCC(internalmachine.cpu().ptw().getElement(instruction.acc() + 10));
    }

    public String mnemocode() {
        return "TSM";
    }

    public int opcode() {
        return 53;
    }

    protected boolean usesAcc() {
        return true;
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
