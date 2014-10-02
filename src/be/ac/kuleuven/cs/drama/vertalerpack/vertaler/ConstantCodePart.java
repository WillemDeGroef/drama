/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ConstantCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import java.util.StringTokenizer;


/**
 * ActiveCodePart representing the definition of a number of constants
 *
 * @version 1.0.0, 08/04/2000
 * @author Tom Schrijvers
 */

public class ConstantCodePart extends ActiveCodePart {

   // maximum value in a cell
   public static final long MAX_VALUE = CELL_SIZE - 1;
   // minimum value in a cell
   public static final long MIN_VALUE = - (CELL_SIZE / 2);

   // the line
   private final String _line;

   // the vertaler
   private final Vertaler2 _vertaler;

   /**
    * Initialize a new ConstantCodePart for the given line and vertaler.
    */
   public ConstantCodePart(String line, Vertaler2 vertaler) {

      _line = line;
      _vertaler = vertaler;

   }

   public int nbObjectLines() {
      return StringUtils.occurences(_line, ';') + 1;
   }

   public String[] getObjectLines(int objectln)
   throws AbnormalTerminationException {

      String[] result = new String[nbObjectLines()];

      StringTokenizer st = new StringTokenizer(_line, ";");

      for (int i = 0; i < result.length; i++) {

         if (! st.hasMoreTokens()) {
            throw new AbnormalTerminationException("fout aantal ;'s of constantes");
         }

         String constant = st.nextToken();
         result[i] = value(evaluateLong(constant, _vertaler));

      }

      return result;

   }

   /*
    * @return the value to be stored in the cell
    */
   private String value(long v)
   throws AbnormalTerminationException {
      if ((v < MIN_VALUE) || (v > MAX_VALUE)) {
         throw new AbnormalTerminationException("waarde niet binnen " + MIN_VALUE + " en " + MAX_VALUE + " :" + v);
      }

      if (v < 0) return Long.toString(v + CELL_SIZE);

      return StringUtils.prependChars(Long.toString(v), '0', 10);
   }

}
