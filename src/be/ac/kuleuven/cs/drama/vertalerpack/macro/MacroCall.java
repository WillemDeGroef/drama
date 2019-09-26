/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroCall.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * MacroLine that represents the call 
 * to a macro.
 *
 * @version 1.0.0 08/31/2000
 * @author Tom Schrijvers
 */

public class MacroCall extends MacroLine {

    private final MacroDefinition _callee;
    private List<Printable> _actualParameters;

    public MacroCall(MacroDefinition callee) {
        _callee = callee;
    }

    public void init(String parameters, MacroDefinition context)
            throws AbnormalTerminationException {
        _actualParameters = parseActualParameters(parameters, context);

        for (Printable actualParameter : _actualParameters) {
            setAllLabelsPrintable(actualParameter);
        }

    }

    private List<Printable> parseActualParameters(String params, MacroDefinition context)
            throws AbnormalTerminationException {
        StringTokenizer tokenizer = new StringTokenizer(params, ",");
        List<Printable> formalParams = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            String param = StringUtils.trimSpaces(tokenizer.nextToken());
            formalParams.add(getExpression(param, context));
        }

        return formalParams;
    }


    protected String outString(MacroExpander expander) {
        return "";
    }

    protected void expandImpl(MacroExpander expander)
            throws AbnormalTerminationException {
        List<String> actualParams = new ArrayList<>();

        for (Printable actualParameter : _actualParameters) {
            actualParams.add(actualParameter.toString(expander));
        }

        expander.setLineNo(_callee.expand(expander.out(), actualParams, expander.getLineNo()));
    }

}
