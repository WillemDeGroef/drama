/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/Led.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import be.ac.kuleuven.cs.drama.gui.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * Led that is either on or off.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

// relocate to gui.util

public class Led

   extends JLabel {

   private static final Icon IRQon = Settings.getIcon(Settings.IRQ_ON_ICON);
   private static final Icon IRQoff = Settings.getIcon(Settings.IRQ_OFF_ICON);

   /**
    * Initialize.
    * Default off
    */
   public Led() {
      super();
      setState(false);
   }

   /**
    * set on or off
    */
   public void setState(boolean on) {

      if (on) {
         setIcon(IRQon);
      } else {
         setIcon(IRQoff);
      }

   }

}
