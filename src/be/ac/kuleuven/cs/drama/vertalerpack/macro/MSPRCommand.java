/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MSPRCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MSPR command.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class MSPRCommand

   extends MacroCommand {

   public MSPRCommand() {
      super();
   }

   protected void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException {
      expander.setCurrentLine((MacroLabel) getArgument(0));
   }



   protected int getArgumentNumber() {
      return 1;
   }

   public String getName() {
      return "MSPR";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      if (index == 0) {
         return context.getLabel(value);
      } else {
         throw new AbnormalTerminationException("Onverwacht argument.");
      }

   }

}
