/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/CVOInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator;

import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;

/**
 * Interface of CVO registers collection
 * for GUIController
 *
 * @version 1.0.0 08/09/2000
 * @author Tom Schrijvers
 */

public interface CVOInterface {

   /**
    * @return the 'bevelenteller'
    */
   public long getBT();

   /**
    * @return the register with given index
    */
   public long getReg(int index);

   /**
    * @return the 'bevelenregister'
    */
   public long getBevReg();

   /**
    * @return the 'programmatoestandswoord'
    */
   public PTW getPTW();

}
