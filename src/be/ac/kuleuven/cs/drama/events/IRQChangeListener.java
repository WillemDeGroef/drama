/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/IRQChangeListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;
/**Interface IRQChangeListener definieert de methode die opgeroepen
 wordt bij een IRQChangeEvent
 */

public interface IRQChangeListener {

   /**
     reageer op een IRQChangeEvent
     
     @param evt het IRQChangeEvent waarop gereageerd wordt
    */
   public void IRQChange(IRQChangeEvent evt);
}
