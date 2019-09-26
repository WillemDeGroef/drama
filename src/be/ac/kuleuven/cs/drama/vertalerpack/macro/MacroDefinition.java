/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroDefinition.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

/**
 * A definition of a macro.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class MacroDefinition {

    private List<String> _formalParameters;
    /*
     * the name of this macro
     * the name is null if this is the main program
     */
    private String _name;
    /*
     * the contents of a macro
     * this is a list of MacroLine's
     */
    private List<MacroLine> _lines;
    private ExpansionManager _manager;
    private Map<String, MacroLabel> _labels;
    /* the lineno where this macro starts */
    private int _lineno;

    /**
     * Initialise a main macro.
     *
     * A main macro has no name: getName() == null
     */
    public MacroDefinition(int lineno, ExpansionManager manager)
            throws AbnormalTerminationException {
        this._lines = new ArrayList<>();
        this._manager = manager;
        this._labels = new HashMap<>();
        this._formalParameters = new ArrayList<>();
        this._lineno = lineno;
    }

    /**
     * Initialise a normal macro.
     */
    public MacroDefinition(int lineno, ExpansionManager manager, String header)
            throws AbnormalTerminationException {
        this(lineno, manager);
        this.setHeaderInfo(header);
    }

    public int getLineNo() {
        return this._lineno;
    }

    /**
     * set the name of this macro definition
     */
    protected void setName(String name) {
        this._name = name;
    }

    /**
     * parse the given line to set the name and the formal parameters
     */
    protected void setHeaderInfo(String header)
            throws AbnormalTerminationException {
        // strip comment and unnecessary spaces
        String actualLine = StringUtils.trimSpaces(Comment.stripComment(header));
        // split off the name
        int nameEnd = 0;

        while (nameEnd < actualLine.length() && !StringUtils.isSpace(actualLine.charAt(nameEnd))) {
            nameEnd += 1;
        }

        String name = actualLine.substring(0, nameEnd);
        String params = actualLine.substring(nameEnd);

        if (!Name.isName(name)) {
            throw new AbnormalTerminationException("Ongeldige macronaam: " + name);
        }

        this.setName(name);
        // parse the formal parameters
        StringTokenizer tokenizer = new StringTokenizer(params, ",");
        List<String> formalParams = new ArrayList<String>();

        while (tokenizer.hasMoreTokens()) {
            String param = StringUtils.trimSpaces(tokenizer.nextToken());

            if (!Name.isName(param)) {
                throw new AbnormalTerminationException("Geen aanvaardbare naam voor een "
                        + "formele parameter: " + param);
            }

            formalParams.add(param);
        }

        this.setFormalParameters(formalParams);
    }

    /**
     * set the formal parameters
     */
    protected void setFormalParameters(List<String> params) {
        this._formalParameters = params;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this._name;
    }

    /**
     * add a line to this macro definition
     */
    public void addLine(int lineno, String line)
            throws AbnormalTerminationException {
        this._lines.add(new MacroLineFactory().parseLine(line, lineno, this));
    }

    /**
     * @return wether this macro has not ended
     * and another line should be added
     */
    public boolean wantNextLine() {
        return this._lines.size() == 0 || !this._lines.get(this._lines.size() - 1).isLastLine();
    }

    /**
     * Expand this macro into the given PrintWriter.
     * @param out the PrintWriter that the output should be written to
     * @param actualParameters the values of the parameters
     */
    public int expand(PrintWriter out, List actualParameters, int lineno)
            throws AbnormalTerminationException {
        this.checkParameters(actualParameters);
        VariableTable localVariableTable =
                new VariableTable(this._formalParameters, actualParameters,
                        this._manager.getGlobalVariableTable());
        MacroExpander m = new MacroExpander(this._manager);
        m.expand(localVariableTable, this._lines, out, lineno);
        return m.getLineNo();
    }

    /**
     * @return the number of lines this macro expands to
     */
    public int nbOfSourceLines() {
        // the main program has an extra MCREINDE
        // by a normal macro we take MACRO and the header into account
        return this.isMainProgram() ? this._lines.size() - 1 : this._lines.size() + 2;
    }

    /**
     * Because the main program is looked at as a special macro (a macro with
     * a special name), we need a way to descriminate between normal macro's
     * and the main progam.
     */
    public boolean isMainProgram() {
        return this.getName() == null;
    }

    /**
     * Checks wether the correct number of parameters is supplied.
     */
    private void checkParameters(List actualParameters)
            throws AbnormalTerminationException {
        if (actualParameters.size() != this._formalParameters.size()) {
            throw new AbnormalTerminationException("Ongeldig aantal parameters in " + this.getName() + "!");
        }
    }

    /**
     * @return the shared instance of the macro label in a macro definition
     */
    public MacroLabel getLabel(String labelString)
            throws AbnormalTerminationException {
        MacroLabel label = MacroLabel.getLabel(labelString);

        if (!this._labels.containsKey(label.getName())) {
            this._labels.put(label.getName(), label);
        }

        return _labels.get(label.getName());
    }

    public boolean isMacroName(String name) {
        return this._manager.containsMacroDefinition(name);
    }

    public MacroDefinition getMacro(String name) {
        return this._manager.getMacroDefinition(name);
    }

    public int getNumberOfLines() {
        return this._lines.size();
    }

}
