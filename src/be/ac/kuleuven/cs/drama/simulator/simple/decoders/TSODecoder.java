/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/MKHDecoder.java,v 1.1.1.1 2015/02/19 19:13:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class TSODecoder extends OpcodeDecoder {

    public TSODecoder() {
    }

    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().ptw().setCC(internalmachine.cpu().ptw().getInterruptFlag(instruction.acc()) ? 1 : 0);
    }

    public String mnemocode() {
        return "TSO";
    }

    public int opcode() {
        return 54;
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
