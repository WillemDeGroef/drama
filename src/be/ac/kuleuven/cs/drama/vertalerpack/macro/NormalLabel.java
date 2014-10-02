/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/NormalLabel.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * Class representing a normal label,
 * for use before macro labels only.
 *
 * @version 1.0.0 08/31/2000
 * @author  Tom Schrijvers
 */


public class NormalLabel

   extends MacroLabel {

   /**
    * Init with given name.
    */
   public NormalLabel(String name) {
      super(name);
   }

   public String toString(MacroExpander expander)
   throws AbnormalTerminationException {
      return MacroLine.getExpression(getName(), null).toString(expander);
   }

   public boolean shouldBePrinted() {
      return true;
   }

}
