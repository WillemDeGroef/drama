/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/DynamicTable.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.io.Console;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import be.ac.kuleuven.cs.drama.vertalerpack.macro.MCREINDECommand;

/**
 * Class that displays the memory contents.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class DynamicTable extends JPanel {
    private static final long serialVersionUID = 0L;

    private JTable _table;
    private MemoryTableModel _model;
    private MachineVisualisation _MachineVisualisation;

    /**
     * Initialize.
     *
     * @param memorySize
     *            the entire size of memory
     */
    public DynamicTable(int memorySize) {
        super();

        setBackground(Color.black);
        setForeground(Color.white);

        this.setLayout(new BorderLayout());
        add(createTable(memorySize), BorderLayout.CENTER);
    }

    /**
     * Set the names of the labels corresponding to the given addresses. All
     * other addresses will have no label.
     */
    public void setLabels(String[] names, int[] addresses) {
        _model.clearLabels();
        for (int i = 0; i < names.length; i++) {
            _model.setLabel(addresses[i], names[i]);
        }
    }

    /**
     * Update the contents of the given cell.
     */
    public void update(int address, long value) {
        _model.setValue(address, value);
    }

    /**
     * Creates a JTable containing all the memory values and labels.
     */
    private JPanel createTable(int memorySize) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());

        outerPanel.setOpaque(true);
        outerPanel.setBackground(Color.PINK);

        JLabel title = new JLabel("Geheugen");
        title.setOpaque(true);
        title.setForeground(Color.white);
        title.setBackground(Color.blue);
        title.setHorizontalAlignment(JLabel.CENTER);

        outerPanel.add(title, BorderLayout.NORTH);

        JTextField textInput = new JTextField("Search");
        textInput.setOpaque(true);
        textInput.setForeground(Color.white);
        textInput.setBackground(Color.blue);
        textInput.setAction(searchEnterAction);
        outerPanel.add(textInput, BorderLayout.SOUTH);

        _model = new MemoryTableModel(memorySize);
        _table = new JTable(_model);

        MemoryTableCellRenderer renderer = new MemoryTableCellRenderer();
        for (int i = 0; i < _table.getColumnCount(); i++) {
            _table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane tableScollPane = new JScrollPane();
        tableScollPane.setViewportView(_table);

        outerPanel.add(tableScollPane, BorderLayout.CENTER);

        return outerPanel;
    }

    /**
     * Set active memory cell, scroll to get cell in visible range, cell will
     * have red foreground until other cell is set active.
     */
    public void setActive(int address) {
        _table.scrollRectToVisible(_table.getCellRect(address, 0, true));
        _table.repaint();
        _model.setActiveLine(address);
    }

    // Data model of the table
    private class MemoryTableModel extends AbstractTableModel {
        private static final int COLUMN_LABEL = 0;
        private static final int COLUMN_ADDRESS = 1;
        private static final int COLUMN_VALUE = 2;
        private String[] columnNames = {"Label", "Adres", "Inhoud"};

        private int memorySize;
        private String[] labels;
        private ArrayList<AbstractMap.SimpleEntry<String, Integer>> labelRows;
        private long[] memory;
        private int activeLine = -1;

        public MemoryTableModel(int memorySize) {
            this.memorySize = memorySize;
            this.labels = new String[memorySize];
            this.memory = new long[memorySize];
            this.labelRows = new ArrayList<AbstractMap.SimpleEntry<String, Integer>>();
        }

        public Object getValueAt(int row, int col) {
            if (col == COLUMN_LABEL) {
                return labels[row];
            } else if (col == COLUMN_ADDRESS) {
                return address(row);
            } else if (col == COLUMN_VALUE) {
                return value(memory[row]);
            }
            return null; // Invalid column! Should never happen.
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getRowCount() {
            return memorySize;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            if (col == COLUMN_LABEL) {
                labels[row] = value.toString();
                fireTableCellUpdated(row, col);
            } else if (col == COLUMN_VALUE) {
                if (value instanceof Long) {
                    memory[row] = Long.parseLong(value.toString());
                } else {
                    System.err.println("Invalid value " + value + " of type "
                            + value.getClass()
                            + " cannot be set as memory value!");
                }
                fireTableCellUpdated(row, col);
            } else {
                System.err.println("MemoryTableModel column " + col
                        + " cannot be updated!");
            }
        }

        public void setLabel(int row, String label) {
            labelRows.add((new SimpleEntry<String, Integer>(label, row)));
            labels[row] = label;
            fireTableCellUpdated(row, COLUMN_LABEL);
        }

        public void clearLabels() {
            labelRows = new ArrayList<AbstractMap.SimpleEntry<String, Integer>>();
            labels = new String[memorySize];
            fireTableDataChanged();
        }

        public void setValue(int row, long value) {
            memory[row] = value;
            fireTableCellUpdated(row, COLUMN_VALUE);
        }

        public void setActiveLine(int newActiveLine) {
            int oldActiveLine = activeLine;
            activeLine = newActiveLine;
            if (oldActiveLine != -1) {
                fireTableCellUpdated(oldActiveLine, COLUMN_LABEL);
                fireTableCellUpdated(oldActiveLine, COLUMN_ADDRESS);
                fireTableCellUpdated(oldActiveLine, COLUMN_VALUE);
            }
            fireTableCellUpdated(activeLine, COLUMN_LABEL);
            fireTableCellUpdated(activeLine, COLUMN_ADDRESS);
            fireTableCellUpdated(activeLine, COLUMN_VALUE);
        }

        public ArrayList<AbstractMap.SimpleEntry<String, Integer>> getLabelRows() {
            return labelRows;
        }
    }

    // Table renderer, handles font color for the table cells
    private class MemoryTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            MemoryTableModel model = (MemoryTableModel) table.getModel();
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            if (model.activeLine == row) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }

    private String address(int adr) {
        String result = Integer.toString(adr);

        while (result.length() < 4) {
            result = "0" + result;
        }

        return result;
    }

    private String value(long val) {
        String result = Long.toString(val);

        while (result.length() < 10) {
            result = "0" + result;
        }

        return result;
    }

    AbstractAction searchEnterAction = new AbstractAction() {

        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Yo");
            String parseString = ((JTextField) arg0.getSource()).getText();
            boolean parseSucceeded = false;
            boolean isValidAddress = false;
            int jumpAddress = 0;

            try {

                jumpAddress = Integer.parseInt(parseString);
                parseSucceeded = true;
                isValidAddress = jumpAddress >= 0 && jumpAddress <= 9999;

            } catch (Exception e) {

            }
            if (!parseSucceeded) {
                /*
                 * Parse a non-numerical Search String
                 */
                int Register1;
                int RegisterIndex = -1;
                SimpleEntry<String, Integer> tmpKeyValue = CheckRegisterIndex(
                        parseString, RegisterIndex);
                parseString = tmpKeyValue.getKey();
                RegisterIndex = tmpKeyValue.getValue();
                int RegisterIndexValue = 0;
                if (RegisterIndex >= 0 && RegisterIndex <= 9) {
                    RegisterIndexValue = _MachineVisualisation
                            .getRegisterValue(RegisterIndex);
                }

                boolean bIsAddition = true;
                String[] splitParse = new String[2];
                if (parseString.contains("-")) {
                    splitParse = parseString.split("-");
                    bIsAddition = false;
                } else if (parseString.contains("+")) {
                    splitParse = parseString.split("\\+");
                    bIsAddition = true;
                }

                if (splitParse[0] == null && splitParse[1] == null) {
                    splitParse[0] = parseString;
                }

                try {
                    Integer.parseInt(splitParse[0]);
                    splitParse[1] = splitParse[0];
                } catch (Exception e) {
                    // TODO: handle exception
                }

                /*
                 * Note: the maximum amount of values should be 2, 0 and 1
                 */
                boolean bHasSecondValue = true;
                int secondValue = 0;
                int tmpAddress = 0;
                try {

                    secondValue = Integer.parseInt(splitParse[1]);
                } catch (Exception e) {
                    bHasSecondValue = false;
                }

                try {
                    String labelName = splitParse[0];
                    SimpleEntry<String, Integer> tmpKV = new SimpleEntry<String, Integer>(
                            null, -1);
                    ArrayList<AbstractMap.SimpleEntry<String, Integer>> tmpList = _model
                            .getLabelRows();

                    for (SimpleEntry<String, Integer> labelRowValue : tmpList) {
                        if (labelName.equals(labelRowValue.getKey())) {
                            tmpKV = labelRowValue;
                            break;
                        }
                    }
                    if (tmpKV.getKey() != null) {
                        tmpAddress = tmpKV.getValue();
                    }

                    if (tmpKV.getKey() == null) {

                        Register1 = ExtractRegisterFromString(splitParse[0]);
                        tmpAddress = _MachineVisualisation
                                .getRegisterValue(Register1);
                    }

                } catch (Exception e) {

                }

                if (bHasSecondValue) {
                    if (bIsAddition) {
                        tmpAddress += secondValue;
                    } else {
                        tmpAddress -= secondValue;
                    }

                }

                tmpAddress += RegisterIndexValue;
                setActive(tmpAddress);

            } else {
                if (isValidAddress) {
                    setActive(jumpAddress);
                }
            }
        }

    };

    private SimpleEntry<String, Integer> CheckRegisterIndex(String pt, int ri) {
        boolean containsRegisterIndex = pt.contains("(") && pt.contains(")");
        if (!containsRegisterIndex) {
            return new SimpleEntry<String, Integer>(pt, ri);
        }
        String ParseRegisterString = pt.substring(pt.indexOf("(") + 1,
                pt.indexOf(")"));
        try {
            ri = ExtractRegisterFromString(ParseRegisterString);
        } catch (Exception e) {
        }
        String tmpStr = "(" + ParseRegisterString + ")";
        pt = pt.replace(tmpStr, "");
        return new SimpleEntry<String, Integer>(pt, ri);
    }

    private int ExtractRegisterFromString(String prs) {
        for (int i = 0; i < 10; i++) {
            String searchR = "R".concat(Integer.toString(i));
            if (prs.contentEquals(searchR)) {
                return i;
            }
        }
        return -1;
    }

    public void SetParentVisualization(MachineVisualisation machineVisualisation) {
        _MachineVisualisation = machineVisualisation;
    }
}
