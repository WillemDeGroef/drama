/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MVGLCommand.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The MVGL command.
 *
 * @version 1.0.0 08/30/2000
 * @author Tom Schrijvers
 */

public class MVGLCommand

        extends MacroCommand {

    public MVGLCommand() {
        super();
    }


    protected void expandImpl(MacroExpander expander)
            throws AbnormalTerminationException {
        expander.setMCC(evaluate(((Printable) getArgument(0)).toString(expander)) - evaluate(((Printable) getArgument(1)).toString(expander)));
    }

    protected int getArgumentNumber() {
        return 2;
    }

    public String getName() {
        return "MVGL";
    }

    protected Object parseArgument(int index, String value, MacroDefinition context)
            throws AbnormalTerminationException {
        switch (index) {

            case 0:            // fall through


            case 1:
                return getExpression(value, context);

            default:
                throw new AbnormalTerminationException("Geen argumenten verwacht.");
        }

    }

}
