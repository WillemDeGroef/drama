/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/PrintableStringWrapper.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import java.util.Set;

/**
 * Printable wrapper for a String.
 *
 * @version 1.0.0 08/29/2000
 * @author Tom Schrijvers
 */


public class PrintableStringWrapper

        implements Printable {
    private final String _string;

    public PrintableStringWrapper(String string) {
        _string = string;
    }

    public String toString(MacroExpander expander) {
        return _string;
    }

    public void getMacroLabels(Set labels) {
    }


}
