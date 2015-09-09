/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/decoders/AFTDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   AFTDecoder.java

package be.ac.kuleuven.cs.drama.simulator.simple.decoders;

import java.math.BigInteger;

// Referenced classes of package be.ac.kuleuven.cs.drama.simulator.simple.decoders:
//            MathDecoder

public class AFTDecoder extends MathDecoder {

   public AFTDecoder() {}








   public String mnemocode() {
      return "AFT";
   }

   public int opcode() {
      return 22;
   }

   protected BigInteger operate(BigInteger biginteger, BigInteger biginteger1) {
      return biginteger.subtract(biginteger1);
   }

	protected boolean hasOverflow(long registerValue, long operand, long result) {
		return result < -5000000000L || result > 4999999999L;
	}

}
