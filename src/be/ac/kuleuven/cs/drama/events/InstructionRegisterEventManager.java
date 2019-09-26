/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/InstructionRegisterEventManager.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * EventManager to handle instruction register events.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

// Should be replaced by non-static manager, provided by controller.

public class InstructionRegisterEventManager {

    private static List _listeners = new ArrayList();

    /**
     * Add a listener that likes to get notification of
     * instruction register updates
     */
    public static void addListener(InstructionRegisterListener irl) {
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
            ((InstructionRegisterListener) it.next()).handleInstructionRegisterEvent();
        }

    }

}
