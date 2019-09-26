/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Class4Opcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * SPR
 * SBR
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */


public class Class4Opcode extends OpcodeCodePart {

    private final int _opcode;
    private final String _keyword;

    public Class4Opcode(String keyword, int opcode) {
        _keyword = keyword;
        _opcode = opcode;
    }

    private Class4Opcode(Class4Opcode sibl) {
        _keyword = sibl._keyword;
        _opcode = sibl._opcode;
    }

    protected LetterCodePart sibling() {
        return new Class4Opcode(this);
    }

    public String keyword() {
        return _keyword;
    }

    protected boolean acceptNbArguments(int nb) {
        return (nb == 1);
    }


    protected int getOpcode() {
        return _opcode;
    }


    protected int getMode()
            throws AbnormalTerminationException {
        return getAddressingMode() * 10 + getCrementMode();
    }

    public int getCrementMode()
            throws AbnormalTerminationException {
        return getIndexation(getIndexationString(getArgument(0)));
    }

    private int getAddressingMode()
            throws AbnormalTerminationException {
        return convertModeType();

    }

    protected int getAcc() {
        return 9;
    }

    protected int getIdx()
            throws AbnormalTerminationException {
        String is = getIndexationString(getArgument(0));
        int mode = getCrementMode();

        if (mode == NOT_INDEXED) {
            return 0;
        }

        if ((mode == PRE_INCREMENT) || (mode == PRE_DECREMENT)) {
            is = is.substring(1);
        }

        if ((mode == POST_INCREMENT) || (mode == POST_DECREMENT)) {
            is = is.substring(0, is.length() - 2);
        }

        return getRegister(is);

    }


    protected int getOperand()
            throws AbnormalTerminationException {
        String address = stripIndexation(getArgument(0));

        int result = evaluateSmall(address, getVertaler());

        if (result < 0 && getCrementMode() == NOT_INDEXED) {
            throw new AbnormalTerminationException("operandwaarde mag niet negatief: " + result);
        }

        if (result < 0) {
            result += MEMORY_SIZE;
        }

        return result;

    }

}
