/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ActiveCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * The ActiveCodePart is the part of a source line that
 * without the label and comment part.
 *
 * Conversion to decimal code is handled by subclasses of this class.
 *
 * @version 1.0.0 08/03/2000
 * @author Tom Schrijvers
 */

public abstract class ActiveCodePart {

    // the number of cells in memory
    public static final int MEMORY_SIZE = 10000;
    // the maximum 4 digit number
    public static final int MAX_SMALL = MEMORY_SIZE - 1;
    // the minimum 4 digit negative number
    public static final int MIN_SMALL = -(MEMORY_SIZE / 2);
    // the number of different possible values in a cell
    public static final long CELL_SIZE = 10000000000L;

    /**
     * @return the number of lines of objectcode
     * represented by this ActiveCodePart
     */
    public abstract int nbObjectLines()
            throws AbnormalTerminationException;

    /**
     * @return the object code lines represented
     * by this ActiveCodePart
     */
    public abstract String[] getObjectLines(int objectln)
            throws AbnormalTerminationException;

    /**
     * @return wether this is the last line of the actual
     * program
     */
    public boolean isLastProgramLine() {
        return false;
    }

    /**
     * ensure that the given number is a valid address
     */
    protected final int address(int number)
            throws AbnormalTerminationException {
        if ((number < 0) || (number >= MEMORY_SIZE)) {
            throw new AbnormalTerminationException("ongeldig adres " + number);
        }

        return number;
    }

    /**
     * evaluate the given operand expression with the given vertaler
     */
    protected int evaluateSmall(String expression, Vertaler2 vertaler)
            throws AbnormalTerminationException {
        long result = evaluateLong(expression, vertaler);

        if ((result > MAX_SMALL) || (result < MIN_SMALL)) {
            throw new AbnormalTerminationException("waarde niet binnen " + MIN_SMALL + " en " +
                    MAX_SMALL + " : " + result);
        }

        return (int) result;
    }

    /**
     * evaluate the given operand expression with the given vertaler
     */
    protected long evaluateLong(String expression, Vertaler2 vertaler)
            throws AbnormalTerminationException {
        String str = StringUtils.trimSpaces(expression).toUpperCase();
        long result;

        if (expression.length() == 0) {
            throw new AbnormalTerminationException("ongeldige expressie: " + expression);
        }

        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            result = getSymbolicAddress(str, vertaler);
        }

        return result;
    }

    /**
     * evaluate the given expression with label with the given vertaler
     */
    private long getSymbolicAddress(String str, Vertaler2 vertaler)
            throws AbnormalTerminationException {
        long result;
        //System.out.println("getSymbolicAddress(" + str + ")");
        if (str.length() == 0) {
            throw new AbnormalTerminationException("Ontbrekende operand");
        }

        int i = str.indexOf('+');
        int j = str.indexOf('-');

        if ((i != -1) || (j != -1)) {
            boolean plus = true;

            if (((j != -1) && (i > j)) || (i == -1)) {
                plus = false;
                i = j;
            }

            if (i == 0) {
                throw new AbnormalTerminationException("Ontbrekende operand voor + of -");
            }

            String op1 = str.substring(0, i).trim();
            String op2;

            try {
                op2 = str.substring(i + 1).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new AbnormalTerminationException("Ontbrekende operand na + of -");
            }

            long val1, val2;

            try {
                val1 = vertaler.getSymbolValue(op1);
                val2 = Long.parseLong(op2);
            } catch (Exception ee) {
                try {
                    val1 = Long.parseLong(op1);
                    val2 = vertaler.getSymbolValue(op2);
                } catch (Exception e) {
                    throw new AbnormalTerminationException("Ongeldige expressie: " + str);
                }

            }

            if (plus) {
                //System.out.println("Got '" + op1 + "' + '" + op2 + "'");
                result = val1 + val2;
            } else {
                //System.out.println("Got '" + op1 + "' - '" + op2 + "'");
                result = val1 - val2;
            }
        } else {
            result = vertaler.getSymbolValue(str.trim());
        }

        return result;

        //      StringBuffer buffer = new StringBuffer();
        //      int index = 0;
        //      if(str.length() == 0) {
        //         throw new AbnormalTerminationException("ontbrekende operand");
        //      }
        //      if(!SourceLine.isFirstLabelCharacter(str.charAt(index))) {
        //         throw new AbnormalTerminationException("ongeldige operand: " + str );
        //      }
        //      while (index < str.length() && SourceLine.isLabelCharacter(str.charAt(index))) {
        //         buffer.append(str.charAt(index));
        //         index++;
        //      }
        //      long result = vertaler.getSymbolValue(buffer.toString());
        //      str = StringUtils.trimLeftSpaces(str.substring(index));
        //      if (str.length() == 0) {
        //         return result;
        //      }
        //      try {
        //         if (str.startsWith("+")) {
        //            str = str.substring(1);
        //         }
        //         result += Long.parseLong(str);
        //         return result;
        //      } catch (NumberFormatException nfe) {
        //         nfe.printStackTrace();
        //         throw new AbnormalTerminationException("ongeldige subexpressie: " + str);
        //      }
    }


}
