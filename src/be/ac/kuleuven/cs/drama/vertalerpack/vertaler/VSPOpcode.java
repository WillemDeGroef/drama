/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/VSPOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * VSP
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class VSPOpcode extends OpcodeCodePart {

   protected LetterCodePart sibling() {
      return new VSPOpcode();
   }

   public String keyword() {
      return "VSP";
   }

   protected boolean acceptNbArguments(int nb) {
      return (nb == 2) || (nb == 1);
   }

   protected int getOpcode() {
      if (nbArguments() == 1) {
         addFirstArgument("NNEG");
      }

      return 33;
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

      return getTest(getArgument(0));
   }

   private int getTest(String test)
   throws AbnormalTerminationException {
      String str = StringUtils.trimSpaces(test).toUpperCase();

      if (str.equals("SO")) {
         return 0;
      }

      if (str.equals("GEL") || str.equals("NUL")) {
         return 1;
      }

      if (str.equals("GRG") || str.equals("NNEG")) {
         return 2;
      }

      if (str.equals("KLG") || str.equals("NPOS")) {
         return 3;
      }

      if (str.equals("GR") || str.equals("POS")) {
         return 6;
      }

      if (str.equals("KL") || str.equals("NEG")) {
         return 7;
      }

      if (str.equals("NGEL") || str.equals("NNUL")) {
         return 8;
      }

      if (str.equals("GSO")) {
         return 9;
      }

      throw new AbnormalTerminationException ("onverwachte test in VSP:" + test);
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
         is = is.substring(0, is.length() - 2);
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
