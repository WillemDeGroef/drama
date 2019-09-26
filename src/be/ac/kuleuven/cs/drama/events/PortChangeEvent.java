/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PortChangeEvent.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.EventObject;

/**
 PortChangeEvent geeft aan dat de inhoud van een poort gewijzigd is
 */

public class PortChangeEvent extends EventObject {
    private static final long serialVersionUID = 0L;

    private int _adres;
    private long _value;
    private int _soort;
    public final static int POG = 1;
    public final static int PT = 2;

    /** Cre�er een nieuw PortChangeEvent
     @source the source of the event
     @param adres het adres van het gewijzigde poort
     @param value de nieuwe waarde van de poort
     @param sort het soort poort (Pog of Pt)
     */
    public PortChangeEvent(Object source, int adres, long value, int sort) {
        super(source);
        _adres = adres;
        _value = value;
        _soort = sort;
    }

    /** Geef het adres van de gewijzigde poort
     @return het adres van de gewijzigde poort
     */
    public int getPortAdres() {
        return _adres;
    }

    /** Zet het door dit object aangegeven adres op de gegeven waarde
     @param adres het nieuwe aan te geven adres
     */
    public void setPortAdres(int adres) {
        _adres = adres;
    }


    /** Geef de nieuwe waarde van de poort
     @return de nieuwe waarde van de poort
     */
    public long getValue() {
        return _value;
    }

    /** Zet de nieuwe waarde van het PortChangeEvent op
     de gegeven waarde
     @param value de nieuwe waarde van het PortChangeEvent
     */
    public void setValue(long value) {
        _value = value;
    }

    /** Geef het nieuwe type van de poort
     @return het nieuwe type van de poort
     */
    public int getSort() {
        return _soort;
    }

    /** Zet het nieuwe type van de poort van het PortChangeEvent
     op de gegeven waarde
     @param het nieuwe type van de poort
     */
    public void setSort(int sort) {
        _soort = sort;
    }


}
