/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/OPTDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   OPTDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import java.math.BigInteger;

// Referenced classes of package be.ac.kuleuven.cs.drama.simulator.simple.decoders:
//            MathDecoder

public class OPTDecoder extends MathDecoder {

   public OPTDecoder() {}








   public String mnemocode() {
      return "OPT";
   }

   public int opcode() {
      return 21;
   }

   protected BigInteger operate(BigInteger biginteger, BigInteger biginteger1) {
      return biginteger.add(biginteger1);
   }

}
