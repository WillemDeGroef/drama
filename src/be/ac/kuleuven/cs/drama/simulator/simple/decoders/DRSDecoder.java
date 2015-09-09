/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/DRSDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   DRSDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import be.ac.kuleuven.cs.drama.simulator.simple.*;

public class DRSDecoder extends OpcodeDecoder {

   public DRSDecoder() {}

   protected final void decodeImpl(Instruction instruction, InternalMachine internalmachine) {
      int l = ((int)internalmachine.cpu().register(0))%10000;
      char c;
      int i = -1;
      long n = 0L;
      while((c = (char)((n = (++i%3) == 0 ? internalmachine.ram().cell(l++%10000)%1000000000L : n/1000)%1000)) != '\0') {
          internalmachine.monitor().writeChar(c);
          if (i == 30000)
        	  break;
      }
      //internalmachine.monitor().writeChar('\t');
   }

   public String mnemocode() {
      return "DRS";
   }

   public int opcode() {
      return 79;
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
