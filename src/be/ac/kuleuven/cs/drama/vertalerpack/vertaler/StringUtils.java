/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/StringUtils.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

public final class StringUtils {

   private StringUtils() {}


   /**
    * @return the string equal to the given string except for leading spaces
    */
   public static String trimLeftSpaces(String string) {
      char[] chars = string.toCharArray();
      int index = 0;

      while ((index < chars.length) && isSpace(chars[index])) {
         index++;
      }

      return string.substring(index);
   }

   public static String trimRightSpaces(String string) {
      if (string.length() == 0) return "";
      char[] chars = string.toCharArray();
      int index = chars.length - 1;

      while ((index >= 0) && (isSpace(chars[index]))) {
         index--;
      }

      return string.substring(0, index + 1);
   }

   public static String trimSpaces(String string) {
      return trimRightSpaces(trimLeftSpaces(string));
   }

   /**
    * @return a string equal to the given string except for any spaces
    */
   public static String stripSpaces(String line) {
      StringBuffer buffer = new StringBuffer();
      char[] chars = line.toCharArray();

      for (int i = 0; i < chars.length; i++) {
         if (! isSpace(chars[i])) {
            buffer.append(chars[i]);
         }

      }

      return buffer.toString();
   }


   /**
    * @return whether the given char is considered a space
    */
   public static boolean isSpace(char c) {
      //      return c == ' ' || c == '\t';
      return java.lang.Character.isWhitespace(c);
   }

   /**
    * @return the number of occurrences of the given char in the given string
    */
   public static int occurences(String s, char c) {
      char[] chars = s.toCharArray();
      int count = 0;

      for (int i = 0; i < chars.length; i++) {
         if (chars[i] == c) {
            count++;
         }

      }

      return count;
   }

   public static String prependChars(String s, char c, int len) {
      int count = len - s.length();
      StringBuffer buffer = new StringBuffer();

      while (count > 0) {
         buffer.append(c);
         count--;
      }

      buffer.append(s);
      return buffer.toString();
   }

   // TEST

   public static void main(String[] args) {
      String test = "  \tspacespacetab";
      System.out.println(trimLeftSpaces(test));
   }

}
