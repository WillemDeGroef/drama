/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroVariable.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.Set;

/**
 * Macro variable.
 *
 * @author Tom Schrijvers
 * @version 1.0.0 08/29/2000
 */

public class MacroVariable

        implements Printable {
    public static final char VARIABLE_OPEN = '<';
    public static final char VARIABLE_CLOSE = '>';


    private final String _name;

    /**
     * Init
     */
    public MacroVariable(String name) {
        _name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the value
     */
    public String toString(MacroExpander expander)
            throws AbnormalTerminationException {
        if (!expander.getLocalVariableTable().contains(_name)) {
            throw new AbnormalTerminationException("Macrovariable " + _name + " niet gedefinieerd");
        }

        return expander.getLocalVariableTable().get(_name);
    }

    /**
     * parse the given string to a macro variable
     */
    public static MacroVariable getMacroVariable(String string)
            throws AbnormalTerminationException {
        if ((string.length() < 3) ||
                (string.charAt(0) != VARIABLE_OPEN) ||
                (string.charAt(string.length() - 1) != VARIABLE_CLOSE)) {
            throw new AbnormalTerminationException("Ongeldige macrovariabele: " + string);
        }

        String name = string.substring(1, string.length() - 1);

        if (!Name.isName(name)) {
            throw new AbnormalTerminationException("Geen welgevormde macrovariabelenaam: " + name);
        }

        return new MacroVariable(name);
    }

    /**
     * equals implementation for usage in Set and Map
     */
    public boolean equals(Object obj) {
        return (obj != null) &&
                (this.getClass().equals(obj.getClass())) &&
                (this.getName().equals(((MacroVariable) obj).getName()));
    }

    /**
     * hashcode implementation for usage in Set and Map
     */
    public int hashCode() {
        return getName().hashCode();
    }

    public void getMacroLabels(Set set) {
    }


}
