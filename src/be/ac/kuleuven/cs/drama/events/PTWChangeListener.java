/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/PTWChangeListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.events;

/**Interface PTWChangeListener definieert de methode die opgeroepen
 wordt bij een PTWChangeEvent
 */

public interface PTWChangeListener {

    /**
     reageer op een PTWChangeEvent

     @param evt het PTWChangeEvent waarop gereageerd wordt
     */
    void PTWChange(PTWChangeEvent evt);
}
