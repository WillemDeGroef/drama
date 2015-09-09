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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

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

      this._globalVariableTable = new VariableTable();
      this._macroDefinitions = new HashMap();
      this._uniqueExpansionNumber = 0;
   }

   public ArrayList getLineMap() {
      return this._linemap;
   }

   public void addLineMapping(int destline, int srcline) {
      if (destline < this._destline) {
         throw new RuntimeException("destline shrinks... bad... very bad");
      }

      if (destline > this._destline) {
         this._linemap.add(new Integer(srcline));
         this._destline++;
      }
   }

   /**
    * @return the global variable table
    */
   public VariableTable getGlobalVariableTable() {
      return this._globalVariableTable;
   }

   /**
    * Add a new macro definition.
    * @param definition the new macro definition
    * @exception AbnormalTerminationException the macro is already defined
    */
   public void addMacroDefinition(MacroDefinition definition)
   throws AbnormalTerminationException {
      if (containsMacroDefinition(definition.getName())) {
         throw new AbnormalTerminationException("Dubbele definitie van macro: " + definition.getName());
      }

      this._macroDefinitions.put(definition.getName(), definition);
   }

   public void addMainMacro(MacroDefinition main) {
      this._main = main;
   }

   /**
    * @return wether the given macro name is already defined
    */
   public boolean containsMacroDefinition(String macroName) {
      return this._macroDefinitions.containsKey(macroName);
   }

   /**
    * @return the macro definition with given name
    */
   public MacroDefinition getMacroDefinition(String name) {
      return (MacroDefinition) this._macroDefinitions.get(name);
   }

   /**
    * @return the main macro (the plain drama code thus)
    */
   public MacroDefinition getMain() {
      return this._main;
   }

   /**
    * @return a unique number, used for unique labels
    */
   public int getUniqueExpansionNumber() {
      return this._uniqueExpansionNumber++;
   }

   /**
    * Expand the main macro to the given printwriter.
    * @param out the PrintWriter to write to
    */
   public void expand(PrintWriter out)
   throws AbnormalTerminationException {
      if (DEBUG) System.out.println("ExpansionManager.expand()");

      if (this.getMain() == null) {
         throw new AbnormalTerminationException("Geen programma gedefinieerd.");
      }
      
      this.getMain().expand(out, new ArrayList(), 1);
   }

}
