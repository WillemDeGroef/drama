/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroLineFactory.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * Factory that transforms Strings into macro line objects.
 * 
 * @version 1.0.0 08/31/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

public class MacroLineFactory {

   public MacroLineFactory() {}

   // general

   public MacroLine parseLine(String line, int lineno, MacroDefinition context)
   throws AbnormalTerminationException {

      MacroLabel label = null;
      MacroLine result = null;

      int i = line.indexOf(':');

      if (i != -1 && (line.indexOf('|') == -1 || i < line.indexOf('|')) && (line.indexOf('"') == -1 || i < line.indexOf('"'))) {
         // we've got a label
         StringTokenizer tokenizer = new StringTokenizer(line, ": \t");
         if (!tokenizer.hasMoreTokens())
             throw new AbnormalTerminationException("Label zonder naam: " + line);
         
         String labelName = tokenizer.nextToken();
         if (labelName.charAt(0) == MacroLabel.LABEL_START) {
            if (labelName.length() < 2)
               throw new AbnormalTerminationException("Label zonder naam: " + line);

            label = context.getLabel(labelName);
         } else
            label = new NormalLabel(labelName);

         label.setIndex(context.getNumberOfLines());
         line = line.substring(i + 1);
      }

      StringTokenizer st = new StringTokenizer(line);

      if (st.hasMoreTokens()) {
         String token = st.nextToken();

         if (isMacroCommand(token)) {
            result = getCommand(token);
            String args = "";

            if (st.hasMoreTokens())
               args = st.nextToken("");

            result.initCaller(args, context, lineno);
         } else if (context.isMacroName(token)) {
            // dealing with a macro call ?
            MacroDefinition def = context.getMacro(token);
            result = new MacroCall(def);
            String args = "";

            if (st.hasMoreTokens())
               args = st.nextToken("");

            result.initCaller(args, context, lineno);
         } else {
            result = new OrdinaryLine();
            result.initCaller(line, context, lineno);
         }
      } else {
         result = new OrdinaryLine();
         result.initCaller(line, context, lineno);
      }

      result.setLabel(label);
      return result;
   }

   // commands

   private static final Map _commands;

   static {
      _commands = new HashMap();
      addCommand(new MEVACommand());
      addCommand(new MNTSCommand());
      addCommand(new MSPRCommand());
      addCommand(new MVSPCommand());
      addCommand(new MVGLCommand());
      addCommand(new MFOUTCommand());
      addCommand(new MCREINDECommand());
   }

   private static void addCommand(MacroCommand mc) {
      _commands.put(mc.getName(), mc);
   }

   public boolean isMacroCommand(String word) {
      return _commands.containsKey(word.toUpperCase());
   }

   public MacroCommand getCommand(String word) {
      return ((MacroCommand) _commands.get(word.toUpperCase())).sibling();
   }

}
