/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/Condition.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.util.Map;
import java.util.HashMap;

/**
 * Superclass of all different kinds of conditions.
 * Also acts as a factory.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public abstract class Condition {

    private static final Map<String, Condition> _conditions = new HashMap<>();

    static {
        addCondition("GEL", "NUL", new GELCondition());
        addCondition("NGEL", "NNUL", new NGELCondition());
        addCondition("KL", "NEG", new KLCondition());
        addCondition("KLG", "NPOS", new KLGCondition());
        addCondition("GR", "POS", new GRCondition());
        addCondition("GRG", "NNEG", new GRGCondition());
    }

    private static void addCondition(String alias1, String alias2, Condition cond) {
        _conditions.put(alias1, cond);
        _conditions.put(alias2, cond);
    }

    /**
     * @return the condition corresponding to the given string
     */
    public static Condition parseCondition(String value)
            throws AbnormalTerminationException {
        Condition result = _conditions.get(value.toUpperCase());

        if (result == null) {
            throw new AbnormalTerminationException("Ongeldige voorwaarde: " + value);
        }

        return result;
    }

    /**
     * @return wether the given condition state meets this conditon
     */
    public abstract boolean accept(ConditionState state);

    // subclasses for the different kinds of conditions

    private static class GELCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return state.equals(ConditionState.ZERO);
        }

    }

    private static class NGELCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return !state.equals(ConditionState.ZERO);
        }

    }

    private static class KLCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return state.equals(ConditionState.NEGATIVE);
        }

    }

    private static class GRCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return state.equals(ConditionState.POSITIVE);
        }

    }

    private static class KLGCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return !state.equals(ConditionState.POSITIVE);
        }

    }

    private static class GRGCondition
            extends Condition {
        public boolean accept(ConditionState state) {
            return !state.equals(ConditionState.NEGATIVE);
        }

    }

}
