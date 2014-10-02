/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroLabel.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.Set;

/**
 * Class representing a macro label.
 * A macro label has two meanings in the current syntax:
 * it is a label in the macro language used as references by 
 * the macro commands and it is used to generate unique labels for the 
 * expanded source code
 *
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */


public class MacroLabel

   implements Printable {

   public static final char LABEL_START = '$';

   private final String _name;

   private int _index = -1;

   private boolean _shouldBePrinted = false;

   /**
    * Init with given name.
    */
   public MacroLabel(String name) {
      _name = name.toUpperCase();
   }

   /**
    * @return the name
    */
   public String getName() {
      return _name;
   }

   /**
    * set the relative position within the macro
    */
   public void setIndex(int index)
   throws AbnormalTerminationException {
      if (_index != -1) {
         throw new AbnormalTerminationException("Macrolabel dubbel gedefinieerd: " + getName());
      }

      _index = index;
   }

   /**
    * @return the relative position within the macro
    */
   public int getIndex()
   throws AbnormalTerminationException {
      if (_index == -1) {
         throw new AbnormalTerminationException("Macrolabel niet gedefinieerd: " + getName());
      }

      return _index;
   }

   public String toString(MacroExpander expander)
   throws AbnormalTerminationException {
      return "_" + getName() + expander.getExpansionNumber();
   }

   /**
    * @parse the given String to a MacroLabel
    */
   public static MacroLabel getLabel(String label)
   throws AbnormalTerminationException {
      if (! label.startsWith("" + LABEL_START)) {
         throw new AbnormalTerminationException("Geen geldig macrolabel: " + label);
      }

      if (! Name.isName(label.substring(1))) {
         throw new AbnormalTerminationException("Geen geldig macrolabel: " + label);
      }

      return new MacroLabel(label.substring(1));
   }

   public boolean shouldBePrinted() {
      return _shouldBePrinted;
   }

   public void setShouldBePrinted() {
      _shouldBePrinted = true;
   }

   public void getMacroLabels(Set labels) {
      labels.add(this);
   }

}
