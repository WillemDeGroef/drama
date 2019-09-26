/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/BIGOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;


/**
 * OpcodeCodePart implementation for:
 * <p>
 * OND
 *
 * @author Tom Schrijvers
 * @version 1.0.0 08/08/2000
 */

public class BIGOpcode extends OpcodeCodePart {

    public BIGOpcode() {
    }


    protected LetterCodePart sibling() {
        return new BIGOpcode();
    }

    public String keyword() {
        return "BIG";
    }

    protected boolean acceptNbArguments(int nb) {
        return (nb == 2);
    }


    protected int getOpcode() {
        return 12;
    }

    protected int getMode()
            throws AbnormalTerminationException {
        return getAddressingMode() * 10 + getCrementMode();
    }

    private int getAddressingMode()
            throws AbnormalTerminationException {
        return convertModeType();

    }

    public int getCrementMode()
            throws AbnormalTerminationException {
        return getIndexation(getIndexationString(getArgument(1)));
    }

    protected int getAcc()
            throws AbnormalTerminationException {
        return getRegister(getArgument(0));
    }

    protected int getIdx()
            throws AbnormalTerminationException {
        return convertRegister();

    }

    protected int getOperand()
            throws AbnormalTerminationException {
        String address = stripIndexation(getArgument(1));

        int result = evaluateSmall(address, getVertaler());

        if (result < 0 && getCrementMode() == NOT_INDEXED) {
            throw new AbnormalTerminationException("operandwaarde mag niet negatief zijn: " + result);
        }

        if (result < 0) {
            result += MEMORY_SIZE;
        }

        return result;

    }

}
