/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/DevicesPanel.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that visualizes the state of the devices.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class DevicesPanel extends JPanel {
	private static final long serialVersionUID = 0L;


   private Device[] _devices;

   /**
    * Initialize
    */
   public DevicesPanel() {
      super();
      setOpaque(true);
      setBackground(Color.white);
   }

   public void setDevices(int number, String[] names) {
      removeAll();
      setLayout(new GridLayout(number, 1));

      _devices = new Device[number];

      for (int i = 0; i < number; i++) {
         _devices[i] = new Device(names[i]);
         add(_devices[i]);
      }

   }

   public void setRAPogTextField(int index, long value) {
      _devices[index].setPog(value);
   }

   public void setRAPtTextField(int index, long value) {
      _devices[index].setPt(value);
   }

   public void setDeviceIRQ(int index, boolean on) {
      _devices[index].setIRQ(on);
   }

   /*
    * Visualisation of one device.
    */

   private class Device
      extends JPanel {
		private static final long serialVersionUID = 0L;

      private final JLabel _pt;
      private final JLabel _pog;
      private final Led _led;

      public Device(String name) {
         super();

         this.setOpaque(true);
         this.setBackground(Color.gray);
         this.setLayout(new BorderLayout());

         JPanel leftPanel = new JPanel();
         GridLayout leftLayout = new GridLayout(3, 1);
         leftLayout.setVgap(3);
         leftPanel.setLayout(leftLayout);

         JPanel rightPanel = new JPanel();
         GridLayout rightLayout = new GridLayout(3, 1);
         rightLayout.setVgap(3);
         rightPanel.setLayout(rightLayout);

         JLabel ptLabel = new JLabel("Pt");
         ptLabel.setForeground(Color.yellow);
         _pt = new JLabel();
         _pt.setOpaque(true);
         _pt.setBackground(Color.black);
         _pt.setForeground(Color.white);
         _pt.setHorizontalAlignment(JLabel.CENTER);

         leftPanel.add(ptLabel);
         rightPanel.add(_pt);

         JLabel pogLabel = new JLabel("Pog");
         pogLabel.setForeground(Color.yellow);
         _pog = new JLabel();
         _pog.setOpaque(true);
         _pog.setBackground(Color.black);
         _pog.setForeground(Color.white);
         _pog.setHorizontalAlignment(JLabel.CENTER);

         leftPanel.add(pogLabel);
         rightPanel.add(_pog);

         _led = new Led();
         leftPanel.add(_led);

         JLabel nameLabel = new JLabel(name);
         nameLabel.setForeground(Color.red);
         nameLabel.setHorizontalAlignment(JLabel.CENTER);
         rightPanel.add(nameLabel);

         this.add(leftPanel, BorderLayout.WEST);
         this.add(rightPanel, BorderLayout.CENTER);

      }

      public void setPog(long value) {
         _pog.setText(Long.toString(value));
      }

      public void setPt(long value) {
         _pt.setText(Long.toString(value));
      }

      public void setIRQ(boolean on) {
         _led.setState(on);
      }

   }

}
