/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ResgrCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.StringTokenizer;

/**
 * This class handles the RESGR compiler directive
 *
 * @version 1.0.0, 08/04/2000
 * @autor Tom Schrijvers
 */

public class ResgrCodePart extends LetterCodePart {

   public ResgrCodePart() {}









   public ResgrCodePart(String line, Vertaler2 vertaler) {
      setArgLine(line.substring(5));
      setVertaler(vertaler);
   }

   protected LetterCodePart sibling() {
      return new ResgrCodePart();
   }

   public String keyword() {
      return "RESGR";
   }

   public int nbObjectLines()
   throws AbnormalTerminationException {
      int result = 0;

      try {
         result = evaluate(getArgLine());
      } catch (NumberFormatException nfe) {
         throw new AbnormalTerminationException("ongeldige RESGR expressie: " + nfe.getMessage());
      }

      if (result < 0) {
         throw new AbnormalTerminationException("aantal gereserveerde locaties moet positief zijn: " + result);
      }

      return result;
   }

   public String[] getObjectLines(int objectln)
   throws AbnormalTerminationException {
      String line = "#locatie " + address(objectln + nbObjectLines());
      return new String[]{line};
   }

   /*
    * @return the evaluation of the given RESGR argument
    */
   private int evaluate(String operand)
   throws AbnormalTerminationException {
      int result = 0;

      operand = StringUtils.trimSpaces(operand);
      StringTokenizer tokenizer = new StringTokenizer(operand, "+*-");

      String operator = "+";

      while (tokenizer.hasMoreTokens()) {

         String token = tokenizer.nextToken();
         operand = operand.substring(token.length());
         operand = StringUtils.trimLeftSpaces(operand);
         token = StringUtils.trimSpaces(token);

         int value = Integer.parseInt(token);

         if (operator.equals("+")) {
            result += value;
         } else if (operator.equals("-")) {
            result -= value;
         } else if (operator.equals("*")) {
            result *= value;
         } else {
            throw new AbnormalTerminationException("operator +*- verwacht: " + operator);
         }

         if (operand.length() > 0) {
            operator = operand.substring(0, 1);
            operand = operand.substring(1);
         }

      }

      return result;
   }

}
