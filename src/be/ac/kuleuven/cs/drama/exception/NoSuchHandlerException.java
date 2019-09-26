/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/exception/NoSuchHandlerException.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.exception;

/**Een klasse om te melden dat een gevraagde EventHandler niet
 *bestaat
 *
 *@version 1.0 04 APR 1999
 *@author Tom Vekemans
 */

public class NoSuchHandlerException extends Exception {
    private static final long serialVersionUID = 0L;

    /**initialiseer een nieuwe NoSuchHandlerException
     */
    public NoSuchHandlerException() {
    }

}
