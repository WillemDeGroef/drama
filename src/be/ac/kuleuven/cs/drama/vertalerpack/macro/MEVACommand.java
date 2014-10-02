/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MEVACommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MEVA command.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class MEVACommand

   extends MacroCommand {

   public MEVACommand() {
      super();
   }


   protected void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException {
      long value = evaluate(((Printable) getArgument(1)).toString(expander));
      expander.setMCC(value);
      expander.getLocalVariableTable().put((MacroVariable) getArgument(0), Long.toString(value));
   }

   protected int getArgumentNumber() {
      return 2;
   }



   public String getName() {
      return "MEVA";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      switch (index) {

      case 0:
         return getMacroVariable(value);

      case 1:
         return getExpression(value, context);

      default:
         throw new AbnormalTerminationException("Geen argumenten verwacht.");
      }

   }

}
