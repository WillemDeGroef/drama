/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/InternalMachine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
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
   public SimpleRAM ram();

   /**
    * @return the CPU
    */
   public SimpleCPU cpu();

   /**
    * @return the output device
    */
   public InternalMonitor monitor();

   /**
    * @return the input device
    */
   public InternalInput input();

   /**
    * @return the statistics module
    */
   public StatistiekModule statistics();

   /**
    * @return wether the execution is halted
    */
   public boolean isHalted();

   /**
    * stop the execution of the machine
    */
   public void halt();

   public void finish();

   /**
    * @return wether the machine is in debug mode.
    */
   //public boolean isDebug();

   /**
    * write a meta-message to the system
    */
   public void systemMessage(String message);

   void halted();

}
