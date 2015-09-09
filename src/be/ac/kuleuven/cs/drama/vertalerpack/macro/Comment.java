/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/Comment.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

/**
 * Utility class to strip comment from a line.
 *
 * @version 1.0.0 08/30/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

public final class Comment {

   private Comment() {}

   /**
    * @return the given string without comment
    */
   public static String stripComment(String string) {
	  int t = CommentStart(string);
      if (t != -1) {
         return string.substring(0, t);
      } else {
         return string;
      }

   }

   public static int CommentStart(String line) {
	   boolean valid = true;
	   for(int i = 0; i < line.length(); ++i) {
		   switch (line.charAt(i)) {
		   case '|':
			   if (valid)
				   return i;
			   continue;
		   case '\\':
			   ++i;
			   continue;
		   case '"':
			   valid = !valid;
		   }
	   }
	   return -1;
   }
   
}
