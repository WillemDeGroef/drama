/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MFOUTCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

/**
 * The MFOUT command.
 * 
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */

public class MFOUTCommand

   extends MacroCommand {

   public MFOUTCommand() {
      super();
   }


   protected void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException {
      throw new AbnormalTerminationException("MFOUT: " + getArgument(0));
   }

   protected int getArgumentNumber() {
      return 1;
   }

   protected void splitArguments(String argLine) {
      pushArgument(StringUtils.trimSpaces(argLine));
   }

   public String getName() {
      return "MFOUT";
   }

   protected Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException {
      if (index == 0) {
         return parseString(value);
      } else
         throw new AbnormalTerminationException("Geen argumenten verwacht.");

   }

   private String parseString(String string)
   throws AbnormalTerminationException {
      if (! string.startsWith("\"") || ! string.endsWith("\"")) {
         throw new AbnormalTerminationException("Geen geldige foutboodschap: " + string);
      }

      return string;
   }

}
