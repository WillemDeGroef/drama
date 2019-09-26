/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/FatalMachineError.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * Class of errors that represent uncrecoverable
 * conditions that should halt the machine.
 *
 * @version 1.0.0 08/14/2000
 * @author Tom Schrijvers
 */

public class FatalMachineError extends Error {
    private static final long serialVersionUID = 0L;

    public FatalMachineError(String message) {
        super(message);
    }

}
