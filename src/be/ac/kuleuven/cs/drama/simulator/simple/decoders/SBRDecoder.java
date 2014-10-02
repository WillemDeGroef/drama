/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/SBRDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   SBRDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;
import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class SBRDecoder extends OpcodeDecoder {

   public SBRDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      long l = getOperandValue(instruction, internalmachine);
      long l1 = internalmachine.cpu().ptw().getBT();
      internalmachine.cpu().setRegister(9, internalmachine.cpu().register(9) - 1L);
      internalmachine.ram().setCell((int)internalmachine.cpu().register(9), l1);
      internalmachine.cpu().ptw().setBT(l);
   }

   public String mnemocode() {
      return "SBR";
   }

   public int opcode() {
      return 41;
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

}
