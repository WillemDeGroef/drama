/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/ConditionState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

/**
 * Class for all different states of a condition code.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class ConditionState {

   public static final ConditionState POSITIVE = new ConditionState();
   public static final ConditionState NEGATIVE = new ConditionState();
   public static final ConditionState ZERO = new ConditionState();

   private ConditionState() {}

   /**
    * @return the condition state that matches the sign of the given value
    */
   public static ConditionState getConditionState(long value) {
      if (value == 0L) {
         return ZERO;
      } else if (value > 0L) {
         return POSITIVE;
      } else {
         return NEGATIVE;
      }

   }
}
