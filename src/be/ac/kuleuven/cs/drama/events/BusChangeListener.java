/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/BusChangeListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

/**Interface BusChangeListener definieert de methode die opgeroepen
 wordt bij een BusChangeEvent
 */

public interface BusChangeListener {

   /**
     reageer op een BusChangeEvent
     
     @param evt het BusChangeEvent waarop gereageerd wordt
    */
   public void BusChange(BusChangeEvent evt);
}
