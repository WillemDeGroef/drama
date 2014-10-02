/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PortChangeEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;
/**EventAdapter voor veranderingen in de poorten van GenericBusDeviceCores
*
* @version 1.0 19 APR 1999
* @author Tom Vekemans
*/

public class PortChangeEventAdapter extends EventAdapter
   implements PortChangeListener {

   /**
    Initialiseer een nieuwe PortChangeEventAdapter  
    */
   public PortChangeEventAdapter() {
      super();

      setParams(PortChangeEvent.class);
   }

   /**
    Reageer op een PortChangeEvent
    @effect alle handlers van deze PortChangeEventAdapter
         worden verwittigd
    */
   public void PortChange(PortChangeEvent evt) {
      notifyHandlers(evt);
   }

}
