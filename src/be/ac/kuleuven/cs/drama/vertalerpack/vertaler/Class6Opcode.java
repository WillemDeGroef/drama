/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Class6Opcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * KTG
 * KTO
 * LEZ
 * DRU
 * STP
 * NTS
 * NWL
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */


public class Class6Opcode extends OpcodeCodePart {

    private final String _keyword;
    private final int _opcode;

    public Class6Opcode(String keyword, int opcode) {
        _keyword = keyword;
        _opcode = opcode;
    }

    private Class6Opcode(Class6Opcode sibl) {
        _keyword = sibl._keyword;
        _opcode = sibl._opcode;
    }

    protected LetterCodePart sibling() {
        return new Class6Opcode(this);
    }

    public String keyword() {
        return _keyword;
    }

    protected boolean acceptNbArguments(int nb) {
        return nb == 0;
    }

    protected int getOpcode() {
        return _opcode;
    }

    protected int getMode()
            throws AbnormalTerminationException {
        if (getModeType() != MODE_DEFAULT) {
            throw new AbnormalTerminationException("ongeldige mode");
        }

        return 99;

    }

    protected int getAcc() {
        return 9;
    }

    protected int getIdx() {
        return 9;
    }

    protected int getOperand() {
        return 9999;
    }

}
