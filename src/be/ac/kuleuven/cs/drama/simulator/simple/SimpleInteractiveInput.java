/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleInteractiveInput.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.events.InputRequestEventManager;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

/**
 * SimpleInteractiveInput is an input handler that only handles
 * input from outside.
 *
 * The input from outside is only used if the machine is waiting for it.
 *
 * @version 1.0.0 08/11/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public class SimpleInteractiveInput

        implements MachineInput {

    private static final boolean DEBUG = false;
    private volatile boolean _abort = false;
    private volatile boolean _waiting = false;
    private String _input = null;

    private final InternalMachine _machine;

    /**
     * Initialize a new SimpleInteractiveInput for the given machine.
     */
    public SimpleInteractiveInput(InternalMachine machine) {
        _machine = machine;
    }

    /**
     * Abort waiting thread and reset.
     */

    public synchronized void reset() {

        _abort = true;

        notify();

        if (!_waiting)
            _abort = false;
    }

    /**
     * @return a long read from outside
     * blocks until valid input is available
     */
    public synchronized long readLong() {

        _waiting = true;
        long result = 0;

        _machine.monitor().requestInput();


        while (_input == null) {

            InputRequestEventManager.fireEvent();

            try {
                if (DEBUG) System.out.println("Waiting for input");

                wait();

                if (_abort)
                    break;

            } catch (InterruptedException ie) {
                _waiting = false;
                _input = null;

                if (DEBUG) System.out.println("Read interrupted");

                throw new RuntimeException("Read interrupted");
            }

            try {
                result = Long.parseLong(_input);
                result = NumberFormat.toSafeDramaNumber(result);
            } catch (NumberFormatException nfe) {
                if (DEBUG) nfe.printStackTrace();
                _machine.systemMessage("Gelieve een correct dramagetal in te voeren. Foute invoer: " + _input);
                _input = null;
            }

        }

        _input = null;
        _waiting = false;

        if (_abort) {
            _abort = false;
            throw new RuntimeException("LEZ afgebroken");
        }

        _machine.monitor().confirmInput(result);

        return result;
    }

    /**
     * Handle the given input.
     * Only used if machine is waiting for input
     */

    public synchronized void setInput(String input) {
        if (_waiting) {

            input = StringUtils.trimSpaces(input);

            if (input.endsWith("\n")) {
                input = input.substring(0, input.length() - 1);
            }

            //System.out.println("Supplying input: " + input);

            //System.out.println("String of length: " + input.length());


            _input = input;

            notify();
        } else {
            _machine.systemMessage("Geen invoer verwacht. Invoer wordt niet verwerkt: " + input);
        }

    }

    public synchronized boolean isWaiting() {
        return _waiting;
    }
}
