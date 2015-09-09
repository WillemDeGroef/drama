/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/ControllableMachine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator;

import java.util.Hashtable;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A ControllableMachine is a Machine that can be used by the
 * GUIController.
 *
 * @version 1.0.0 08/09/2000
 * @author Tom Schrijvers
 */

public interface ControllableMachine {

   /**
    * @return the inspectable RAM memory
    */
   public Memory getRAMMemory();

   /**
    * @return the CVO
    */
   public CVOInterface getCVOInterface();

   /**
    * @return the device with given index
    */
   public DeviceInterface getDeviceInterface(int index);

   /**
    * @return the monitor
    */
   public MonitorInterface getMonitorInterface();

   /**
    * Set the components of the machine to the given components.
    */
   public void buildMachine(String[] component, int[] address, int[] irq);

   /**
    * Load the drama object program stored in the given file.
    */
   public void loadProgram(String file, Hashtable breakpoints);

   /**
    * Start debug mode.
    */
   //public void startDebug(Hashtable breaklist);

   /**
    * Stop debug mode.
    */
   //public void stopDebug();

   /**
    * Execute one instruction.
    * 
    */
   public void step();

   /**
    * Stop the execution.
    * 
    */
   public void halt();

   /**
    * Continue the execution
    * 
    */
   public void cont();

   /**
    * Abort the execution.
    *
    */
   public void abort();

   public boolean isFinished();

   /**
    * Set the time between the execution of two instructions.
    */
   public void setMachineSleep(int millis);

   /**
    * @return the inputstream used for input from file
    */
   public InputStream getInputStream();

   /**
    * set the inputstream used for input from file
    */
   public void setInputStream(InputStream in);

   /**
    * @return the outputstream for output instructions
    */
   public OutputStream getOutputStream();

   /**
    * set the ouputstream for output instructions
    */
   public void setOutputStream(OutputStream out);

   /**
    * User input that should/could be handled.
    * HACK IMPOSED BY DramaMachine
    */
   public void handleInput(String input);

   /**
    * reset the statistics
    */
   public void resetStats();

   /**
    * write the gathered statistics of
    * instruction execution to file
    */
   public void writeRunTimeStatsToFile();
   
   /**
    * clears the output screen
    */
   public void clear();

}
