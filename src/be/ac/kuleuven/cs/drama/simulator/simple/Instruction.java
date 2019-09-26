/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/Instruction.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * Container class for drama instruction.
 *
 * @version 1.0.0 08/10/2000
 * @author Tom Schrijvers
 */

public class Instruction {

    private final long _instruction;

    /**
     * Initialize a new Instruction with the given dramaNumber
     */
    public Instruction(long instruction) {
        _instruction = instruction;
    }

    /**
     * @return the opcode
     */
    public int opcode() {
        return digits(9, 8);
    }

    /**
     * @return the addressing mode
     */
    public int addressing() {
        return digits(7, 7);
    }

    /**
     * @return the indexation mode
     */
    public int indexation() {
        return digits(6, 6);
    }

    /**
     * @return the accumulator
     */
    public int acc() {
        return digits(5, 5);
    }

    /**
     * @return the index register
     */
    public int idx() {
        return digits(4, 4);
    }

    /**
     * @return the operand
     */
    public int operand() {
        return digits(3, 0);
    }


    /*
     * @return the specified interval of digits from the instruction
     *
     * 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
     */
    private int digits(int left, int right) {
        long result = _instruction / power10(right);
        //System.out.println("after shift " + result);
        result = result % power10(left - right + 1);
        //System.out.println("after mod   "  + result);
        return (int) result;
    }

    /*
     * @return power of ten
     */
    private long power10(int exp) {
        return (long) Math.pow(10, exp);
    }

    public String toString() {
        return Long.toString(_instruction);
    }

    // testing only
    public static void main(String[] args) {
        System.out.println(new Instruction(1234567890L).opcode());
    }

}
