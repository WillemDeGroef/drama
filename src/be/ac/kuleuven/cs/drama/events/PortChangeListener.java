/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PortChangeListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;
/**Interface PortChangeListener definieert de methode die opgeroepen
 wordt bij een PortChangeEvent
*/

public interface PortChangeListener {

   /**
      reageer op een PortChangeEvent
      
      @param evt het PortChangeEvent waarop gereageerd wordt
     */
   public void PortChange(PortChangeEvent evt);
}
