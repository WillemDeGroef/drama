/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MCREINDECommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MCREINDE command.
 * 
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */

public class MCREINDECommand

   extends MacroCommand {

   public MCREINDECommand() {
      super();
   }

   public boolean isLastLine() {
      return true;
   }

   protected void expandImpl(MacroExpander expander) {
      expander.finish();
   }

   protected int getArgumentNumber() {
      return 0;
   }

   public String getName() {
      return "MCREINDE";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      throw new AbnormalTerminationException("Geen argumenten verwacht.");
   }

}
