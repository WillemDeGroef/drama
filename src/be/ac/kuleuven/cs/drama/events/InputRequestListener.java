/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/InputRequestListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

/**
 * Interface for classes that want to be notified of
 * input requests.
 *
 * @version 1.0.0 09/19/2000
 * @author  Tom Schrijvers
 */

public interface InputRequestListener {

   public void handleInputRequest();

}
