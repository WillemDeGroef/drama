/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/InputRequestEventManager.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
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
 * EventManager to handle input request events
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

// Should be replaced by non-static manager, provided by controller.

public class InputRequestEventManager {

   private static List _listeners = new ArrayList();

   /**
    * Add a listener that likes to get notification of
    * input requests.
    */
   public static void addListener(InputRequestListener irl) {
      _listeners.add(irl);
   }

   /**
    * Fire an instruction register update event
    */
   public static void fireEvent() {
      notifyListeners();
   }

   /*
    * Notify all listeners of an instruction register update.
    */
   private static void notifyListeners() {
      Iterator it = _listeners.iterator();

      while (it.hasNext()) {
         ((InputRequestListener) it.next()).handleInputRequest();
      }

   }

}
