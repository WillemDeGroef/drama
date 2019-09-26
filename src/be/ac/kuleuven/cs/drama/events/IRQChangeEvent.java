/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/IRQChangeEvent.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.EventObject;

/**
 IRQChangeEvent geeft aan dat een Inerrupt aanvraag veranderd is
 */

public class IRQChangeEvent extends EventObject {
    private static final long serialVersionUID = 0L;

    private int _index;
    private boolean _on;

    /** Cre�er een nieuw IRQChangeEvent
     @source the source of the event
     @param index het nummer van het gewijzigde IRQ
     @param on de nieuwe waarde van het IRQ
     */
    public IRQChangeEvent(Object source, int index, boolean on) {
        super(source);
        _index = index;
        _on = on;
    }

    /** Geef de index van het veranderde IRQ
     @return de index van het gewijzigde IRQ
     */
    public int getIRQIndex() {
        return _index;
    }

    /** Zet de index op de gegeven waarde
     @param idx de index van het gewijzigde IRQ
     */
    public void setIRQIndex(int idx) {
        _index = idx;
    }


    /** Geef de nieuwe waarde van het IRQ
     @return de nieuwe waarde van het IRQ
     */
    public boolean getOn() {
        return _on;
    }

    /** Zet de nieuwe waarde van het IRQChangeEvent op
     de gegeven waarde
     @param value de nieuwe waarde van het IRQ
     */
    public void setOn(boolean on) {
        _on = on;
    }

}
