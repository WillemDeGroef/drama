/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
import java.util.StringTokenizer;


/**
 * Superclass for all macro commands.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public abstract class MacroCommand extends MacroLine {

   private final List _arguments;

   public MacroCommand sibling() {
      try {
         return (MacroCommand) getClass().newInstance();
      } catch (Throwable t) {
         t.printStackTrace();
         return null;
      }

   }

   /**
    * MacroCommands don't write strings to the output.
    */
   protected final String outString(MacroExpander expander) {
      return "";
   }

   protected MacroCommand() {
      _arguments = new ArrayList();
   }

   public final void init(String argumentLine, MacroDefinition context)
   throws AbnormalTerminationException {
      splitArguments(Comment.stripComment(argumentLine));
      checkArguments(context);
   }



   /**
    * Parses the argument line.
    * Override in case arguments are not separated by commas.
    */
   protected void splitArguments(String argLine) {
      StringTokenizer tokenizer = new StringTokenizer(argLine, ",");

      while (tokenizer.hasMoreTokens()) {
         pushArgument(StringUtils.trimSpaces(tokenizer.nextToken()));
      }

      if (_arguments.size() == 1 && _arguments.get(0).equals("")) {
         _arguments.clear();
      }

   }

   /**
    * Push an argument in String form on the argument list
    */
   protected final void pushArgument(String arg) {
      _arguments.add(arg);
   }

   /**
    * checks the correct number of arguments and parses from Strig to the desired object type
    */
   private void checkArguments(MacroDefinition context)
   throws AbnormalTerminationException {
      if (_arguments.size() != getArgumentNumber()) {
         throw new AbnormalTerminationException("Ongeldig aantal argumenten voor macrocommando");
      }

      for (int i = 0; i < _arguments.size(); i++) {
         _arguments.set(i, parseArgument(i, (String) _arguments.get(i), context));
      }

   }

   /**
    * parse the given argument in String format to the desired type
    */
   protected abstract Object parseArgument(int index, String value, MacroDefinition context)
   throws AbnormalTerminationException;

   /**
    * @return the argument at given index
    */
   protected Object getArgument(int index) {
      return _arguments.get(index);
   }

   /**
    * @return the expected number of arguments
    */
   protected abstract int getArgumentNumber();

   /**
    * @return the name of this MacroCommand
    */
   public abstract String getName();

   protected long evaluate(String expression)
   throws AbnormalTerminationException {
      return Evaluator.evaluate(expression);
   }

}
