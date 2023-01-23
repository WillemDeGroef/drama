/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/Name.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

/**
 * Utility class to verify names.
 *
 * @version 1.0.0 08/30/2000
 * @author  Tom Schrijvers
 */

public final class Name {

   private Name() {}

   /*
    * @return wether the given string is an acceptable name
    * (move to utility class)
    */
   public static boolean isName(String string) {
      if (string.length() == 0) {
         return false;
      }

      if (! isFirstCharOfName(string.charAt(0))) {
         return false;
      }

      for (int i = 1; i < string.length() - 1; i++) {
         if (! isCharOfName(string.charAt(i))) {
            return false;
         }

      }

      return true;
   }

   /*
    * isName helper function
    */
   private static boolean isFirstCharOfName(char c) {
      return Character.isLetter(c);
   }

   /*
    * isName helper function
    */
   private static boolean isCharOfName(char c) {
      return Character.isLetterOrDigit(c) || c == '_';
   }


}
