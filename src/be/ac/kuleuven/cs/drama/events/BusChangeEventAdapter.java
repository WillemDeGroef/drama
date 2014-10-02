/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/BusChangeEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;
/**EventAdapter voor veranderingen in de bus
*
* @version 1.0 21 APR 1999
* @author Tom Vekemans
*/

public class BusChangeEventAdapter extends EventAdapter
   implements BusChangeListener {

   /**
      Initialiseer een nieuwe BusChangeEventAdapter
    */
   public BusChangeEventAdapter() {
      super();

      setParams(BusChangeEvent.class);
   }

   /**
     Reageer op een BusChangeEvent (door de EventHandlers te 
     verwittigen)
     
     @param evt het BusChangeEvent waarop deze methode reageert
     @see EventAdapter
    */
   public void BusChange(BusChangeEvent evt) {
      notifyHandlers(evt);
   }

}
