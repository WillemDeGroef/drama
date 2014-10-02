/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/MonitorInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator;

//import java.awt.TextArea;
import javax.swing.JTextArea;

/**
 * A MonitorInterface has to be implemented 
 * by monitors for the GUIController
 *
 * @version 1.0.0 08/09/2000
 * @author  Tom Schrijvers
 */

public abstract class MonitorInterface
   extends JTextArea {

   /**
    * Initialisation of the TextArea
    */
   protected MonitorInterface(String text, int rows, int columns /*, int scrollbars*/) {
      super(text, rows, columns /*, scrollbars */);
   }

   /**
    * Has to be implemented to run 
    * in separate thread.
    * 
    * IMPOSED BY DramaMachine
    */
   public void start() {}








}
