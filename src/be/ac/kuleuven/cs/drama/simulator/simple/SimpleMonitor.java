/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleMonitor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import java.awt.Color;
import java.awt.Font;

import be.ac.kuleuven.cs.drama.simulator.MonitorInterface;
import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;

/**
 * Simple implementation of an output device.
 *
 * @version 1.0.0 08/09/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

public class SimpleMonitor

         extends MonitorInterface

   implements InternalMonitor {
	private static final long serialVersionUID = 0L;


   public SimpleMonitor() {
      super("Monitor online.\n \n", 40 , 20 /*, TextArea.SCROLLBARS_VERTICAL_ONLY */);
      setBackground(Color.black);
      setForeground(Color.yellow);
      setFont(new Font("courrier", 1, 14));
      setEditable(false);
      setLineWrap(true);
   }

   // INTERNALMONITOR INTERFACE

   public void writeLong(long value) {
      append(Long.toString(NumberFormat.toJavaNumber(value)));
      append("\t");

   }

   public void writeChar(char value) {
      append(Character.toString(value));
   }

   public void newLine() {
      append("\n");
   }

   public void requestInput() {
      append(">");
   }

   public void confirmInput(long value) {
      writeLong(value);
      newLine();
   }

   public void close() {}








   public void append(String text) {
      super.append(text);
      setCaretPosition(getText().length());
   }
   
   public void clear() {
	   super.setText("Monitor online.\n\n");
   }

   public void delLastChar() {
	   super.setText(getText().substring(0, getText().length() - 1));
   }

}
