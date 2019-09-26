/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/OrdinaryLine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * Ordinary macro line. Not a macro command.
 * Only text to be copied.
 *
 * @version 1.0.0 08/29/2000
 * @author Tom Schrijvers
 */

public class OrdinaryLine extends MacroLine {

    private static final boolean DEBUG = false;
    private Printable _line;

    public OrdinaryLine() {
    }


    public void init(String line, MacroDefinition context)
            throws AbnormalTerminationException {
        if (DEBUG) System.out.println("OrdinaryLine.init: " + line);

        String comment = "";

        if (line.contains("|")) {
            comment = line.substring(line.indexOf("|"));
        }

        _line = getExpression(Comment.stripComment(line), context);
        ((PrintableSequence) _line).add(new PrintableStringWrapper(comment));
        setAllLabelsPrintable(_line);
    }

    /**
     * No extra actions necessary
     */
    public void expandImpl(MacroExpander expander) {
    }


    public String outString(MacroExpander expander)
            throws AbnormalTerminationException {
        if (DEBUG) System.out.println("OrdinaryLine.outString(): " + _line.toString(expander));

        return _line.toString(expander);
    }

    public boolean isMacroCommand() {
        return false;
    }

    protected boolean labelOnSeparateLine() {
        return false;
    }

}
