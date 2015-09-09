/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/VSPDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   VSPDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.Instruction;
import be.ac.kuleuven.cs.drama.simulator.simple.InternalMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.OpcodeDecoder;

public class VSPDecoder extends OpcodeDecoder {

   public VSPDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      long l = getOperandValue(instruction, internalmachine);

      if (test(instruction.acc(), internalmachine))
         internalmachine.cpu().ptw().setBT(l);
   }

   public String mnemocode() {
      return "VSP";
   }

   public int opcode() {
      return 33;
   }

   private boolean test(int i, InternalMachine internalmachine) {
      switch (i) {

      case 0:
    	 return internalmachine.cpu().ptw().getSOI();

      case 1:
         return internalmachine.cpu().ptw().getCC() == 0;

      case 2:
         return internalmachine.cpu().ptw().getCC() != 2;

      case 3:
         return internalmachine.cpu().ptw().getCC() != 1;

      case 4:
         return internalmachine.cpu().ptw().getOVI();

      case 5:
         return ! internalmachine.cpu().ptw().getOVI();

      case 6:
         return internalmachine.cpu().ptw().getCC() == 1;

      case 7:
         return internalmachine.cpu().ptw().getCC() == 2;

      case 8:
         return internalmachine.cpu().ptw().getCC() != 0;

      case 9:
         return ! internalmachine.cpu().ptw().getSOI();
      }

      return false;
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
   
   protected boolean isPrivileged() {
	   return false;
   }

   /*
   private static final int SO = 0;
   private static final int NUL = 1;
   private static final int NNEG = 2;
   private static final int NPOS = 3;
   private static final int OVL = 4;
   private static final int GOVL = 5;
   private static final int POS = 6;
   private static final int NEG = 7;
   private static final int NNUL = 8;
   private static final int GSO = 9;
   */
}
