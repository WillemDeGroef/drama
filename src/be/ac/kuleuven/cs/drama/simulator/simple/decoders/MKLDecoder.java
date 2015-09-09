/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/MKLDecoder.java,v 1.1.1.1 2015/02/19 19:13:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class MKLDecoder extends OpcodeDecoder {

   public MKLDecoder() {}
   
   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
	   internalmachine.cpu().ptw().setElement(instruction.acc() + 10, 0);
   }

   public String mnemocode() {
      return "MKL";
   }

   public int opcode() {
      return 51;
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
