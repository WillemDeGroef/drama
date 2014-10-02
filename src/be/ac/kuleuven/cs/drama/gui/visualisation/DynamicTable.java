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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Arrays;

/**
 * Class that displays the memory contents.
 * 
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class DynamicTable

   extends JPanel {

   private static final boolean DEBUG = false;

   private final InternalRepresentationFrame _imr;

   private final int _virtualSize;
   private final int _realSize;

   private int _frameStart;

   private JLabel[] _labels;
   private JLabel[] _addresses;
   private JLabel[] _values;

   private JScrollBar _bar;

   private int _activeLine = 0;
   private int _activePos = 0;

   private int[] _labelAddresses;
   private String[] _labelNames;

   /**
    * Initialize.
    * @param virtualSize the entire size of memory
    * @param realSize    the size of the memory frame visible 
    */
   public DynamicTable(InternalRepresentationFrame imr, int virtualSize, int realSize) {
      super();

      setBackground(Color.black);
      setForeground(Color.white);

      _imr = imr;
      _virtualSize = virtualSize;
      _realSize = realSize;

      setLayout(new BorderLayout());

      add(createTable(), BorderLayout.CENTER);
      add(createScrollBar(), BorderLayout.EAST);

      fillAll();

   }

   /**
    * Set the names of the labels corresponding to the given addresses.
    * All other addresses will have no label.
    */
   public void setLabels(String[] names, int[] addresses) {
      _labelNames = names;
      _labelAddresses = addresses;
   }

   /**
    * Update the contents of the given cell.
    */
   public void update(int address, long value) {
      if (address >= _frameStart && address < (_frameStart + _realSize)) {
         // only if visible
         fill(address - _frameStart, address);
      }

   }

   /*
    * Refill all visible cells.
    */
   private void fillAll() {
      for (int i = 0; i < _realSize; i++) {
         fill(i, _frameStart + i);
      }

   }

   /*
    * call in constructor only
    */
   private JPanel createTable() {

      JPanel outerPanel = new JPanel();
      outerPanel.setLayout(new BorderLayout());

      outerPanel.setOpaque(true);
      outerPanel.setBackground(Color.black);

      JLabel title = new JLabel("Geheugen");
      title.setOpaque(true);
      title.setForeground(Color.white);
      title.setBackground(Color.blue);
      title.setHorizontalAlignment(JLabel.CENTER);

      outerPanel.add(title, BorderLayout.NORTH);

      JPanel panel = new JPanel();
      GridLayout layout = new GridLayout(_realSize + 1, 3);
      layout.setHgap(3);
      layout.setVgap(3);
      panel.setLayout(layout);
      panel.setForeground(Color.white);
      panel.setBackground(Color.black);

      JLabel labelLabel = new JLabel("Label");
      JLabel addressLabel = new JLabel("Adres");
      JLabel contentLabel = new JLabel("Inhoud");
      labelLabel.setOpaque(true);
      labelLabel.setForeground(Color.white);
      labelLabel.setBackground(Color.blue);
      labelLabel.setHorizontalAlignment(JLabel.CENTER);
      addressLabel.setOpaque(true);
      addressLabel.setForeground(Color.white);
      addressLabel.setBackground(Color.blue);
      addressLabel.setHorizontalAlignment(JLabel.CENTER);
      contentLabel.setOpaque(true);
      contentLabel.setForeground(Color.white);
      contentLabel.setBackground(Color.blue);
      contentLabel.setHorizontalAlignment(JLabel.CENTER);
      panel.add(labelLabel);
      panel.add(addressLabel);
      panel.add(contentLabel);

      _labels = new JLabel[_realSize];
      _addresses = new JLabel[_realSize];
      _values = new JLabel[_realSize];

      for (int i = 0; i < _realSize; i++) {
         _labels[i] = new JLabel();
         _labels[i].setForeground(Color.white);
         _labels[i].setHorizontalAlignment(JLabel.CENTER);
         panel.add(_labels[i]);
         _addresses[i] = new JLabel("");
         //_addresses[i].setOpaque(true);
         // _addresses[i].setBackground(Color.gray);
         _addresses[i].setForeground(Color.white);
         _addresses[i].setHorizontalAlignment(JLabel.CENTER);
         panel.add(_addresses[i]);
         _values[i] = new JLabel("");
         _values[i].setOpaque(true);
         _values[i].setBackground(Color.gray);
         _values[i].setForeground(Color.white);
         _values[i].setHorizontalAlignment(JLabel.CENTER);
         panel.add(_values[i]);
      }

      outerPanel.add(panel, BorderLayout.CENTER);

      return outerPanel;

   }

   /*
    * call in constructor only
    */
   private JScrollBar createScrollBar() {
      _frameStart = 0;
      _bar = new JScrollBar(JScrollBar.VERTICAL, _frameStart, 10, 0, _virtualSize);
      _bar.addAdjustmentListener(new MyAdjustmentListener(_bar));
      return _bar;
   }

   /*
    * Scroll the visible range to the given address
    */
   private void scrollViewTo(int frameStart) {

      int difference = _frameStart - frameStart;

      if (difference == 0) {
         // do nothing if not moved
         return ;
      }

      _frameStart = frameStart;

      if (_activePos >= 0) {
         // reset red line to white
         _labels[_activePos].setForeground(Color.white);
         _addresses[_activePos].setForeground(Color.white);
         _values[_activePos].setForeground(Color.white);
      }

      fillAll();

      if (_activeLine >= _frameStart && _activeLine < (_frameStart + _realSize)) {
         // set red line if visible
         _activePos = _activeLine - _frameStart;

         _labels[_activePos].setForeground(Color.red);
         _addresses[_activePos].setForeground(Color.red);
         _values[_activePos].setForeground(Color.red);

      } else {
         _activePos = -1;
      }

   }

   private void fill(int position, int address) {
      _addresses[position].setText(address(address));
      _values[position].setText(value(_imr.getValue(address)));

      int pos = -1;

      if (_labelAddresses != null) {
         pos = Arrays.binarySearch(_labelAddresses, address);
      }

      if ( pos >= 0) {
         _labels[position].setText(_labelNames[pos]);
         _labels[position].setToolTipText(_labelNames[pos]);
      } else {
         _labels[position].setText("");
         _labels[position].setToolTipText(null);
      }

   }

   // Past inhoud van cellen aan als er gescrolled wordt.

   private class MyAdjustmentListener

      implements AdjustmentListener {

      private JScrollBar _scroller;

      public MyAdjustmentListener(JScrollBar scroller) {
         _scroller = scroller;
      }

      public void adjustmentValueChanged(AdjustmentEvent e) {

         // doe niets als er gedragged wordt

         if (DEBUG) {
            System.out.println("adjustmentValueChanged()");
            System.out.println("dragging: " + _scroller.getValueIsAdjusting());
         }

         if (! _scroller.getValueIsAdjusting()) scrollViewTo(e.getValue());

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

   /**
    * Set active memory cell,
    * scroll to get cell in visible range,
    * cell will have red foreground until
    * other cell is set active.
    */
   public void setActive(int adr) {
      _activeLine = adr;
      scrollViewTo(adr);
   }

}
