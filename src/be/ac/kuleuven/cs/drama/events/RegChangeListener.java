/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/RegChangeListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;
/**Interface RegChangeListener definieert de methode die opgeroepen
 wordt bij een RegChangeEvent
*/

public interface RegChangeListener {

   /**
     reageer op een RegChangeEvent
     
     @param evt het RegChangeEvent waarop gereageerd wordt
   */
   public void RegChange(RegChangeEvent evt);
}
