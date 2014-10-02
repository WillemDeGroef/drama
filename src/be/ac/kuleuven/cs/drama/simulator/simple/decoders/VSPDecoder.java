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

import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;
import be.ac.kuleuven.cs.drama.simulator.simple.*;
import java.io.PrintStream;

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

      case 0:        // '\0'







         System.out.println("VSP conditie SO niet geimplementeerd");
         return false;

      case 1:        // '\001'







         return internalmachine.cpu().ptw().getCC() == 0;

      case 2:        // '\002'
         return internalmachine.cpu().ptw().getCC() != 2;

      case 3:        // '\003'
         return internalmachine.cpu().ptw().getCC() != 1;

      case 4:        // '\004'
         System.out.println("VSP conditie OVL niet geimplementeerd");
         return false;

      case 5:        // '\005'
         System.out.println("VSP conditie GOVL niet geimplementeerd");
         return true;

      case 6:        // '\006'
         return internalmachine.cpu().ptw().getCC() == 1;

      case 7:        // '\007'
         return internalmachine.cpu().ptw().getCC() == 2;

      case 8:        // '\b'
         return internalmachine.cpu().ptw().getCC() != 0;

      case 9:        // '\t'
         System.out.println("VSP conditie GSO niet geimplementeerd");
         return true;
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
}
