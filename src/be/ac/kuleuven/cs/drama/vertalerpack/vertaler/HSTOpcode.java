/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/HSTOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * HST
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class HSTOpcode extends OpcodeCodePart {

    private Class1Opcode _hia;

    public String keyword() {
        return "HST";
    }

    protected LetterCodePart sibling() {
        return new HSTOpcode();
    }

    protected boolean acceptNbArguments(int nb) {
        return nb == 1;
    }

    private void initHia() {
        _hia = new Class1Opcode("HIA", 11);
        _hia.addArgument(getArgument(0));
        _hia.addArgument("0(SR+)");
    }

    protected int getOpcode()
            throws AbnormalTerminationException {
        initHia();
        return _hia.getOpcode();
    }

    protected int getMode()
            throws AbnormalTerminationException {
        return _hia.getMode();
    }

    protected int getAcc()
            throws AbnormalTerminationException {
        return _hia.getAcc();
    }

    protected int getIdx()
            throws AbnormalTerminationException {
        return _hia.getIdx();
    }

    protected int getOperand()
            throws AbnormalTerminationException {
        return _hia.getOperand();
    }

}



