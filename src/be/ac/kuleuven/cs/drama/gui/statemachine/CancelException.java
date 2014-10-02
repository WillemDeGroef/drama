/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/CancelException.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * Exception to indicate that a method call
 * on the state machine was unsuccesful.
 *
 * @version 1.0.0 09/06/2000
 * @author  Tom Schrijvers
 */

class CancelException

   extends Exception {

   public CancelException() {
      super();
   }

   public CancelException(String message) {
      super(message);
   }

}
