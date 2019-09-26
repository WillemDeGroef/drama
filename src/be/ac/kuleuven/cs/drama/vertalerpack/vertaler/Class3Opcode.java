/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Class3Opcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * OpcodecodePart implementation for:
 *
 * INV
 * UTV
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */


public class Class3Opcode extends OpcodeCodePart {

    private final String _keyword;
    private final int _opcode;

    public Class3Opcode(String k, int o) {
        _keyword = k;
        _opcode = o;
    }

    private Class3Opcode(Class3Opcode o) {
        _keyword = o._keyword;
        _opcode = o._opcode;
    }

    protected LetterCodePart sibling() {
        return new Class3Opcode(this);
    }

    public String keyword() {
        return _keyword;
    }

    protected boolean acceptNbArguments(int nb) {
        return (nb == 2);
    }


    protected int getOpcode() {
        return _opcode;
    }

    protected int getMode()
            throws AbnormalTerminationException {
        checkAddressingMode();
        return 99;
    }

    private void checkAddressingMode()
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

    protected int getAcc()
            throws AbnormalTerminationException {
        return getRegister(getArgument(0));
    }

    protected int getIdx() {
        return 9;
    }

    public int getOperand()
            throws AbnormalTerminationException {
        try {
            String str = StringUtils.trimSpaces(getArgument(1));
            int result = Integer.parseInt(str);

            if (result < 0 || result > MAX_SMALL) {
                throw new AbnormalTerminationException("ongeldige waarde: " + result);
            }

            return result;
        } catch (NumberFormatException nfe) {
            throw new AbnormalTerminationException("ongeldige waarde: " + getArgument(1));
        }

    }

}


