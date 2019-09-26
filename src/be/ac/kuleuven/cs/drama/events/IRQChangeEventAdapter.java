/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/IRQChangeEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**EventAdapter voor veranderingen in de IRQLine (het aan-of uitgaan
 * van PO-aanvragen
 *
 * @version 1.0 04 APR 1999
 * @author Tom Vekemans
 */

public class IRQChangeEventAdapter extends EventAdapter
        implements IRQChangeListener {

    /**
     Initialiseer een nieuwe IRQChangeEventAdapter 
     */
    public IRQChangeEventAdapter() {
        super();

        setParams(IRQChangeEvent.class);
    }

    /**
     Reageer op een IRQChangeEvent (door de EventHandlers te 
     verwittigen)

     @param evt het IRQChangeEvent waarop deze methode reageert
     @see EventAdapter
     */
    public void IRQChange(IRQChangeEvent evt) {
        notifyHandlers(evt);
    }

}
