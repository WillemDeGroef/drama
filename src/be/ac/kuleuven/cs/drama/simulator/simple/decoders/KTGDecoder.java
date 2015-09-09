/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/KTGDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   KTGDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.Instruction;
import be.ac.kuleuven.cs.drama.simulator.simple.InternalMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.OpcodeDecoder;

public class KTGDecoder extends OpcodeDecoder {

   public KTGDecoder() {}








   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      internalmachine.cpu().ptw().setBT(internalmachine.ram().cell((int)internalmachine.cpu().register(9)));
      internalmachine.cpu().setRegister(9, internalmachine.cpu().register(9) + 1L);
   }

   public String mnemocode() {
      return "KTG";
   }

   public int opcode() {
      return 42;
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
	   return false;
   }

}
