/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroDefiner.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Manages the definition of all macros, including the special 
 * main macro that is the main program.
 *
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */

public class MacroDefiner {

   private final ExpansionManager _expansionManager = new ExpansionManager();
   private final BufferedReader _in;

   /**
    * @param in the reader containing the program with macros
    */
   public MacroDefiner(BufferedReader in) {
      _in = in;
   }

   /**
    * build the macro definitions and return the expansion manager for the 
    * expansion phase
    */
   public ExpansionManager process()
   throws AbnormalTerminationException, IOException {
      String line = null;
      MacroDefinition macro = null;
      int lineno = 0;
      MacroDefinition main = new MacroDefinition(0, _expansionManager);

      try {
         while ((line = _in.readLine()) != null) {
            lineno++;
            //System.out.println(lineno + ": " + line);
            if (macro != null) {
               // bezig met verwerken van een macro
               macro.addLine(lineno, line);

               if (!macro.wantNextLine()) macro = null;
            } else {
               // zoeken naar macro's of gewone code
               String content = Comment.stripComment(line).trim();

               if (content.toUpperCase().equals("MACRO")) {
                  // begin van macro gevonden
                  line = _in.readLine();

                  if (line == null) {
                     throw new AbnormalTerminationException("Onverwacht einde van inputfile");
                  }

                  lineno++;
                  macro = new MacroDefinition(lineno - 1, _expansionManager, line);
                  _expansionManager.addMacroDefinition(macro);
               } else if (content.toUpperCase().startsWith("MACRO")) {
                  throw new AbnormalTerminationException("Onverwachte tekens na MACRO commando");
               } else {
                  main.addLine(lineno, line);
               }

            }

         }
      } catch (AbnormalTerminationException e) {
         throw new AbnormalTerminationException(lineno, e.getMessage());
      }

      if ((macro != null) && macro.wantNextLine())
         throw new AbnormalTerminationException(macro.getLineNo(), "Macro " +
                                                "wordt niet afgesloten");
      main.addLine(lineno + 1, "MCREINDE");
      _expansionManager.addMainMacro(main);
      return _expansionManager;
   }

}
