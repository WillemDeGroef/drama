/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/InternalMachine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.util.StatistiekModule;

/**
 * Interface to be implemented by the
 * machines that would like to use
 * the simple machine components.
 *
 * @version 1.0.0 08/090/2000
 * @author Tom Schrijvers
 */

public interface InternalMachine {

    /**
     * @return the RAM
     */
    SimpleRAM ram();

    /**
     * @return the CPU
     */
    SimpleCPU cpu();

    /**
     * @return the output device
     */
    InternalMonitor monitor();

    /**
     * @return the input device
     */
    InternalInput input();

    /**
     * @return the statistics module
     */
    StatistiekModule statistics();

    /**
     * @return wether the execution is halted
     */
    boolean isHalted();

    /**
     * stop the execution of the machine
     */
    void halt();

    void finish();

    /**
     * @return wether the machine is in debug mode.
     */
    //public boolean isDebug();

    /**
     * write a meta-message to the system
     */
    void systemMessage(String message);

    void halted();

}
