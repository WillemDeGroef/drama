/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/basis/NumberFormat.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.basis;

/**
 * Class with utility methodes to
 * transform numbers between java
 * and drama representation.
 *
 * @version 1.0.0 08/11/2000
 * @author  Tom Schrijvers
 */

public final class NumberFormat {

   public static final long MODULO = 10000000000L;

   public static final long MAX_POSITIVE = 4999999999L;

   public static final long MIN_NEGATIVE = - 5000000000L;

   /**
    * Do nothing if the given java number is representable
    * as a drama number. Throw a NumberFormatException otherwise.
    */
   public static void checkDramaRepresentable(long value) {
      if (value < MIN_NEGATIVE) {
         throw new NumberFormatException("Waarde " + value + "ongeldig want kleiner dan " + MIN_NEGATIVE);
      }

      if (value > MAX_POSITIVE) {
         throw new NumberFormatException("Waarde " + value + "ongeldig want groter dan " + MAX_POSITIVE);
      }

   }

   public static boolean isDramaNumber(long number) {
      return number >= 0 && number < MODULO;
   }

   /**
    * @return the java number corresponding to the given drama number
    */
   public static long toJavaNumber(long dramaNumber) {
      if (dramaNumber <= MAX_POSITIVE) {
         return dramaNumber;
      } else {
         return dramaNumber - MODULO;
      }

   }

   /**
    * @return the drama number corresponding to the given java number
    * exception NumberFormatException the number has no drama representation
    */
   public static long toSafeDramaNumber(long javaNumber) {
      checkDramaRepresentable(javaNumber);
      return toDramaNumber(javaNumber);
   }

   /**
    * @return the drama number corresponding to the given java number
    */
   public static long toDramaNumber(long javaNumber) {
      long dramaNumber = javaNumber % MODULO;

      if (dramaNumber < 0) {
         dramaNumber += MODULO;
      }

      return dramaNumber;
   }

   // STATIC METHODS ONLY
   private NumberFormat() {}


}
