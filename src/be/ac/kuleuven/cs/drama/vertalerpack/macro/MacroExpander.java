/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroExpander.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.io.PrintWriter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Class that manages all the variable for
 * the expansion of a single macro.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class MacroExpander {

   private static final Map _expanders = new HashMap();

   private final ExpansionManager _manager;

   private VariableTable _localVariableTable;
   private List _lines;
   private PrintWriter _out;
   private int _currentLine;
   private boolean _finished;
   private ConditionState _mcc;
   private int _expansionNumber;
   private int _lineno;

   /**
    * Initialize.
    */
   public MacroExpander(ExpansionManager manager) {
      _manager = manager;
   }

   /**
    * Expand the given list of macro lines into the given
    * printwriter with the given local variable table.
    */
   public long expand(VariableTable localVariableTable, List lines, PrintWriter out, int n)
   throws AbnormalTerminationException {
      init(localVariableTable, lines, out);
      setLineNo(n);

      while (! _finished) {
         MacroLine line = (MacroLine) _lines.get(_currentLine++);

         try {
            line.expand(this);
         } catch (AbnormalTerminationException e) {
            if (!e.hasLineNo()) {
               throw new AbnormalTerminationException(line.getLineNo(), e.getMessage());
            } else {
               throw e;
            }

         }

      }

      return n;
   }

   private static char lineseparator_newline;
   private static int lineseparator_length;
   static {
      lineseparator_length = System.getProperty("line.separator").length();
      lineseparator_newline = System.getProperty("line.separator").
                              charAt(lineseparator_length - 1);
   }
   protected long countNewlines(String s) {
      int i = s.indexOf(lineseparator_newline);
      long n = 0;

      while (i != -1) {
         n++;
         i = s.indexOf(lineseparator_newline, i + lineseparator_length);
      }

      return n;
   }

   /**
    * Print the given string s to the printbuffer and put a newline at the end.
    *
    * @param srcline The corresponding sourceline from where this line is
    * expanded.
    * @param s A string with no newline characters in it (this is because we
    * want to keep track of the lineno we're on)
    */
   public void println(int srcline, String s) {
      //System.out.println(getLineNo() + " - src:" + srcline);
      //_lineno += countNewlines(s);
      _manager.addLineMapping(_lineno, srcline);
      _lineno++;
      out().println(s);
   }

   /**
    * Print the given string s to the printbuffer.
    *
    * @param srcline The corresponding sourceline from where this line is
    * expanded.
    * @param s A string with no newline characters in it (this is because we
    * want to keep track of the lineno we're on)
    */
   public void print(int srcline, String s) {
      //System.out.println(" " + getLineNo() + " - src:" + srcline);
      //_lineno += countNewlines(s);
      _manager.addLineMapping(_lineno, srcline);
      out().print(s);
   }

   public int getLineNo() {
      return _lineno;
   }

   public void setLineNo(int lineno) {
      _lineno = lineno;
   }

   /*
    * Init all datamembers
    */
   private void init(VariableTable lvt, List lines, PrintWriter out) {
      _localVariableTable = lvt;
      _lines = lines;
      _out = out;
      _currentLine = 0;
      _finished = false;
      _mcc = ConditionState.ZERO;
      _expansionNumber = _manager.getUniqueExpansionNumber();
   }

   /**
    * @return the local variable table
    */
   public VariableTable getLocalVariableTable() {
      return _localVariableTable;
   }

   /**
    * @return the printwriter
    */
   public PrintWriter out() {
      return _out;
   }

   /**
    * Notify this MacroExpander that the expansion of the macro
    * has ended.
    */
   public void finish() {
      _finished = true;
   }

   /**
    * jump to the line with given macro label
    */
   public void setCurrentLine(MacroLabel label)
   throws AbnormalTerminationException {
      _currentLine = label.getIndex();
   }

   /**
    * set the macro condition code with the given value
    */
   public void setMCC(long value) {
      _mcc = ConditionState.getConditionState(value);
   }

   /**
    * @return wether the macro condition code
    *  meets the given conditon
    */
   public boolean _assert(Condition cond) {
      return cond.accept(_mcc);
   }

   /**
    * @return the number of this expansion
    * usable for unique label numbers
    */
   public int getExpansionNumber() {
      return _expansionNumber;
   }

}
