/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/BusChangeEvent.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.EventObject;

/**
 BusChangeEvent geeft aan dat de businhoud veranderd is
 */

public class BusChangeEvent extends EventObject {
    private static final long serialVersionUID = 0L;

    private long _adres;
    private long _data;
    private int _strobe;

    /** Cre�er een nieuw BusChangeEvent
     @source the source of the event
     @param adres de nieuwe waarde van de adresbus
     @param data de nieuwe waarde van de databus
     @param strobe de nieuwe waarde van de signaalbus
     */
    public BusChangeEvent(Object source, long adres, long data, int strobe) {
        super(source);
        _adres = adres;
        _data = data;
        _strobe = strobe;
    }

    /** Geef de inhoud van de databus
     @return de nieuwe inhoud van de databus
     */
    public long getData() {
        return _data;
    }

    /** Zet de databus op de gegeven waarde
     @param data de nieuwe waarde van de databus
     */
    public void setData(long data) {
        _data = data;
    }

    /** Geef de inhoud van de adresbus
     @return de nieuwe inhoud van de adresbus
     */
    public long getAdres() {
        return _adres;
    }

    /** Zet de adresbus op de gegeven waarde
     @param adres de nieuwe waarde van de adresbus
     */
    public void setAdres(long adres) {
        _adres = adres;
    }

    /** Geef de inhoud van de signaalbus
     @return de nieuwe inhoud van de signaalbus
     */
    public int getStrobe() {
        return _strobe;
    }

    /** Zet de signaalbus op de gegeven waarde
     @param strobe de nieuwe waarde van de signaalbus
     */
    public void setStrobe(int strobe) {
        _strobe = strobe;
    }

}
