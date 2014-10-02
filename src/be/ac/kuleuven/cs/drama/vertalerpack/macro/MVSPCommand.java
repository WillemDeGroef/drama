/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MVSPCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MVSP command.
 * 
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */

public class MVSPCommand

   extends MacroCommand {

   public MVSPCommand() {
      super();
   }


   protected void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException {
      if (expander._assert((Condition) getArgument(0))) {
         expander.setCurrentLine((MacroLabel) getArgument(1));
      }

   }

   protected int getArgumentNumber() {
      return 2;
   }

   public String getName() {
      return "MVSP";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      switch (index) {

      case 0:
         return Condition.parseCondition(value);

      case 1:
         return context.getLabel(value);

      default:
         throw new AbnormalTerminationException("Geen argumenten verwacht.");
      }

   }

}
