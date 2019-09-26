/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/EindprCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * EindprCodePart represents the EINDPR compiler directive.
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class EindprCodePart extends LetterCodePart {

    public EindprCodePart() {
    }


    public EindprCodePart(String line, Vertaler2 vertaler) {
        setArgLine(line.substring(6));
    }

    protected LetterCodePart sibling() {
        return new EindprCodePart();
    }

    public String keyword() {
        return "EINDPR";
    }

    public boolean isLastProgramLine() {
        return true;
    }

    public int nbObjectLines() {
        return 0;
    }

    public String[] getObjectLines(int objectln)
            throws AbnormalTerminationException {

        String args = StringUtils.stripSpaces(getArgLine());

        if (args.length() != 0) {
            throw new AbnormalTerminationException("EINDPR verwacht geen argumenten: " + args);
        }

        return new String[]{};
    }

}
