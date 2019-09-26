/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ONDOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;


/**
 * OpcodeCodePart implementation for:
 *
 * OND
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class ONDOpcode extends OpcodeCodePart {

    public ONDOpcode() {
    }


    protected LetterCodePart sibling() {
        return new ONDOpcode();
    }

    public String keyword() {
        return "OND";
    }

    public int getOpcode() {
        return 61;
    }

    protected boolean acceptNbArguments(int nb) {
        return nb == 1;
    }

    public int getMode()
            throws AbnormalTerminationException {
        checkMode();
        return 99;
    }

    private void checkMode()
            throws AbnormalTerminationException {

        switch (getModeType()) {

            case MODE_W:
                throw new AbnormalTerminationException(".w is ongeldige mode/geen mode verwacht");

            case MODE_A:
                throw new AbnormalTerminationException(".a is ongeldige mode/geen mode verwacht");

            case MODE_D:
                throw new AbnormalTerminationException(".d is ongeldige mode/geen mode verwacht");

            case MODE_I:
                throw new AbnormalTerminationException(".i is ongeldige mode/geen mode verwacht");
        }

    }

    public int getAcc() {
        return 9;
    }


    public int getIdx() {
        return 9;
    }

    public int getOperand()
            throws AbnormalTerminationException {
        try {
            String str = StringUtils.trimSpaces(getArgument(0));
            int result = Integer.parseInt(str);

            if (result < 0 || result > MAX_SMALL) {
                throw new AbnormalTerminationException("ongeldige waarde: " + result);
            }

            return result;
        } catch (NumberFormatException nfe) {
            throw new AbnormalTerminationException("ongeldige waarde: " + getArgument(0));
        }

    }

}


