/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/OpcodeDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;

/**
 * Decoder of an opcode instruction.
 *
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 * @version 1.0.0 08/11/2015
 */

public abstract class OpcodeDecoder {

    //protected static final long MAX = 10000000000L;

    //protected static final long MAX_POSITIVE = 4999999999L;

    // INDEXATION MODE

    private static final int NO_INDEXATION = 1;
    private static final int INDEXATION = 2;
    private static final int PRE_INCREMENT = 3;
    private static final int POST_INCREMENT = 4;
    private static final int PRE_DECREMENT = 5;
    private static final int POST_DECREMENT = 6;

    // OPERAND MODE
    private static final int VALUE = 1;
    private static final int ABSOLUTE_VALUE = 2;
    private static final int ADDRESS = 3;
    private static final int INDIRECT_ADDRESS = 4;

    /**
     * @return the opcode that this is a decoder for
     */
    public abstract int opcode();

    /**
     * @return the mnemocode of the instruction that this is a decoder for
     */

    public abstract String mnemocode();

    /**
     * Decode the given instruction for the given machine.
     */
    public void decode(Instruction instruction, InternalMachine machine) {
        checkValidity(instruction);
        decodeImpl(instruction, machine);
        machine.statistics().addOccurence(opcode());
    }

    /**
     * Decode the given instruction for the given machine.
     * Template method.
     */
    protected abstract void decodeImpl(Instruction instruction, InternalMachine machine);

    /**
     * @return the indexation value to be added to the operand
     * Handle any decrement or increment as well.
     */
    private long getIndexationValue(Instruction instruction, InternalMachine machine) {

        int mode = instruction.indexation();

        long value = 0;

        switch (mode) {

            case NO_INDEXATION:
                return 0;

            case INDEXATION:
                return machine.cpu().register(instruction.idx());

            case PRE_INCREMENT:
                //System.out.println("PRE_INCREMENT");
                value = machine.cpu().register(instruction.idx());
                value++;
                machine.cpu().setRegister(instruction.idx(), value);
                return value;

            case POST_INCREMENT:
                //System.out.println("POST_INCREMENT");
                value = machine.cpu().register(instruction.idx());
                machine.cpu().setRegister(instruction.idx(), value + 1);
                return value;

            case PRE_DECREMENT:
                value = machine.cpu().register(instruction.idx());
                value--;
                machine.cpu().setRegister(instruction.idx(), value);
                return value;

            case POST_DECREMENT:
                value = machine.cpu().register(instruction.idx());
                machine.cpu().setRegister(instruction.idx(), value - 1);
                return value;

            default:
                System.out.println("Omgeldige indexatiemode");
                return 0;
        }

    }

    /**
     * @return the entire operand value
     */
    protected final long getOperandValue(Instruction instruction, InternalMachine machine) {
        long operand = instruction.operand();
        long index = getIndexationValue(instruction, machine);

        operand = operand + index;

        switch (instruction.addressing()) {

            case VALUE:
                operand = operand - index;

                if (operand > 4999) {
                    operand += 9999990000L;
                }

                return operand + index;

            case ABSOLUTE_VALUE:
                return operand;

            case ADDRESS:
                operand = (machine.cpu().addGBE(operand)) % 10000;
                return machine.ram().cell((int) operand);

            case INDIRECT_ADDRESS:
                operand = (machine.cpu().addGBE(operand)) % 10000;
                return machine.ram().cell((int) machine.cpu().addGBE(machine.ram().cell((int) operand)));

            default:
                System.out.println("No valid addressing mode");
                return 0;
        }

    }

    /**
     * Set the condition code of the given machine based on the sign of the given value
     */
    protected final void setCC(InternalMachine machine, long value) {

        int result = 0;
        long javaNumber = NumberFormat.toJavaNumber(value);

        if (javaNumber < 0) {
            result = 2;
        } else {
            // javaNumber > 0
            result = 1;
        }

        machine.cpu().ptw().setCC(result);

    }

    /**
     * Set the condition code of the given machine based on the sign of the given value
     */
    protected final void setOVI(InternalMachine machine, boolean overflow) {
        machine.cpu().ptw().setOVI(overflow);
    }

    /**
     * @return wether this decoder uses the addressing mode field
     */
    protected abstract boolean usesAddressing();

    /**
     * @return wether this decoder uses the indexation mode field and the index register field
     */
    protected abstract boolean usesIndexation();

    /**
     * @return wether this decoder uses the accumulator field
     */
    protected abstract boolean usesAcc();

    /**
     * @return wether this decoder uses the operand field
     */
    protected abstract boolean usesOperand();

    /**
     * @return wether this decoder is privileged
     */
    protected abstract boolean isPrivileged();

    /*
     * check wether the fields of the instruction are all valid
     */
    private void checkValidity(Instruction instruction) {
        checkAddressing(instruction);
        checkIndexation(instruction);
        checkAcc(instruction);
        checkOperand(instruction);
    }

    /*
     * check wether the addressing mode field is valid
     */
    private void checkAddressing(Instruction instruction) {
        if (!usesAddressing()) {
            if (instruction.addressing() != 9) {
                throw new FatalMachineError("Geen addressering verwacht in " + this + " bij " + instruction);
            }
        } else {

            switch (instruction.addressing()) {

                case 0:

                case 5:

                case 6:

                case 7:

                case 8:

                case 9:
                    throw new FatalMachineError("Ongeldige addressering in " + this + " bij " + instruction);
            }

        }

    }

    /*
     * check wether the indexation mode field and the index register field are valid
     */
    private void checkIndexation(Instruction instruction) {
        if (!usesIndexation()) {
            if (instruction.indexation() != 9) {
                throw new FatalMachineError("Ongeldige indexatiemode in " + this + " bij " + instruction);
            }

            if (instruction.idx() != 9) {
                throw new FatalMachineError("Ongeldig indexveld in " + this + " bij " + instruction);
            }
        } else {

            switch (instruction.indexation()) {

                case 0:

                case 7:

                case 8:

                case 9:
                    throw new FatalMachineError("Ongeldige indexeringsmode in " + this + " bij " + instruction);
            }

        }

    }

    /*
     * check wether the accumulator field is valid
     */
    private void checkAcc(Instruction instruction) {
        if (!usesAcc()) {
            if (instruction.acc() != 9) {
                throw new FatalMachineError("Ongeldig accumulatorveld in " + this + " bij " + instruction);
            }

        }

    }

    /*
     * check wether the operand field is valid
     */
    private void checkOperand(Instruction instruction) {
        if (!usesOperand()) {
            if (instruction.operand() != 9999) {
                throw new FatalMachineError("Ongeldig operandveld in " + this + " bij " + instruction);
            }

        }

    }

    public final String toString() {
        return getClass().getName();
    }

}
