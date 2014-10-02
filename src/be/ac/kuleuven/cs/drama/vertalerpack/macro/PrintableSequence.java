/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/PrintableSequence.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Sequence of printable entities.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class PrintableSequence

   implements Printable {
   private final List _sequence = new ArrayList();

   public void add(Printable printable) {
      _sequence.add(printable);
   }

   public String toString(MacroExpander expander)
   throws AbnormalTerminationException {
      StringBuffer buffer = new StringBuffer();
      Iterator it = _sequence.iterator();

      while (it.hasNext()) {
         buffer.append( ((Printable) it.next()).toString(expander));
      }

      return buffer.toString();
   }

   public void getMacroLabels(Set labels) {
      Iterator it = _sequence.iterator();

      while (it.hasNext()) {
         ((Printable) it.next()).getMacroLabels(labels);
      }

   }

}
