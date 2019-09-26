/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroLine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

/**
 * Superclass for all classes that represent
 * lines in a macro.
 *
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 * @version 1.0.0 08/29/2015
 */

public abstract class MacroLine {

    private static boolean _terminationRequested;

    private MacroLabel _label = null;
    private String _line;
    private int _lineno;

    protected MacroLine() {
    }

    public void initCaller(String line, MacroDefinition context, int lineno)
            throws AbnormalTerminationException {
        this.setLineNo(lineno);
        this.init(line, context);
    }

    public abstract void init(String line, MacroDefinition context)
            throws AbnormalTerminationException;

    public String getLine() {
        return this._line;
    }

    protected void setLineNo(int lineno) {
        this._lineno = lineno;
    }

    public int getLineNo() {
        return this._lineno;
    }

    public boolean isMacroCommand() {
        return true;
    }

    public final void expand(MacroExpander expander)
            throws AbnormalTerminationException {
        if (_terminationRequested)
            throw new AbnormalTerminationException("Voorvertaling is geforceerd gestopt.");

        this.expandImpl(expander);
        String text = this.outString(expander);
        boolean outputText = !this.isMacroCommand() || (text.length() > 0);

        if (this._label != null && this._label.shouldBePrinted()) {
            if (this.labelOnSeparateLine() || !outputText) {
                expander.println(this.getLineNo(), this._label.toString(expander) + ":");
            } else {
                expander.print(this.getLineNo(), this._label.toString(expander) + ":");
            }
        }

        if (outputText) {
            expander.println(this.getLineNo(), text);
        }

    }

    public void setLabel(MacroLabel label) {
        this._label = label;
    }

    /**
     * Implementation of the actions necessary before writing
     * out the line.
     */
    protected abstract void expandImpl(MacroExpander expander)
            throws AbnormalTerminationException;

    /**
     * @return the String of this line that has to be written out
     */
    protected abstract String outString(MacroExpander expander)
            throws AbnormalTerminationException;

    /**
     * @return wether this is the last line of a macro (default is false)
     */
    public boolean isLastLine() {
        return false;
    }

    /**
     * parse the given string to a Printable
     */
    public static Printable getExpression(String expression, MacroDefinition context)
            throws AbnormalTerminationException {
        PrintableSequence sequence = new PrintableSequence();
        int index = 0;
        StringBuilder builder = new StringBuilder();

        while (index < expression.length()) {
            if (expression.charAt(index) == MacroVariable.VARIABLE_OPEN) {
                if (builder.length() > 0) {
                    sequence.add(new PrintableStringWrapper(builder.toString()));
                    builder.delete(0, builder.length());
                }

                if (expression.indexOf(MacroVariable.VARIABLE_CLOSE, index + 1) == -1) {
                    throw new AbnormalTerminationException("Ontbrekende " +
                            MacroVariable.VARIABLE_CLOSE +
                            " in macrovariabele.");
                } else {
                    sequence.add(getMacroVariable(expression.substring(index,
                            expression.indexOf(MacroVariable.VARIABLE_CLOSE,
                                    index + 1) + 1)));
                    index = expression.indexOf(MacroVariable.VARIABLE_CLOSE, index + 1) + 1;
                }
            } else if (expression.charAt(index) == MacroLabel.LABEL_START) {
                if (builder.length() > 0) {
                    sequence.add(new PrintableStringWrapper(builder.toString()));
                    builder.delete(0, builder.length());
                }

                int labelStart = index;

                while (index < expression.length() && !StringUtils.isSpace(expression.charAt((index)))) {
                    index++;
                }

                sequence.add(context.getLabel(expression.substring(labelStart, index)));
            } else {
                builder.append(expression.charAt(index));
                index += 1;
            }

        }

        if (builder.length() > 0) {
            sequence.add(new PrintableStringWrapper(builder.toString()));
        }

        return sequence;
    }

    /**
     * parse the given string to a macro variable
     */
    protected static MacroVariable getMacroVariable(String string)
            throws AbnormalTerminationException {
        return MacroVariable.getMacroVariable(string);
    }

    protected void setAllLabelsPrintable(Printable p) {
        Set<MacroLabel> labels = new HashSet<>();
        p.getMacroLabels(labels);

        for (MacroLabel label : labels) {
            label.setShouldBePrinted();
        }

    }

    protected boolean labelOnSeparateLine() {
        return true;
    }

    public static void setTerminationRequest(boolean requested) {
        _terminationRequested = requested;
    }

    public void setLine(String _line) {
        this._line = _line;
    }
}
