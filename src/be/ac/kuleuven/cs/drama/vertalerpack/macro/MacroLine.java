/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroLine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Superclass for all classes that represent 
 * lines in a macro.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public abstract class MacroLine {

   private MacroLabel _label = null;
   private String _line;
   private int _lineno;

   protected MacroLine() {}








   public void initCaller(String line, MacroDefinition context, int lineno)
   throws AbnormalTerminationException {
      setLineNo(lineno);
      init(line, context);
   }

   public abstract void init(String line, MacroDefinition context)
   throws AbnormalTerminationException;

   public String getLine() {
      return _line;
   }

   protected void setLineNo(int lineno) {
      _lineno = lineno;
   }

   public int getLineNo() {
      return _lineno;
   }

   public boolean isMacroCommand() {
      return true;
   }

   public final int expand(MacroExpander expander)
   throws AbnormalTerminationException {
      expandImpl(expander);
      String text = outString(expander);
      boolean outputText = !isMacroCommand() || (text.length() > 0);

      if (_label != null && _label.shouldBePrinted()) {
         if (labelOnSeparateLine() || !outputText) {
            expander.println(getLineNo(), _label.toString(expander) + ":");
         } else {
            expander.print(getLineNo(), _label.toString(expander) + ":");
         }

      }

      if (outputText) {
         expander.println(getLineNo(), text);
      }

      return 0;
   }

   public void setLabel(MacroLabel label) {
      _label = label;
   }

   /**
    * Implementation of the actions necessary before writing
    * out the line.
    */
   protected abstract void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException;

   /**
    * @return the String of this line that has to be written out 
    */
   protected abstract String outString(MacroExpander expander)
   throws AbnormalTerminationException;

   /**
    * @return wether this is the last line of a macro (default is false)
    */
   public boolean isLastLine() {
      return false;
   }

   /**
    * parse the given string to a Printable
    */
   public static final Printable getExpression(String expression, MacroDefinition context)
   throws AbnormalTerminationException {
      PrintableSequence sequence = new PrintableSequence();
      int index = 0;
      StringBuffer buffer = new StringBuffer();

      while (index < expression.length()) {
         if (expression.charAt(index) == MacroVariable.VARIABLE_OPEN) {
            if (buffer.length() > 0) {
               sequence.add(new PrintableStringWrapper(buffer.toString()));
               buffer.delete(0, buffer.length());
            }

            if (expression.indexOf(MacroVariable.VARIABLE_CLOSE, index + 1) == -1) {
               throw new AbnormalTerminationException("Ontbrekende " +
                                                      MacroVariable.VARIABLE_CLOSE +
                                                      " in macrovariabele.");
            } else {
               sequence.add(getMacroVariable(expression.substring(index,
                                             expression.indexOf(MacroVariable.VARIABLE_CLOSE,
                                                                index + 1) + 1)));
               index = expression.indexOf(MacroVariable.VARIABLE_CLOSE, index + 1) + 1;
            }
         } else if (expression.charAt(index) == MacroLabel.LABEL_START) {
            if (buffer.length() > 0) {
               sequence.add(new PrintableStringWrapper(buffer.toString()));
               buffer.delete(0, buffer.length());
            }

            int labelStart = index;

            while (index < expression.length() && ! StringUtils.isSpace(expression.charAt((index)))) {
               index++;
            }

            sequence.add(context.getLabel(expression.substring(labelStart, index)));
         } else {
            buffer.append(expression.charAt(index));
            index += 1;
         }

      }

      if (buffer.length() > 0) {
         sequence.add(new PrintableStringWrapper(buffer.toString()));
      }

      return sequence;
   }

   /**
    * @parse the given string to a macro variable
    */
   protected static final MacroVariable getMacroVariable(String string)
   throws AbnormalTerminationException {
      return MacroVariable.getMacroVariable(string);
   }

   protected void setAllLabelsPrintable(Printable p) {
      Set labels = new HashSet();
      p.getMacroLabels(labels);
      Iterator it = labels.iterator();

      while (it.hasNext()) {
         ((MacroLabel) it.next()).setShouldBePrinted();
      }

   }

   protected boolean labelOnSeparateLine() {
      return true;
   }

}
