/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/DummyCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * This is an ActiveCodePart for a line cannot be
 * parsed and therefor should most likely be part
 * of the comment section.
 *
 * @version 1.0.0 08/03/2000
 * @author Tom Schrijvers
 */

public class DummyCodePart extends ActiveCodePart {

    // the line
    private final String _line;

    /**
     * Initialize a new DummyCode for the given line.
     */
    public DummyCodePart(String line) {
        _line = line;
    }

    public int nbObjectLines()
            throws AbnormalTerminationException {
        //debug: System.out.println("DummyCodePart: " + _line);
        throw new AbnormalTerminationException("Ongeldige code " + _line);
    }

    public String[] getObjectLines(int objectln)
            throws AbnormalTerminationException {
        throw new AbnormalTerminationException("Ongeldige code " + _line);
    }

}
