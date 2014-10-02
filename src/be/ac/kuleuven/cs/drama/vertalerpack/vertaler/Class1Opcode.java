/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Class1Opcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * HIA
 * OPT
 * AFT
 * VER
 * DEL
 * VGL
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */


public class Class1Opcode extends OpcodeCodePart {

   private final int _opcode;
   private final String _keyword;

   public Class1Opcode(String keyword, int opcode) {
      _keyword = keyword;
      _opcode = opcode;
   }

   private Class1Opcode(Class1Opcode sibl) {
      _keyword = sibl._keyword;
      _opcode = sibl._opcode;
   }

   protected LetterCodePart sibling() {
      return new Class1Opcode(this);
   }

   public String keyword() {
      return _keyword;
   }

   protected boolean acceptNbArguments(int nb) {
      return (nb == 2);
   }


   protected int getOpcode() {
      return _opcode;
   }

   protected int getMode()
   throws AbnormalTerminationException {
      return getAddressingMode() * 10 + getCrementMode();
   }

   private int getAddressingMode() {
      switch (getModeType()) {

      case MODE_W:
         return 1;

      case MODE_A:
         return 2;

      case MODE_D:
         return 3;

      case MODE_I:
         return 4;

      case MODE_DEFAULT:

         if ((isRegisterRegister())) {
            return 1;
         } else {
            return 3;
         }

      }

      throw new RuntimeException("Class1Opcode.getAddressingMode(): unexpected mode");

   }

   private int getCrementMode()
   throws AbnormalTerminationException {
      if (isRegisterRegister()) {
         return INDEXED;
      }

      return getIndexation(getIndexationString(getArgument(1)));

   }

   protected int getAcc()
   throws AbnormalTerminationException {
      //debug: System.out.println("acc of  " + getArgLine()+ " " + getRegister(getArgument(0)));
      return getRegister(getArgument(0));
   }

   private boolean isRegisterRegister() {
      try {
         getRegister(getArgument(1));
         return true;
      } catch (AbnormalTerminationException ate) {
         return false;
      }

   }

   protected int getIdx()
   throws AbnormalTerminationException {
      if (isRegisterRegister()) {
         return getRegister(getArgument(1));
      }

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

      if (isRegisterRegister()) {
         return 0;
      }

      String address = stripIndexation(getArgument(1));

      int result = evaluateSmall(address, getVertaler());

      if (getAddressingMode() == 1) {
         if (result > (MEMORY_SIZE / 2 - 1)) {
            throw new AbnormalTerminationException("operandwaarde te groot: " + result );
         }

      } else {
         if (result < 0 && getCrementMode() == NOT_INDEXED) {
            throw new AbnormalTerminationException("operandwaarde mag niet negatief: " + result);
         }

      }

      if (result < 0) {
         result = result + MEMORY_SIZE;
      }

      return result;

   }

}
