/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/CpuFrame.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import java.awt.*;
import javax.swing.*;

/**
 * Frame containing a CpuPanel.
 * All methods are delegated to the CpuPanel.
 *
 * @see be.ac.kuleuven.cs.drama.gui.visualisation.CpuPanel
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class CpuFrame

   extends JFrame {

   private final CpuPanel _panel;

   public CpuFrame() {

      super("CVO");
      setSize(240, 346);
      _panel = new CpuPanel();
      getContentPane().add(_panel);

   }

   public void setRegister(int idx, long value) {
      _panel.setRegister(idx, value);
   }

   public void setInstruction(long value) {
      _panel.setInstruction(value);
   }

   public void setCC(int value) {
      _panel.setCC(value);
   }

   public void setBT(long value) {
      _panel.setBT(value);
   }

}
