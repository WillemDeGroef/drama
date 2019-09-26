/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/InternalRepresentationFrame.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import be.ac.kuleuven.cs.drama.gui.Settings;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.DramaPTW;

import java.awt.*;

import javax.swing.*;

/**
 * Visualisation of the internal machine.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class InternalRepresentationFrame

        extends JFrame {
    private static final long serialVersionUID = 0L;

    private final CpuPanel _leftPanel;

    private final DynamicTable _memoryPanel;

    private final DevicesPanel _devicesPanel;

    private final JPanel _PTWAndBusPanel;

    private final JPanel _secondPanel;

    private MachineVisualisation _machineVisualisation;

    private int _GBE = 0;

    /**
     * Initialize for the given MachineVisualisation manager.
     */
    public InternalRepresentationFrame(MachineVisualisation mv) {

        super("Interne machine");

        _machineVisualisation = mv;

        GridLayout layout = new GridLayout(1, 2);
        layout.setHgap(10);
        getContentPane().setLayout(layout);

        JPanel firstPanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        bl.setHgap(3);
        firstPanel.setLayout(bl);
        _leftPanel = new CpuPanel();
        firstPanel.add(_leftPanel, BorderLayout.WEST);

        _memoryPanel = new DynamicTable(10000);
        _memoryPanel.SetParentVisualization(_machineVisualisation);
        firstPanel.add(_memoryPanel, BorderLayout.CENTER);

        _secondPanel = new JPanel();
        GridLayout secondLayout = new GridLayout(1, 2);
        _secondPanel.setOpaque(true);
        _secondPanel.setBackground(Color.black);
        secondLayout.setHgap(5);
        _secondPanel.setLayout(secondLayout);

        _devicesPanel = new DevicesPanel();
        _secondPanel.add(_devicesPanel);

        _PTWAndBusPanel = createPTWAndBusPanel();
        _secondPanel.add(_PTWAndBusPanel);

        getContentPane().add(firstPanel);

        getContentPane().add(_secondPanel);

        setBackground(Color.black);
        setForeground(Color.white);

        setSize(1080, 260);

        this.setIconImage(new ImageIcon(Settings.class.getResource(Settings.LOGO_ICON)).getImage());
    }

    /**
     * Set the names of the labels corresponing to the addresses.
     */
    public void setLabels(String[] names, int[] addresses) {
        _memoryPanel.setLabels(names, addresses);
    }

    /**
     * Set the value of the given register.
     */
    public void setRegister(int idx, long value) {
        _leftPanel.setRegister(idx, value);
    }

    /**
     * Set the value of the current instruction.
     */
    public void setInstruction(long value) {
        _leftPanel.setInstruction(value);
    }

    /**
     * set the condition code
     */
    public void setCC(int value) {
        _leftPanel.setCC(value);
    }

    /**
     * set the instruction counter
     */
    public void setBT(long value) {
        _leftPanel.setBT(value);
        _memoryPanel.setActive((int) value + _GBE);
    }

    /**
     * get the value of the given memory cell
     */
    public long getValue(int address) {

        return _machineVisualisation.getRAMCell(address);

    }

    /**
     * update the value of the given memory cell
     */
    public void updateMemory(int address, long value) {
        _memoryPanel.update(address, value);
    }

    public void setRAPogTextField(int idx, long val) {
        _devicesPanel.setRAPogTextField(idx, val);
    }

    public void setRAPtTextField(int idx, long val) {
        _devicesPanel.setRAPtTextField(idx, val);
    }

    public void setDeviceIRQ(int idx, boolean on) {
        _devicesPanel.setDeviceIRQ(idx, on);
    }

    public void setDevices(int number, String[] names) {
        _devicesPanel.setDevices(number, names);
    }

    // PTW & BUS

    private JPanel createPTWAndBusPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(createBusPanel(), BorderLayout.NORTH);
        panel.add(createPTWPanel(), BorderLayout.CENTER);

        return panel;
    }

    // BUS

    private JLabel _busAddress;
    private JLabel _busData;
    private JLabel _busSignal;

    private JPanel createBusPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.gray);

        panel.setLayout(new BorderLayout());

        JPanel dataPanel = new JPanel();
        GridLayout layout = new GridLayout(3, 2);
        layout.setHgap(3);
        layout.setVgap(3);
        dataPanel.setLayout(layout);

        JLabel dataLabel = new JLabel("DATA");
        JLabel addressLabel = new JLabel("ADRES");
        JLabel signalLabel = new JLabel("SIGNAAL");

        dataLabel.setForeground(Color.yellow);
        dataLabel.setOpaque(true);
        dataLabel.setBackground(Color.gray);
        addressLabel.setForeground(Color.yellow);
        addressLabel.setOpaque(true);
        addressLabel.setBackground(Color.gray);
        signalLabel.setForeground(Color.yellow);
        signalLabel.setOpaque(true);
        signalLabel.setBackground(Color.gray);
        dataLabel.setHorizontalAlignment(JLabel.LEFT);
        addressLabel.setHorizontalAlignment(JLabel.LEFT);
        signalLabel.setHorizontalAlignment(JLabel.LEFT);

        _busData = new JLabel();
        _busData.setOpaque(true);
        _busData.setForeground(Color.black);
        _busData.setBackground(Color.white);
        _busData.setHorizontalAlignment(JLabel.CENTER);
        _busAddress = new JLabel();
        _busAddress.setOpaque(true);
        _busAddress.setForeground(Color.black);
        _busAddress.setBackground(Color.white);
        _busAddress.setHorizontalAlignment(JLabel.CENTER);
        _busSignal = new JLabel();
        _busSignal.setOpaque(true);
        _busSignal.setForeground(Color.black);
        _busSignal.setBackground(Color.white);
        _busSignal.setHorizontalAlignment(JLabel.CENTER);

        dataPanel.add(dataLabel);
        dataPanel.add(_busData);
        dataPanel.add(addressLabel);
        dataPanel.add(_busAddress);
        dataPanel.add(signalLabel);
        dataPanel.add(_busSignal);

        panel.add(dataPanel, BorderLayout.CENTER);

        JLabel name = new JLabel("Bus");
        name.setForeground(Color.red);
        name.setVerticalAlignment(JLabel.BOTTOM);

        panel.add(name, BorderLayout.WEST);

        return panel;
    }

    public void setBusAdresText(long value) {
        _busAddress.setText(Long.toString(value));
    }

    public void setBusDataText(long value) {
        _busData.setText(Long.toString(value));
    }

    public void setBusSignaalText(int strobe) {
        _busSignal.setText(Integer.toString(strobe));
    }

    // PTW

    private JLabel[] _ptw;

    private static final String[] _ptwNames = new String[]{
            "ONV",
            "G",
            "H/U",
            "GPF",
            "S/P",
            "WEK",
            "CC",
            "DRK",
            "OVI",
            "IN",
            "SOI",
            "UIT",
            "BT",
            "SCH",
            "",
            "OVL",
            "GBA",
            "SPL",
            "GBE",
            "MFT"
    };

    private static final int ONV = 0;
    private static final int G = 1;
    private static final int HU = 2;
    private static final int GPF = 3;
    private static final int SP = 4;
    private static final int WEK = 5;
    private static final int CC = 6;
    private static final int DRK = 7;
    private static final int OVI = 8;
    private static final int IN = 9;
    private static final int SOI = 10;
    private static final int UIT = 11;
    private static final int BT = 12;
    private static final int SCH = 13;
    private static final int OVL = 15;
    private static final int GBA = 16;
    private static final int SPL = 17;
    private static final int GBE = 18;
    private static final int MFT = 19;

    private JPanel createPTWPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.gray);

        panel.setLayout(new BorderLayout());

        JLabel name = new JLabel("PTW");
        name.setOpaque(true);
        name.setBackground(Color.blue);
        name.setForeground(Color.white);

        panel.add(name, BorderLayout.NORTH);

        JPanel dataPanel = new JPanel();
        GridLayout layout = new GridLayout(10, 4);
        layout.setVgap(3);
        dataPanel.setLayout(layout);

        _ptw = new JLabel[20];

        for (int i = 0; i < 20; i++) {
            JLabel label = new JLabel(_ptwNames[i]);
            label.setForeground(Color.RED);
            label.setHorizontalAlignment(JLabel.CENTER);

            dataPanel.add(label);

            _ptw[i] = new JLabel();
            _ptw[i].setOpaque(true);
            _ptw[i].setBackground(Color.black);
            _ptw[i].setForeground(Color.white);
            _ptw[i].setHorizontalAlignment(JLabel.CENTER);

            dataPanel.add(_ptw[i]);
        }

        panel.add(dataPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * set the ptw fields
     */
    public void setPTW(PTW ptw) {

        DramaPTW dp = (DramaPTW) ptw;

        setTextPTW(dp, ONV, DramaPTW.ONV, HU, DramaPTW.H_U, SP, DramaPTW.S_P, CC, DramaPTW.CC, OVI, DramaPTW.OVI, SOI, DramaPTW.SOI, BT, dp.getBT(), G, DramaPTW.G, GPF, dp.getElement(DramaPTW.GPF));
        setTextPTW(dp, WEK, DramaPTW.WEK, DRK, DramaPTW.DRK, IN, DramaPTW.IN, UIT, DramaPTW.UIT, SCH, DramaPTW.SCH, OVL, DramaPTW.OVL, SPL, dp.getElement(DramaPTW.SPL), MFT, DramaPTW.MFT, GBE, dp.getGBE() % 10000);
        _ptw[GBA].setText(Long.toString(dp.getGBE() / 10000));
        if ((_GBE = ((int) dp.getGBE()) - 50000) < 0)
            _GBE = 0;
        _memoryPanel.setActive((int) dp.getBT() + _GBE);
    }

    private void setTextPTW(DramaPTW dp, int onv, int onv2, int hu, int hU, int sp, int sP, int cc, int cc2, int ovi, int ovi2, int soi, int soi2, int bt, long bt2, int g, int g2, int gpf, long element) {
        _ptw[onv].setText(Long.toString(dp.getElement(onv2)));
        _ptw[hu].setText(Long.toString(dp.getElement(hU)));
        _ptw[sp].setText(Long.toString(dp.getElement(sP)));
        _ptw[cc].setText(Long.toString(dp.getElement(cc2)));
        _ptw[ovi].setText(Long.toString(dp.getElement(ovi2)));
        _ptw[soi].setText(Long.toString(dp.getElement(soi2)));
        _ptw[bt].setText(Long.toString(bt2));
        _ptw[g].setText(Long.toString(dp.getElement(g2)));
        _ptw[gpf].setText(Long.toString(element));
    }

    /**
     * set the visibility of the ptw part of the visualisation
     * also affects the irq part
     */
    public void setPTWPartEnabled(boolean ptwOn) {
        getContentPane().remove(_secondPanel);

        if (ptwOn) {
            getContentPane().add(_secondPanel);
        }

        getContentPane().validate();
    }

    /**
     * set the visibility of the irq part of the visualisation
     * also affects the ptw part
     */
    public void setIRQPartEnabled(boolean irqOn) {
        getContentPane().remove(_secondPanel);

        if (irqOn) {
            getContentPane().add(_secondPanel);
        }

        getContentPane().validate();
    }


}
