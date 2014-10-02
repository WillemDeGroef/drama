/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/SourceLine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;
import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * A SourceLine is a line from a drama source file
 * that provides several methods for generating the decimal
 * code lines.
 *
 * Label and comment are handled in this class.
 *
 * @version 1.0.0 08/03/2000
 * @author Tom Schrijvers
 */

public class SourceLine {

   // the source line without label and comment
   private String _line;
   // the label if any
   private String _label = null;
   // the comment if any
   private String _comment = null;
   // the part of the code without label and comment
   private ActiveCodePart _codePart;

   /**
    * Initialize a new SourceLine for the given vertaler and the given
    * drama source line.
    */
   public SourceLine(String line, Vertaler2 vertaler) {
      _line = line;

      splitOffLabel();
      splitOffComment();

      _codePart = ActiveCodePartFactory.instance().getActiveCodePart(_line, vertaler);

   }


   // LABEL RELATED METHODS

   /**
    * @return wether this SourceLine contains a label
    */
   public boolean hasLabel() {
      return _label != null;
   }

   /**
    *  require hasLabel()
    * @return  the label of this SourceLine
    */
   public String getLabel() {
      return _label;
   }

   /*
    * Split off the label if any.
    */
   private void splitOffLabel() {

      _line = StringUtils.trimLeftSpaces(_line);

      char[] chars = _line.toCharArray();

      int index = 0;

      if (chars.length < 2) {
         return ;
      }

      if (! isFirstLabelCharacter(chars[index])) {
         return ;
      }

      while ((index < chars.length) && isLabelCharacter(chars[index])) {
         index++;
      }

      if (index >= chars.length) {
         return ;
      }

      if (chars[index] == ':') {
         _label = _line.substring(0, index);
         _line = _line.substring(index + 1);
      }

   }

   /**
    * return wether c is acceptable as the first character of a label
    */
   public static boolean isFirstLabelCharacter(char c) {
      return Character.isLetter(c) || c == '_';
   }

   /**
    * @return wether c is acceptable as a character of a label
    */
   public static boolean isLabelCharacter(char c) {
      return isFirstLabelCharacter(c) || Character.isDigit(c) || c == '.';
   }

   // COMMENT RELATED CODE

   /**
    * @return wether this SourceLine has a comment
    */
   public boolean hasComment() {
      return _comment != null;
   }

   /**
    * return the comment
    */
   public String getComment() {
      return _comment;
   }


   /*
    * remove the comment, if any, from  _line
    */
   private void splitOffComment() {
      int index = _line.indexOf('|');

      if (index != -1) {
         _comment = _line.substring(index + 1);
         _line = _line.substring(0, index);
      }

   }

   // OBJECT CODE RELATED METHODS

   /**
    * @return the number of decimal code lines
    */
   public int nbObjectLines()
   throws AbnormalTerminationException {
      return _codePart.nbObjectLines();
   }

   /**
    * @return an array of decimal code lines
    */
   public String[] getObjectLines(int objectln)
   throws AbnormalTerminationException {
      return _codePart.getObjectLines(objectln);
   }

   /**
    * @return wether this is the last line of the source program
    */
   public boolean isLastProgramLine() {
      return _codePart.isLastProgramLine();
   }

   // TESTS

   public static void main(String[] args) {
      if (true) {
         testLabelDetection("label: blabla");
         testLabelDetection("_label: blabla");
         testLabelDetection("1label: blabla");
         testLabelDetection(": blabla");
         testLabelDetection("blabla");
         testLabelDetection("");
         testLabelDetection("|label: blabla");
         testLabelDetection("la_be1l: blabla");
         testLabelDetection("\tlabel: blabla");
         testLabelDetection("  label: blabla");
         testLabelDetection("la+bel: blabla");
      }

      if (true) {

         testCommentDetection("");
         testCommentDetection("blablabla");
         testCommentDetection(" \t |blablabla");
         testCommentDetection("|||");
         testCommentDetection("|");
         testCommentDetection("HIA BIG LOL | No Comment");
      }

   }

   private static void testLabelDetection(String line) {
      SourceLine sl = new SourceLine(line, null);
      System.out.println(line);
      System.out.println("hasLabel: " + sl.hasLabel());

      if (sl.hasLabel()) System.out.println("label: " + sl.getLabel());

   }

   private static void testCommentDetection(String line) {
      SourceLine sl = new SourceLine(line, null);
      System.out.println(line);
      System.out.println("hasComment: " + sl.hasComment());

      if (sl.hasComment()) System.out.println("comment: " + sl.getComment());
   }

}
