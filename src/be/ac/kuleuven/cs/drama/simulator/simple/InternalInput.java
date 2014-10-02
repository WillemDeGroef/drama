/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/InternalInput.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * Interface to be implemented by the
 * input devices/sources usable by
 * the simple machine components.
 *
 * @version 1.0.0 08/11/2000
 * @author Tom Schrijvers
 */

public interface InternalInput {


   /**
    * @return drama number read
    */
   public long readLong();

}
