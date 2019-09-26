/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/ONDDecoder.java,v 1.1.1.1 2015/02/19 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.Instruction;
import be.ac.kuleuven.cs.drama.simulator.simple.InternalMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.OpcodeDecoder;

public class ONDDecoder extends OpcodeDecoder {

    public ONDDecoder() {
    }

    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().ptw().setInterruptFlag(1, true);
    }

    public String mnemocode() {
        return "OND";
    }

    public int opcode() {
        return 61;
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
        return false;
    }

}
