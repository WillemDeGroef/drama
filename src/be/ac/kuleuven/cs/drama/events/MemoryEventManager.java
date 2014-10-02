/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/MemoryEventManager.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manager of memory cell update events
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

// Should be replaced by non-static manager, provided by controller.

public class MemoryEventManager {

   private static List _listeners = new ArrayList();

   /**
    * Add a listener.
    */
   public static void addListener(MemoryListener irl) {
      _listeners.add(irl);
   }

   /**
    * Fire an update event.
    * @param address the updated cell
    * @param value the new value
    */
   public static void fireEvent(int address, long value) {
      notifyListeners(address, value);
   }

   /*
    * notify all registred listeners
    */
   private static void notifyListeners(int address, long value) {
      Iterator it = _listeners.iterator();

      while (it.hasNext()) {
         ((MemoryListener) it.next()).handleMemoryEvent(address, value);
      }

   }

}
