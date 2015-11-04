/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/visualisation/DynamicTable.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.visualisation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Class that displays the memory contents.
 * 
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class DynamicTable
   extends JPanel {
   private static final long serialVersionUID = 0L;
   
   private JTable _table;
   private MemoryTableModel _model;
   
   /**
    * Initialize.
    * @param memorySize the entire size of memory
    */
   public DynamicTable(int memorySize) {
      super();

      setBackground(Color.black);
      setForeground(Color.white);

      this.setLayout(new BorderLayout());
      add(createTable(memorySize), BorderLayout.CENTER);
   }

   /**
    * Set the names of the labels corresponding to the given addresses.
    * All other addresses will have no label.
    */
   public void setLabels(String[] names, int[] addresses) {
      _model.clearLabels();
      for(int i = 0;i<names.length;i++){
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
      
      _model = new MemoryTableModel(memorySize);
      _table = new JTable(_model);
      
      MemoryTableCellRenderer renderer = new MemoryTableCellRenderer();
      for(int i = 0;i<_table.getColumnCount();i++){
         _table.getColumnModel().getColumn(i).setCellRenderer(renderer);
      }

      JScrollPane tableScollPane = new JScrollPane();
      tableScollPane.setViewportView(_table);
      
      outerPanel.add(tableScollPane, BorderLayout.CENTER);

      return outerPanel;
   }

   /**
    * Set active memory cell,
    * scroll to get cell in visible range,
    * cell will have red foreground until
    * other cell is set active.
    */
   public void setActive(int address) {
      _table.scrollRectToVisible(_table.getCellRect(address, 0, true));
      _table.repaint();
      _model.setActiveLine(address);
   }

   //Data model of the table
   private class MemoryTableModel extends AbstractTableModel {
      private static final int COLUMN_LABEL = 0;
      private static final int COLUMN_ADDRESS = 1;
      private static final int COLUMN_VALUE = 2;
      private String[] columnNames = {"Label", "Adres", "Inhoud"};
      
      private int memorySize;
      private String[] labels;
      private long[] memory;
      private int activeLine = -1;
      
      public MemoryTableModel(int memorySize){
          this.memorySize = memorySize;
          this.labels = new String[memorySize];
          this.memory = new long[memorySize];
      }
      
      public Object getValueAt(int row, int col) {
         if(col == COLUMN_LABEL){
            return labels[row];
         }else if(col == COLUMN_ADDRESS){
            return address(row);
         }else if(col == COLUMN_VALUE){
            return value(memory[row]);
         }
         return null; //Invalid column! Should never happen.
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
      
      public boolean isCellEditable(int row, int col){ 
         return false;
      }
      public void setValueAt(Object value, int row, int col) {
         if(col == COLUMN_LABEL){
            labels[row] = value.toString();
            fireTableCellUpdated(row, col);
         }else if(col == COLUMN_VALUE){
            if(value instanceof Long){
               memory[row] = (Long)value;
            }else{
               System.err.println("Invalid value " +value  + " of type " + value.getClass() + " cannot be set as memory value!");
            }
            fireTableCellUpdated(row, col);
         }else{
            System.err.println("MemoryTableModel column "+col+" cannot be updated!");
         }
      }
      
      public void setLabel(int row, String label){
         labels[row] = label;
         fireTableCellUpdated(row, COLUMN_LABEL);
      }
      
      public void clearLabels() {
         labels = new String[memorySize];
         fireTableDataChanged();
      }
      
      public void setValue(int row, long value){
         memory[row] = value;
         fireTableCellUpdated(row, COLUMN_VALUE);
      }
      
      public void setActiveLine(int newActiveLine){
         int oldActiveLine = activeLine;
         activeLine = newActiveLine;
         if(oldActiveLine != -1){
            fireTableCellUpdated(oldActiveLine, COLUMN_LABEL);
            fireTableCellUpdated(oldActiveLine, COLUMN_ADDRESS);
            fireTableCellUpdated(oldActiveLine, COLUMN_VALUE);
         }
         fireTableCellUpdated(activeLine, COLUMN_LABEL);
         fireTableCellUpdated(activeLine, COLUMN_ADDRESS);
         fireTableCellUpdated(activeLine, COLUMN_VALUE);
      }
   }
   
   //Table renderer, handles font color for the table cells
   private class MemoryTableCellRenderer extends DefaultTableCellRenderer {
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         MemoryTableModel model = (MemoryTableModel)table.getModel();
         Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if(model.activeLine == row){
            c.setForeground(Color.RED);
         }else{
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
}
