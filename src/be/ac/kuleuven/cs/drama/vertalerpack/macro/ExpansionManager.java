/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/ExpansionManager.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.io.PrintWriter;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * The ExpansionManager collects and distributes all
 * global information for macro expansion.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class ExpansionManager {

   private static final boolean DEBUG = false;
   private final VariableTable _globalVariableTable;
   private final Map _macroDefinitions;
   private MacroDefinition _main = null;
   private int _uniqueExpansionNumber;
   /* specify type, because Serialized is only present by the subtype */
   private ArrayList _linemap = new ArrayList();
   private long _destline = 0;

   /**
    * Initialize.
    */
   public ExpansionManager() {
      if (DEBUG) System.out.println("ExpansionManager.<init>");

      _globalVariableTable = new VariableTable();
      _macroDefinitions = new HashMap();
      _uniqueExpansionNumber = 0;
   }

   public ArrayList getLineMap() {
      return _linemap;
   }

   public void addLineMapping(int destline, int srcline) {
      if (destline < _destline) {
         throw new RuntimeException("destline shrinks... bad... verry bad");
      }

      if (destline > _destline) {
         _linemap.add(new Integer(srcline));
         _destline++;
      }

      //System.out.println("-- " + destline + " src:" + srcline);
   }

   /**
    * @return the global variable table
    */
   public VariableTable getGlobalVariableTable() {
      return _globalVariableTable;
   }

   /**
    * Add a new macro definition.
    * @param definition the new macro definition
    * @exception AbnormalTerminationException the macro is already defined
    */
   public void addMacroDefinition(MacroDefinition definition)
   throws AbnormalTerminationException {
      if (containsMacroDefinition(definition.getName())) {
         throw new AbnormalTerminationException("Dubbele definitie van macro: "
                                                + definition.getName());
      }

      _macroDefinitions.put(definition.getName(), definition);
   }

   public void addMainMacro(MacroDefinition main) {
      _main = main;
   }

   /**
    * @return wether the given macro name is already defined
    */
   public boolean containsMacroDefinition(String macroName) {
      return _macroDefinitions.containsKey(macroName);
   }

   /**
    * @return the macro definition with given name
    */
   public MacroDefinition getMacroDefinition(String name) {
      return (MacroDefinition) _macroDefinitions.get(name);
   }

   /**
    * @return the main macro (the plain drama code thus)
    */
   public MacroDefinition getMain() {
      return _main;
   }

   /**
    * @return a unique number, used for unique labels
    */
   public int getUniqueExpansionNumber() {
      return _uniqueExpansionNumber++;
   }

   /**
    * Expand the main macro to the given printwriter.
    * @param out the PrintWriter to write to
    */
   public void expand(PrintWriter out)
   throws AbnormalTerminationException {
      if (DEBUG) System.out.println("ExpansionManager.expand()");

      if (getMain() == null) {
         throw new AbnormalTerminationException("Geen programma gedefinieerd.");
      }

      //      System.out.println("lines: " + getMain().nbOfSourceLines());
      //      Iterator it = _macroDefinitions.keySet().iterator();
      //      while(it.hasNext()) {
      //         String macroName = (String) it.next();
      //         System.out.println("Macro: " + macroName + "(" +
      //                            getMacroDefinition(macroName).getNumberOfLines() + ", " +
      //                            getMacroDefinition(macroName).nbOfSourceLines()+
      //                            ")");
      //      }
      getMain().expand(out, new ArrayList(), 1);
   }

}
