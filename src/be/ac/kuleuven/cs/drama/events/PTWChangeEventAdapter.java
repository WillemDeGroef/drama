/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PTWChangeEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**EventAdapter voor veranderingen in PTW's (programmatoestandswoord)
 *
 * @version 1.0 04 APR 1999
 * @author Tom Vekemans
 */

public class PTWChangeEventAdapter extends EventAdapter
        implements PTWChangeListener {

    /**
     Initialiseer een nieuwe PTWChangeEventAdapter
     */
    public PTWChangeEventAdapter() {
        super();

        setParams(PTWChangeEvent.class);
    }

    /**
     Reageer op een PTWChangeEvent
     @effect alle handlers van deze PTWChangeEventAdapter
     worden verwittigd
     */
    public void PTWChange(PTWChangeEvent evt) {
        notifyHandlers(evt);
    }

}
