/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/DeviceInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator;

/**
 * Interface of a device for the GUIController.
 *
 * @version 1.0.0 08/09/2000
 * @author  Tom Schrijvers
 */

public interface DeviceInterface {

   /**
    * @return the type of device
    */
   public String getType();

}
