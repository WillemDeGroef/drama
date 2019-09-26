/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/BSTOpcode.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * BST
 *
 * @version 1.0.0 08/08/2000
 * @author Tom Schrijvers
 */

public class BSTOpcode extends OpcodeCodePart {

    private BIGOpcode _big;

    public String keyword() {
        return "BST";
    }

    protected LetterCodePart sibling() {
        return new BSTOpcode();
    }

    protected boolean acceptNbArguments(int nb) {
        return nb == 1;
    }

    private void initBig() {
        _big = new BIGOpcode();
        _big.addArgument(getArgument(0));
        _big.addArgument("0(-SR)");
    }

    protected int getOpcode()
            throws AbnormalTerminationException {
        initBig();
        return _big.getOpcode();
    }

    protected int getMode()
            throws AbnormalTerminationException {
        return _big.getMode();
    }

    protected int getAcc()
            throws AbnormalTerminationException {
        return _big.getAcc();
    }

    protected int getIdx()
            throws AbnormalTerminationException {
        return _big.getIdx();
    }

    protected int getOperand()
            throws AbnormalTerminationException {
        return _big.getOperand();
    }

}



