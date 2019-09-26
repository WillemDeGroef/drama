/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/KTODecoder.java,v 1.1.1.1 2015/02/19 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.Instruction;
import be.ac.kuleuven.cs.drama.simulator.simple.InternalMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.OpcodeDecoder;

public class KTODecoder extends OpcodeDecoder {

    public KTODecoder() {
    }

    protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
        internalmachine.cpu().ptw().setValue(internalmachine.ram().getCell((int) internalmachine.cpu().getReg(9)));
        internalmachine.cpu().setRegister(9, internalmachine.cpu().getReg(9) + 1L);
    }

    public String mnemocode() {
        return "KTO";
    }

    public int opcode() {
        return 62;
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
