/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ActiveCodePartFactory.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import java.util.Map;
import java.util.HashMap;

/**
 * The ActiveCodePArtFactory manufactures the right
 * ActiveCodePart for the given drama source line.
 *
 * @version 1.0.0 08/03/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public final class ActiveCodePartFactory {

   // singleton
   private static final ActiveCodePartFactory _instance = new ActiveCodePartFactory();

   // only for singleton
   private ActiveCodePartFactory() {}

   /**
    * @return singleton instance
    */
   public static ActiveCodePartFactory instance() {
      return _instance;
   }

   /**
    * @return an ActiveCodePart for the given line and vertaler.
    */
   public ActiveCodePart getActiveCodePart(String line, Vertaler2 vertaler) {
      String eline = line.trim();

      if (eline.length() == 0) {
         return new NoCodePart();
      }

      char c = eline.charAt(0);

      if (Character.isLetter(c))
         return getLetterCodePart(eline, vertaler);

      if (c == '-' || c == '+' || Character.isDigit(c) || c == '"')
    	  return new LiteralCodePart(eline, vertaler);

      return new DummyCodePart(eline);
   }

   /*
    * @return an ActiveCodePart for a line that starts with a letter.
    */
   private ActiveCodePart getLetterCodePart(String line, Vertaler2 vertaler) {
      String eline = line.toUpperCase();

      if (eline.startsWith("RESGR")) {
         return new ResgrCodePart(eline, vertaler);
      }

      if (eline.startsWith("EINDPR")) {
         return new EindprCodePart(eline, vertaler);
      }

      if (eline.length() >= 3) {
         String opcode = eline.substring(0, 3);

         if (_opcodes.containsKey(opcode)) {
            LetterCodePart lcp = (LetterCodePart) _opcodes.get(opcode);
            return lcp.newInstance(eline.substring(3), vertaler);
         }

      }

      return new LiteralCodePart(line, vertaler);
      //return new DummyCodePart(line);
   }

   // map of OpcodecodeParts
   private static final Map _opcodes = new HashMap();

   /*
    * add an OpcodeCodePart prototype to the map
    */
   private static void addOpcode(OpcodeCodePart oc) {
      _opcodes.put(oc.keyword(), oc);
   }

   // fill the map
   static {
      addOpcode(new Class1Opcode("HIA", 11));
      addOpcode(new Class1Opcode("OPT", 21));
      addOpcode(new Class1Opcode("AFT", 22));
      addOpcode(new Class1Opcode("VER", 23));
      addOpcode(new Class1Opcode("DEL", 24));
      addOpcode(new Class1Opcode("MOD", 25));
      addOpcode(new Class1Opcode("VGL", 31));

      addOpcode(new BIGOpcode());
      addOpcode(new VSPOpcode());
      addOpcode(new HSTOpcode());
      addOpcode(new BSTOpcode());
      addOpcode(new ONDOpcode());

      addOpcode(new Class3Opcode("INV", 81));
      addOpcode(new Class3Opcode("UTV", 82));

      addOpcode(new Class4Opcode("SPR", 32));
      addOpcode(new Class4Opcode("SBR", 41));
      
      addOpcode(new Class4Opcode("HIB", 91));
      addOpcode(new Class4Opcode("SGI", 92));
      addOpcode(new Class4Opcode("SGU", 93));

      addOpcode(new Class5Opcode("MKL", 51));
      addOpcode(new Class5Opcode("MKH", 52));
      addOpcode(new Class5Opcode("TSM", 53));
      addOpcode(new Class5Opcode("TSO", 54));

      addOpcode(new Class6Opcode("KTG", 42));
      addOpcode(new Class6Opcode("KTO", 62));
      addOpcode(new Class6Opcode("LEZ", 71));
      addOpcode(new Class6Opcode("DRU", 72));
      addOpcode(new Class6Opcode("DRS", 79));
      addOpcode(new Class6Opcode("STP", 99));
      addOpcode(new Class6Opcode("NTS", 74));
      addOpcode(new Class6Opcode("NWL", 73));
      
   }

}
