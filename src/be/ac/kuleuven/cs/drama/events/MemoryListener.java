/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/MemoryListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**
 * Listener interface for all classes that like to
 * get informed of memory updates.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public interface MemoryListener {

    /**
     * handle the update of the cell with given address and new value
     */
    void handleMemoryEvent(int address, long value);

}
