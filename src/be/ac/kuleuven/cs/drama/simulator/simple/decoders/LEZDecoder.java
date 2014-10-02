/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/LEZDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   LEZDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class LEZDecoder extends OpcodeDecoder {

   public LEZDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      long l = internalmachine.input().readLong();
      setCC(internalmachine, l);
      internalmachine.cpu().setRegister(0, l);
   }

   public String mnemocode() {
      return "LEZ";
   }

   public int opcode() {
      return 71;
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

}
