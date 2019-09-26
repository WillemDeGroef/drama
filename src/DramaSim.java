/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/DramaSim.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
import be.ac.kuleuven.cs.drama.gui.GuiMain;
import java.awt.EventQueue;

/**
 * Temporary Main Project Main class.
 *
 * @version 1.0.0 09/04/2000
 * @author  Tom Schrijvers
 */

public class DramaSim {

   public static void main(String[] args) {
      // Run all GUI code in the AWT event handling thread to avoid races
      // between initialization code running in the main thread and event
      // handling code running in the AWT thread.
      EventQueue.invokeLater(() -> new GuiMain().start());
   }

}
