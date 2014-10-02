/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/BIGDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BIGDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class BIGDecoder extends OpcodeDecoder {

   public BIGDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      long l = getOperandValue(instruction, internalmachine) % 10000L;
      long l1 = internalmachine.cpu().register(instruction.acc());
      setCC(internalmachine, l1);
      internalmachine.ram().setCell((int)l, l1);
   }

   public String mnemocode() {
      return "BIG";
   }

   public int opcode() {
      return 12;
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

}
