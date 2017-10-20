/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/CpuPanel.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * Panel that visualizes the registers, the instruction register
 * the instruction counter and the condition code.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class CpuPanel

   extends JPanel {
	private static final long serialVersionUID = 0L;


   private JLabel[] _registers;
   private JLabel _instruction;
   private JLabel _bt;
   private JLabel _cc;

   /**
    * Initialize
    */
   public CpuPanel() {

      setLayout(new BorderLayout());

      add(createCenterPanel(), BorderLayout.CENTER);
      add(createNorthPanel(), BorderLayout.NORTH);

   }

   /*
    * call once in constructor
    */
   private JPanel createCenterPanel() {

      JPanel panel = new JPanel();

      panel.setOpaque(true);
      panel.setBackground(Color.black);

      GridLayout layout = new GridLayout(10, 2);

      layout.setHgap(3);
      layout.setVgap(3);

      panel.setLayout(layout);

      _registers = new JLabel[10];

      for (int i = 0; i < 10; i++) {

         JLabel label = new JLabel("R" + i);
         label.setHorizontalAlignment(JLabel.CENTER);
         //label.setOpaque(true);
         label.setForeground(Color.white);
         //label.setBackground(Color.gray);

         panel.add(label);

         _registers[i] = new JLabel("0");
         _registers[i].setHorizontalAlignment(JLabel.CENTER);
         _registers[i].setOpaque(true);
         _registers[i].setForeground(Color.white);
         _registers[i].setBackground(Color.gray);

         panel.add(_registers[i]);

      }

      return panel;
   }

   /*
    * call once in constructor
    */
   private JPanel createNorthPanel() {
      JPanel panel = new JPanel();

      panel.setOpaque(true);
      panel.setBackground(Color.black);

      GridLayout layout = new GridLayout(3, 2);

      layout.setVgap(1);
      layout.setHgap(3);

      panel.setLayout(layout);

      JLabel instructionLabel = new JLabel("bevelregister");
      instructionLabel.setOpaque(true);
      instructionLabel.setForeground(Color.white);
      instructionLabel.setBackground(Color.blue);

      _instruction = new JLabel("");
      _instruction.setOpaque(true);
      _instruction.setForeground(Color.white);
      _instruction.setBackground(Color.blue);
      _instruction.setHorizontalAlignment(JLabel.CENTER);

      panel.add(instructionLabel);
      panel.add(_instruction);

      JLabel btLabel = new JLabel("bevelenteller");
      btLabel.setOpaque(true);
      btLabel.setForeground(Color.white);
      btLabel.setBackground(Color.blue);

      _bt = new JLabel("");
      _bt.setOpaque(true);
      _bt.setForeground(Color.white);
      _bt.setBackground(Color.blue);
      _bt.setHorizontalAlignment(JLabel.CENTER);
      panel.add(btLabel);
      panel.add(_bt);


      JLabel ccLabel = new JLabel("conditiecode");
      ccLabel.setOpaque(true);
      ccLabel.setForeground(Color.white);
      ccLabel.setBackground(Color.blue);

      _cc = new JLabel("");
      _cc.setOpaque(true);
      _cc.setForeground(Color.white);
      _cc.setBackground(Color.blue);
      _cc.setHorizontalAlignment(JLabel.CENTER);
      panel.add(ccLabel);
      panel.add(_cc);

      return panel;

   }

   public void setRegister(int idx, long value) {
      _registers[idx].setText(value(value));
   }

   public void setInstruction(long value) {
      _instruction.setText(value(value));
   }

   public void setCC(int value) {
      _cc.setText(getCCString(value));
   }

   private String getCCString(int value) {
      if (value == 0) return "NUL";

      if (value == 1) return "POS";

      if (value == 2) return "NEG";

      return "ERROR";
   }

   public void setBT(long value) {
      _bt.setText(address((int) value));
   }

   private String address(int adr) {
      StringBuffer buffer = new StringBuffer();
      String part = Integer.toString(adr);

      for (int i = 0; i < 4 - part.length(); i++) {
         buffer.append('0');
      }

      buffer.append(part);
      return buffer.toString();
   }


   private String value(long adr) {
      StringBuffer buffer = new StringBuffer();
      String part = Long.toString(adr);

      for (int i = 0; i < 10 - part.length(); i++) {
         buffer.append('0');
      }

      buffer.append(part);
      return buffer.toString();
   }

   public JLabel[] getRegisters() {
	   return _registers;
   }


}
