/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Class5Opcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.List;
import java.util.ArrayList;

/**
 * OpcodeCodePart implementation for:
 *
 * MKH
 * MKL
 * TSM
 * TSO
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */


public class Class5Opcode extends OpcodeCodePart {

    private final String _keyword;
    private final int _opcode;

    public Class5Opcode(String k, int o) {
        _keyword = k;
        _opcode = o;
    }

    private Class5Opcode(Class5Opcode o) {
        _keyword = o._keyword;
        _opcode = o._opcode;
    }

    protected LetterCodePart sibling() {
        return new Class5Opcode(this);
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
        String str = StringUtils.trimSpaces(getArgument(0)).toUpperCase();

        try {
            int result = Integer.parseInt(str);

            if (result < 0 || result > 9) {
                throw new AbnormalTerminationException("ongeldige vlag: " + result);
            }

            return result;
        } catch (NumberFormatException nfe) {
            int result = _flags.lastIndexOf(str);

            if (result == -1) {
                throw new AbnormalTerminationException("ongeldige vlag: " + result);
            }

            return result;
        }

    }

    protected int getIdx() {
        return 9;
    }

    protected int getOperand() {
        return 9999;
    }

    private static final List _flags = new ArrayList(10);

    static {
        _flags.add("G");
        _flags.add("GPF");
        _flags.add("WEK");
        _flags.add("DRK");
        _flags.add("IN");
        _flags.add("UIT");
        _flags.add("SCH");
        _flags.add("OVL");
        _flags.add("SPL");
        _flags.add("MFT");
    }

}
