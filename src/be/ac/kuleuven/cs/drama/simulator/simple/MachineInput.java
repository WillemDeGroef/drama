/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/MachineInput.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * Interface for input handling facilities
 * that accpet input from outside and are resettable..
 *
 * @version 1.0.0
 * @author  Tom Schrijvers
 */

public interface MachineInput

   extends InternalInput {

   /**
    * reset the state of the input handler
    */
   public void reset();

   /**
    * handle the given outside input
    * IMPOSED BY GUICONTROLLER
    */
   public void setInput(String input);
   
   /**
    * @return whatever the user is currently being asked for input
    */
   public boolean isWaiting();

}
