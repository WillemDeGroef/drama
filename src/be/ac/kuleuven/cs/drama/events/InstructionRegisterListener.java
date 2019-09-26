/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/InstructionRegisterListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**
 * Interface for classes that want to be notified of
 * instruction register updates.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public interface InstructionRegisterListener {

    void handleInstructionRegisterEvent();

}
