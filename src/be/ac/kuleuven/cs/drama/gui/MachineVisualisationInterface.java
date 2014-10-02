/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/MachineVisualisationInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;

import javax.swing.JFrame;

/**
 * Interface for machine visualisation managers.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public interface MachineVisualisationInterface {

   // all methods as defined in gui.MachineImage

   public void setRAPogTextField(int index, long value);

   public void setRAPtTextField(int index, long value);

   public void setBusAdresText(long value);

   public void setBusDataText(long value);

   public void setBusSignaalText(int strobe);

   public void setIRQ(int irq, boolean on)
   throws AbnormalTerminationException ;

   public void setDevices(int number, String[] names);

   public void setDeviceIRQ(int deviceNumber, boolean on);

   public void setEventsEnabled(boolean b);

   public void setPTWPartEnabled(boolean ptwOn);

   public void setIRQPartEnabled(boolean irqOn);

   public void halt();

   public void cont();

   public void setPTW(PTW ptw);

   public void setReg(int idx, long value);

   public void setBevReg(long value);

   public JFrame getInternalRepresentationFrame();

   public JFrame getCVOFrame();

   public void updateMemory(int address, long value);

   public void setLabels(String[] names, int[] addresses);

}
