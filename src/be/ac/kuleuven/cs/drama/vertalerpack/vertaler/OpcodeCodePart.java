/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/OpcodeCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Representation of an opcode line
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public abstract class OpcodeCodePart extends LetterCodePart {

   // representation of the different mode type
   protected static final int MODE_DEFAULT = 0;
   protected static final int MODE_W = 1;
   protected static final int MODE_A = 2;
   protected static final int MODE_D = 3;
   protected static final int MODE_I = 4;

   // indexation types
   protected static final int NOT_INDEXED = 1;
   protected static final int INDEXED = 2;
   protected static final int PRE_INCREMENT = 3;
   protected static final int POST_INCREMENT = 4;
   protected static final int PRE_DECREMENT = 5;
   protected static final int POST_DECREMENT = 6;

   // the mode type
   private int _modetype = MODE_DEFAULT;
   // the arguments
   private List _args = new ArrayList();

   protected OpcodeCodePart() {}

   /*
    * parse the mode type and arguments
    */
   private void parseLine()
   throws AbnormalTerminationException {
      detectArguments(detectModeType());
   }

   /*
    * parse the mode type
    */
   private String detectModeType()
   throws AbnormalTerminationException {
      String line = getArgLine();

      if (line.length() == 0 || StringUtils.isSpace(line.charAt(0))) {
         _modetype = MODE_DEFAULT;
         return getArgLine();
      }

      if (line.length() == 1) {
         throw new AbnormalTerminationException("Onverwacht karakter: " + line);
      }

      if (line.length() < 3 || StringUtils.isSpace(line.charAt(2))) {
         switch (line.charAt(1)) {

         case 'A':
            _modetype = MODE_A;
            break;

         case 'W':
            _modetype = MODE_W;
            break;

         case 'D':
            _modetype = MODE_D;
            break;

         case 'I':
            _modetype = MODE_I;
            break;

         default:
            throw new AbnormalTerminationException("Onverwachte mode " + line.charAt(1));
         }

         return line.substring(2);
      }

      throw new AbnormalTerminationException("Onverwachte mode " + getArgLine());
   }

   /*
    * split up the arguments
    */
   private void detectArguments(String args)
   throws AbnormalTerminationException {

      StringTokenizer st = new StringTokenizer(args, ",");

      while (st.hasMoreTokens()) {
         String arg = st.nextToken().trim();
         //System.out.println("*" + arg + "*");
         _args.add(arg);
      }

      if ((_args.size() == 1) && (((String) _args.get(0)).trim().length() == 0 ) ) {
         _args.clear();
      }

      if (! acceptNbArguments(_args.size())) {
         throw new AbnormalTerminationException("Ongeldig aantal argumenten (" + _args.size() + ")");
      }

   }

   /**
    * @return the ith argument
    */
   protected String getArgument(int index) {
      return (String) _args.get(index);
   }

   /**
    * push a new argument
    */
   protected void addArgument(String arg) {
      _args.add(arg);
   }

   /**
    * @return the numner of arguments
    */
   protected int nbArguments() {
      return _args.size();
   }

   /**
    * add a first argument that is implicit
    */
   protected void addFirstArgument(String arg) {
      _args.add(0, arg);
   }


   /**
    * @return wether the given number of arguments is accpetable
    */
   protected abstract boolean acceptNbArguments(int nb);

   /**
    * @return the 2 opcode digits
    */
   protected abstract int getOpcode() throws AbnormalTerminationException;

   /**
    * @return the 2 mode digits
    */
   protected abstract int getMode() throws AbnormalTerminationException;

   /**
    * @return the 1 accumulator digit
    */
   protected abstract int getAcc() throws AbnormalTerminationException;

   /**
    * @return the 1 indexation digit
    */
   protected abstract int getIdx() throws AbnormalTerminationException;

   /**
    * @return the 4 operand digits
    */
   protected abstract int getOperand() throws AbnormalTerminationException;

   public final int nbObjectLines() {
      return 1;
   }

   public final String[] getObjectLines(int objectln)
   throws AbnormalTerminationException {

      long result = 0L;

      parseLine();

      result = getOpcode();
      result = result * 100L + getMode();
      result = result * 10L + getAcc();
      result = result * 10L + getIdx();
      result = result * 10000L + getOperand();

      getVertaler().statistics().addOccurence(getOpcode());

      return new String[] {Long.toString(result)};
   }

   /**
    * @return the detected mode type
    */
   protected int getModeType() {
      return _modetype;
   }

   /**
    * @return the register number of the given register
    */
   protected int getRegister(String str)
   throws AbnormalTerminationException {
      String reg = str.toUpperCase().trim();

      if (! isRegister(reg)) {
         throw new AbnormalTerminationException("ongeldig register: " + str);
      }

      return ((Integer) _registers.get(reg)).intValue();
   }

   /**
    * @param str The complete operand string
    * @return the part of the operand string that is the indexation
    */
   protected String getIndexationString(String str)
   throws AbnormalTerminationException {

      //System.out.println(":" + str + ":");
      if (!str.endsWith(")")) {
         return "";
      }

      char[] chars = str.toCharArray();
      int idx = chars.length - 2;

      while (idx >= 0 && chars[idx] != '(') {
         // debug:System.out.println("char: " + chars[idx]);
         idx--;
      }

      if ((idx < 0) || (chars[idx] != '(' )) {
         throw new AbnormalTerminationException("Fout in indexatie: " + str);
      }

      String result = str.substring(idx + 1, str.length() - 1);
      result = StringUtils.trimSpaces(result).toUpperCase();

      if ((result.length() < 2 ) || (result.length() > 3)) {
         throw new AbnormalTerminationException("Ongeldige indexatie: " + result);
      }

      return result;
   }

   /**
    * @return the indexation type
    */
   protected int getIndexation(String str)
   throws AbnormalTerminationException {

      if (str.length() == 0) {
         return NOT_INDEXED;
      }

      if (str.length() == 2) {
         return INDEXED;
      }

      char[] chars = str.toCharArray();

      if (chars[0] == '+') {
         return PRE_INCREMENT;
      }

      if (chars[0] == '-') {
         return PRE_DECREMENT;
      }

      if (chars[2] == '+') {
         return POST_INCREMENT;
      }

      if (chars[2] == '-') {
         return POST_DECREMENT;
      }

      throw new AbnormalTerminationException("ongeldige indexatie: " + str);
   }

   /**
    * @return the operand string without indexation
    */
   protected String stripIndexation(String str)
   throws AbnormalTerminationException {

      if (getIndexationString(str).length() == 0) {
         return str;
      }

      int idx = str.lastIndexOf(getIndexationString(str));

      if (idx == -1) {
         throw new RuntimeException("OpcodePart.stripIndexation() unexpected error");
      }

      return str.substring(0, idx - 1);
   }

   /**
    * @return wether the given string is a register
    */
   public boolean isRegister(String str) {
      return _registers.containsKey(str);
   }


   // map of register names and values
   private static final Map _registers = new HashMap();

   // build the map
   static {
      _registers.put("R0", new Integer(0));
      _registers.put("R1", new Integer(1));
      _registers.put("R2", new Integer(2));
      _registers.put("R3", new Integer(3));
      _registers.put("R4", new Integer(4));
      _registers.put("R5", new Integer(5));
      _registers.put("R6", new Integer(6));
      _registers.put("R7", new Integer(7));
      _registers.put("R8", new Integer(8));
      _registers.put("R9", new Integer(9));
      _registers.put("SR", new Integer(9));
   }

}
