/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/InternalMonitor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * Output device usable by other
 * machine components.
 *
 * @version 08/11/2000
 * @author  Tom Schrijvers
 */

public interface InternalMonitor {

	   /**
	    * write the given drama number to the output
	    */
	   public void writeLong(long value);

	   /**
	    * write the given drama number to the output
	    */
	   public void writeChar(char value);

   /**
    * write a newline to the output
    */
   public void newLine();

   /**
    * write an input request to the output
    */
   public void requestInput();

   /**
    * write an input confirmation to the output
    */
   public void confirmInput(long value);

   /**
    * release resources if necessary
    */
   public void close();
   
   /**
    * clear output screen
    */
   public void clear();
   
   /**
    * Removes the last character of the output
    */
   public void delLastChar();

}
