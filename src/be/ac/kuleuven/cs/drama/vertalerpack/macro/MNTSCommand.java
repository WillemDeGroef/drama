/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MNTSCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MNTS command.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class MNTSCommand

   extends MacroCommand {

   public MNTSCommand() {
      super();
   }


   protected void expandImpl(MacroExpander expander) {}












   protected int getArgumentNumber() {
      return 0;
   }



   public String getName() {
      return "MNTS";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      throw new AbnormalTerminationException("Geen argumenten verwacht.");
   }

}
