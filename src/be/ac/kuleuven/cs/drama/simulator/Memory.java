/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/Memory.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator;

/**
 * Interface to be implemented by all RAM-like
 * classes that allow an indexed list of cells
 * to be inspected.
 *
 * version 1.0.0 08/09/2000
 * @author Tom Schrijvers
 */

public interface Memory {

    long getData(int address);

}
