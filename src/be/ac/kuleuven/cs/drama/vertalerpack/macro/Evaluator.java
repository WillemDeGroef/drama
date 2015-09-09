/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/Evaluator.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.StringTokenizer;

/**
 * Utility class to evaluate a String expression
 * to a value.
 *
 * @version 1.0.0 08/30/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

public class Evaluator {

   private static final boolean DEBUG = false;

   private Evaluator() {}


   /**
    * Evaluate the given string to a long.
    * Operators are evaluated from left to right!
    */
   public static long evaluate(String operand)
   throws AbnormalTerminationException {
      long result = 0L;

      operand = StringUtils.trimSpaces(operand);
      StringTokenizer tokenizer = new StringTokenizer(operand, "+*-");

      String operator = "+";

      if (operand.startsWith("-")) {
         operator = "-";
         operand = operand.substring(1);
      }

      while (tokenizer.hasMoreTokens()) {
         String token = tokenizer.nextToken();
         operand = operand.substring(token.length());
         operand = StringUtils.trimLeftSpaces(operand);
         token = StringUtils.trimSpaces(token);

         if (DEBUG) {
            System.out.println("token: " + token);
            System.out.println("operator: " + operator);
         }

         long value;
         try {
        	 value = Long.parseLong(token);
         } catch (NumberFormatException e) {
        	 throw new AbnormalTerminationException("MEVA werkt enkel met getallen, " + token + " is niet herkend als getal.");
         }

         if (operator.equals("+")) {
            result += value;
         } else if (operator.equals("-")) {
            result -= value;
         } else if (operator.equals("*")) {
            result *= value;
         } else {
            throw new AbnormalTerminationException("operator +*- verwacht: " + operator);
         }

         if (operand.length() > 0L) {
            operator = operand.substring(0, 1);
            operand = operand.substring(1);
         }

      }

      return result;
   }

}
