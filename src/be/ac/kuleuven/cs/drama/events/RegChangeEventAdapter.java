/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/RegChangeEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**EventAdapter voor veranderingen in de registers van CVO's
 * @version 1.0 04 APR 1999
 * @author Tom Vekemans
 */

public class RegChangeEventAdapter extends EventAdapter
        implements RegChangeListener {

    /**
     Initialiseer een nieuwe RegChangeEventAdapter  
     */
    public RegChangeEventAdapter() {
        super();

        setParams(RegChangeEvent.class);
    }

    /**
     Reageer op een RegChangeEvent
     @effect alle handlers van deze RegChangeEventAdapter
     worden verwittigd
     */
    public void RegChange(RegChangeEvent evt) {
        notifyHandlers(evt);
    }

}
