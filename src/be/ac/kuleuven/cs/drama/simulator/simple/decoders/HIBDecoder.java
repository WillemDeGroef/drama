/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/HIBDecoder.java,v 1.1.1.1 2014/10/14 19:13:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   HIBDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class HIBDecoder extends OpcodeDecoder {

   public HIBDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      long l = internalmachine.cpu().addGBE(getOperandValue(instruction, internalmachine)) % 10000L;
      internalmachine.cpu().setGBE(l);
   }

   public String mnemocode() {
      return "HIB";
   }

   public int opcode() {
      return 91;
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
   
   protected boolean isPrivileged() {
	   return true;
   }

}
