/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/MachineVisualisation.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import javax.swing.JFrame;

import be.ac.kuleuven.cs.drama.gui.GUIController;
import be.ac.kuleuven.cs.drama.gui.MachineVisualisationInterface;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;

/**
 * Magager of the visualisation of the machine.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class MachineVisualisation
        implements MachineVisualisationInterface {

    private final GUIController _controller;

    private InternalRepresentationFrame _internalRepresentationFrame;

    private CpuFrame _cpuFrame;

    /**
     * Initialize for the given controller
     */
    public MachineVisualisation(GUIController controller) {
        _controller = controller;
        buildInternalRepresentationFrame();
        buildCpuFrame();


    }

    public void setLabels(String[] names, int[] addresses) {
        _internalRepresentationFrame.setLabels(names, addresses);
    }

    public JFrame getInternalRepresentationFrame() {
        return _internalRepresentationFrame;
    }

    public JFrame getCVOFrame() {
        return _cpuFrame;
    }

    private void buildInternalRepresentationFrame() {
        _internalRepresentationFrame = new InternalRepresentationFrame(this);
    }

    private void buildCpuFrame() {

        _cpuFrame = new CpuFrame();

    }

    public void setReg(int idx, long value) {
        //System.out.println("setReg");
        _internalRepresentationFrame.setRegister(idx, value);
        _cpuFrame.setRegister(idx, value);
    }

    public void setBevReg(long value) {
        //System.out.println("setBevReg: " + value);
        _internalRepresentationFrame.setInstruction(value);
        _cpuFrame.setInstruction(value);
    }

    public void setPTW(PTW ptw) {
        _internalRepresentationFrame.setCC(ptw.getCC());
        _internalRepresentationFrame.setBT(ptw.getBT());
        _internalRepresentationFrame.setPTW(ptw);
        _cpuFrame.setCC(ptw.getCC());
        _cpuFrame.setBT(ptw.getBT());
    }

    /**
     * @return the value of the given memory cell
     */
    public long getRAMCell(int address) {
        return _controller.getRAMCell(address);
    }

    public void updateMemory(int address, long value) {
        _internalRepresentationFrame.updateMemory(address, value);
    }

    public void setRAPogTextField(int index, long value) {
        _internalRepresentationFrame.setRAPogTextField(index, value);
    }

    public void setRAPtTextField(int index, long value) {
        _internalRepresentationFrame.setRAPtTextField(index, value);
    }

    public void setDevices(int number, String[] names) {
        _internalRepresentationFrame.setDevices(number, names);
    }

    public void setDeviceIRQ(int deviceNumber, boolean on) {
        _internalRepresentationFrame.setDeviceIRQ(deviceNumber, on);
    }

    public void setBusAdresText(long value) {
        _internalRepresentationFrame.setBusAdresText(value);
    }

    public void setBusDataText(long value) {
        _internalRepresentationFrame.setBusDataText(value);
    }

    public void setBusSignaalText(int strobe) {
        _internalRepresentationFrame.setBusSignaalText(strobe);
    }

    public void setIRQ(int irq, boolean on) {
        // _internalRepresentationFrame.setIRQ(irq, on);
    }


    public int getRegisterValue(int index) {
        String t = _cpuFrame.getPanel().getRegisters()[index].getText();
        int value = -1;
        try {
            value = Integer.parseInt(t);
        } catch (Exception e) {
        }

        return value;
    }


    public void setEventsEnabled(boolean b) {
    }


    public void setPTWPartEnabled(boolean ptwOn) {
        _internalRepresentationFrame.setPTWPartEnabled(ptwOn);
    }

    public void setIRQPartEnabled(boolean irqOn) {
        _internalRepresentationFrame.setIRQPartEnabled(irqOn);
    }

    public void halt() {
    }


    public void cont() {
    }


}
