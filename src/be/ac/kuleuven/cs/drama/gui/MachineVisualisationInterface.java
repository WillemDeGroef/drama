/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/MachineVisualisationInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;

import javax.swing.JFrame;

/**
 * Interface for machine visualisation managers.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public interface MachineVisualisationInterface {

    // all methods as defined in gui.MachineImage

    void setRAPogTextField(int index, long value);

    void setRAPtTextField(int index, long value);

    void setBusAdresText(long value);

    void setBusDataText(long value);

    void setBusSignaalText(int strobe);

    void setIRQ(int irq, boolean on)
            throws AbnormalTerminationException;

    void setDevices(int number, String[] names);

    void setDeviceIRQ(int deviceNumber, boolean on);

    void setEventsEnabled(boolean b);

    void setPTWPartEnabled(boolean ptwOn);

    void setIRQPartEnabled(boolean irqOn);

    void halt();

    void cont();

    void setPTW(PTW ptw);

    void setReg(int idx, long value);

    void setBevReg(long value);

    JFrame getInternalRepresentationFrame();

    JFrame getCVOFrame();

    void updateMemory(int address, long value);

    void setLabels(String[] names, int[] addresses);

}
