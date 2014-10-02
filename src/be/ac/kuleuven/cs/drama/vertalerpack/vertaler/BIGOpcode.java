/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/BIGOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;


/**
 * OpcodeCodePart implementation for:
 * 
 * OND
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class BIGOpcode extends OpcodeCodePart {

   public BIGOpcode() {}









   protected LetterCodePart sibling() {
      return new BIGOpcode();
   }

   public String keyword() {
      return "BIG";
   }

   protected boolean acceptNbArguments(int nb) {
      return (nb == 2);
   }


   protected int getOpcode() {
      return 12;
   }

   protected int getMode()
   throws AbnormalTerminationException {
      return getAddressingMode() * 10 + getCrementMode();
   }

   private int getAddressingMode()
   throws AbnormalTerminationException {
      switch (getModeType()) {

      case MODE_W:
         throw new AbnormalTerminationException(".w is ongeldige mode") ;

      case MODE_A:
         throw new AbnormalTerminationException(".a is ongeldige mode");

      case MODE_D:
         return 2;

      case MODE_I:
         return 3;

      case MODE_DEFAULT:
         return 2;
      }

      throw new RuntimeException("BIGOpcode.getAddressingMode(): unexpected mode");

   }

   private int getCrementMode()
   throws AbnormalTerminationException {
      return getIndexation(getIndexationString(getArgument(1)));
   }

   protected int getAcc()
   throws AbnormalTerminationException {
      return getRegister(getArgument(0));
   }

   protected int getIdx()
   throws AbnormalTerminationException {
      String is = getIndexationString(getArgument(1));
      int mode = getCrementMode();

      if (mode == NOT_INDEXED) {
         return 0;
      }

      if ((mode == PRE_INCREMENT) || (mode == PRE_DECREMENT)) {
         is = is.substring(1);
      }

      if ((mode == POST_INCREMENT) || (mode == POST_DECREMENT)) {
         is = is.substring(0, is.length() - 1);
      }

      return getRegister(is);

   }

   protected int getOperand()
   throws AbnormalTerminationException {
      String address = stripIndexation(getArgument(1));

      int result = evaluateSmall(address, getVertaler());

      if (result < 0 && getCrementMode() == NOT_INDEXED) {
         throw new AbnormalTerminationException("operandwaarde mag niet negatief: " + result);
      }

      if (result < 0) {
         result += MEMORY_SIZE;
      }

      return result;

   }

}
