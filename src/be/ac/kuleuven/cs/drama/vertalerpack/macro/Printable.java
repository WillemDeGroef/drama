/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/Printable.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.Set;

/**
 * Interface for all printable entities.
 *
 * @version 1.0.0 08/29/2000
 * @author Tom Schrijvers
 */

public interface Printable {

    /**
     * @return String representation
     */
    String toString(MacroExpander expander)
            throws AbnormalTerminationException;

    void getMacroLabels(Set labels);

}
