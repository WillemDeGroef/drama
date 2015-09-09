/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/exception/AbnormalTerminationException.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.exception;

/* Een klasse om voorgedefinieerde fouten te signaleren naar de omgeving
 *
 * @version 1.1 06_APR-1999
 * @author Tom Vekemans
 */

public class AbnormalTerminationException extends Exception {
	private static final long serialVersionUID = 0L;


   private boolean _hasLineNo = false;

   /**
   * Initialiseer een nieuwe AbnormalTerminationException met een gegeven
   * foutboodschap.
   *
   * @param s de foutboodschap horend bij deze AbnormalTerminationException
   */
   public AbnormalTerminationException(String s) {
      super(s);
   }

   /**
   * Initialiseer een nieuwe AbnormalTerminationException met een gegeven
   * foutboodschap.
   *
   * @param lineno de regelnummer van de fout
   * @param s de foutboodschap horend bij deze AbnormalTerminationException
   */
   public AbnormalTerminationException(int lineno, String s) {
      super(" ### " + lineno + " ### " + s);
      _hasLineNo = true;
   }

   /**
   * Initialiseer een nieuwe AbnormalTerminationException met een gegeven
   * foutboodschap.
   *
   * @param lineno de regelnummer van de fout in de pre file
   * @param srcline de regelnummer van de fout in de source file
   * @param s de foutboodschap horend bij deze AbnormalTerminationException
   */
   public AbnormalTerminationException(int lineno, int srcline, String s) {
      super(" ### " + ((srcline != lineno) ? srcline + " (pre:" + lineno + ")" : srcline + "") + " ### " + s);
      _hasLineNo = true;
   }

   /**
    * Hack so we can catch exceptions without lineno and rethrow the exception
    * with a lineno.
    *
    * @return true if this exception has a lineno, false otherwise.
    */
   public boolean hasLineNo() {
      return _hasLineNo;
   }

}
