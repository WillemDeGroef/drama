/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/RegChangeEvent.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.EventObject;

/**
   RegChangeEvent geeft aan dat een Register (R0...R9 of het bevelenregister) 
   veranderd is
*/

public class RegChangeEvent extends EventObject {
	private static final long serialVersionUID = 0L;

   private int _index;
   private long _value;

   /** Creëer een nieuw RegChangeEvent
      @source the source of the event
      @param index het nummer van het gewijzigde register
      @param value de nieuwe waarde van het register
   */
   public RegChangeEvent(Object source, int index, long value) {
      super(source);
      _index = index;
      _value = value;
   }

   /** Geef de index van het veranderde register
      @return de index van het gewijzigde register
   */
   public int getRegIndex() {
      return _index;
   }

   /** Zet de index op de gegeven waarde
      @param idx de index van het gewijzigde register
   */
   public void setRegIndex(int idx) {
      _index = idx;
   }


   /** Geef de nieuwe waarde van het register
      @return de nieuwe waarde van het register
   */
   public long getValue() {
      return _value;
   }

   /** Zet de nieuwe waarde van het RegChangeEvent op
    de gegeven waarde
    @param value de nieuwe waarde van het register
   */
   public void setValue(long value) {
      _value = value;
   }

}
