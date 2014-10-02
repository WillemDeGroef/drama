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

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.Map;
import java.util.HashMap;

import java.util.StringTokenizer;

/**
 * Factory that transforms Strings into macro line objects.
 * 
 * @version 1.0.0 08/31/2000
 * @author  Tom Schrijvers
 */

public class MacroLineFactory {

   private static final boolean DEBUG = false;

   public MacroLineFactory() {}

   // general











   public MacroLine parseLine(String line, int lineno, MacroDefinition context)
   throws AbnormalTerminationException {

      MacroLabel label = null;
      MacroLine result = null;
      String origLine = line;

      //System.out.print("parsing '" + line + "' into ");

      int i = line.indexOf(':');

      if ((i != -1) && ((line.indexOf('|') == -1) || (i < line.indexOf('|')))) {
         // we've got a label
         if (i == 0) {
            throw new AbnormalTerminationException("Label zonder naam: " + line);
         }

         String labelName = (new StringTokenizer(line, ": \t")).nextToken();
         //System.out.println("LABEL:"+labelName);
         if (labelName.charAt(0) == MacroLabel.LABEL_START) {
            if (labelName.length() < 2) {
               throw new AbnormalTerminationException("Label zonder naam: " + line);
            }

            label = context.getLabel(labelName);
         } else {
            label = new NormalLabel(labelName);
         }

         label.setIndex(context.getNumberOfLines());
         line = line.substring(i + 1);
      }

      StringTokenizer st = new StringTokenizer(line);

      if (st.hasMoreTokens()) {
         String token = st.nextToken();

         if (isMacroCommand(token)) {
            result = getCommand(token);
            String args = "";

            if (st.hasMoreTokens()) {
               args = st.nextToken("");
               //System.out.println("args: " + args);
            }










            result.initCaller(args, context, lineno);
         } else if (context.isMacroName(token)) {
            // dealing with a macro call ?
            MacroDefinition def = context.getMacro(token);
            result = new MacroCall(def);
            String args = "";

            if (st.hasMoreTokens()) {
               args = st.nextToken("");
               //System.out.println("args: " + args);
            }










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
      //System.out.println(result);
      return result;
   }

   //      line = StringUtils.trimSpaces(line);
   //      MacroLabel label = null;
   //      MacroLine result = null;
   //
   //      // look for MacroLabel
   //      String isLabel = getFirstWord(line);
   //
   //      if (isLabel.length() < line.length() && line.charAt(isLabel.length()) == ':') {
   //         if (isLabel.charAt(0) == MacroLabel.LABEL_START) {
   //            label = context.getLabel(isLabel);
   //         } else {
   //            label = new NormalLabel(isLabel);
   //         }
   //
   //         label.setIndex(context.getNumberOfLines());
   //         line = StringUtils.trimLeftSpaces(line.substring(isLabel.length() + 1));
   //      }
   //
   //      // are we dealing with a macro command ?
   //      String firstWord = getFirstWord(line);
   //
   //      if (isMacroCommand(firstWord)) {
   //         MacroCommand command = getCommand(firstWord);
   //
   //         if (DEBUG) System.out.println("MacroLineFactory.parseLine() - macro command argument line: "
   //                                          + line.substring(firstWord.length()));
   //         command.init(line.substring(firstWord.length()), context);
   //         result = command;
   //      } else if (context.isMacroName(firstWord)) {
   //         // dealing with a macro call ?
   //         MacroDefinition def = context.getMacro(firstWord);
   //         result = new MacroCall(def);
   //         result.init(line.substring(firstWord.length()), context);
   //      } else {
   //         // we are dealing with an ordinary line
   //         result = new OrdinaryLine();
   //         result.init(line, context);
   //      }
   //
   //      // do general stuff and return
   //      result.setLabel(label);
   //      return result;
   //   }
   //
   //   // String stuff
   //   private String getFirstWord(String line) {
   //      int index = 0;
   //
   //      while (index < line.length() &&
   //             ! StringUtils.isSpace(line.charAt(index)) &&
   //             line.charAt(index) != ':'
   //            ) {
   //         index += 1;
   //      }
   //
   //      return line.substring(0, index);
   //   }

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
      return _commands.containsKey(word);
   }

   public MacroCommand getCommand(String word) {
      return ((MacroCommand) _commands.get(word)).sibling();
   }

}
