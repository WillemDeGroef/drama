/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PTWChangeEvent.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.EventObject;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.*;

/**
   PTWChangeEvent geeft aan dat een programmatoestandswoord veranderd is
*/

public class PTWChangeEvent extends EventObject {
	private static final long serialVersionUID = 0L;

   private PTW _newPTW;

   /** Creëer een nieuw PTWChangeEvent
      @source the source of the event
      @param ptw het nieuwe PTW
   */
   public PTWChangeEvent(Object source, PTW ptw) {
      super(source);
      _newPTW = ptw;
   }

   /** Geef het veranderde PTW object
   @return het veranderde PTW object
   */
   public PTW getPTW() {
      return _newPTW;
   }

   /** Zet het veranderde PTW object
    @param PTW het veranderde PTW object
   */
   public void setPTW(PTW ptw) {
      _newPTW = ptw;
   }


}
