/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/BreakPointCollection.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.editor;

//import be.ac.kuleuven.cs.drama.gui.DramaTaskBar;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

/**
 * A BreakpointCollection manages the breakpoints,
 * both visually as internally.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class BreakPointCollection {

   public static int HEIGHT = 13;

   private Hashtable _mapping;
   private SimpleEditor _editor;

   private JPanel _box;

   private int _count;
   private List _buttons;

   /**
    * Initialize for a given SimpleEditor.
    */
   public BreakPointCollection(SimpleEditor se) {
      _editor = se;
      _mapping = new Hashtable();
      _box = new JPanel();
      _box.setLayout(new VerticalLayout());
      //_box.add(Box.createRigidArea(new Dimension(20, 0)));
      _buttons = new ArrayList();
      _count = 0;
      _box.setForeground(Color.white);
      _box.setBackground(Color.white);

   }

   /**
    * Table of breakpoints.
    * Keys are Integer objects representing the line numbers
    * of the breakpoint lines.
    * Values are undefined.
    */
   public Hashtable breakPoints() {
      return _mapping;
   }

   /**
    * Clear all breakpoints and all
    * visual compoments.
    */
   public void clear() {
      _mapping.clear();

      while (_count > 0) {
         removeLine();
      }

   }

   private void toggleBreakPoint(int line, Object key) {
      if (_mapping.containsKey(key)) {
         _mapping.remove(key);
      } else {
         _mapping.put(key, new Integer(line));
      }

   }

   /**
    * Toggle the breakpoint of the given line
    * in the source code.
    */
   public void toggleBreakPoint(int line) {
      toggleBreakPoint(line, _editor.getKey(line));
   }

   /**
    * Toggle the state of the button at the
    * given object code line.
    */
   public void toggle(int line) {
      getButton(line).toggle();
   }

   /**
    * @return the visual component containing
    * all breakpoint buttons
    */
   public JPanel getBox() {
      return _box;
   }

   /**
    * increase the number of source code lines.
    */
   public void addLine() {
      _count += 1;
      _buttons.add(new MyButton(_count));
      _box.add(getButton(_count));
   }

   /**
    * decrease the number of source code lines
    */
   public void removeLine() {
      getButton(_count).disable();
      _box.remove(getButton(_count));
      _buttons.remove(getButton(_count));
      _count -= 1;
   }

   /**
    * disallow a source code line to have a breakpoint
    */
   public void disableLine(int line) {
      getButton(line).disableButton();
   }

   /**
    * allow a source code line to have a breakpoint
    */
   public void enableLine(int line) {
      getButton(line).enableButton();
   }

   private MyButton getButton(int line) {
      return (MyButton) _buttons.get(line - 1);
   }

   /**
    * Disable all breakpoints
    */
   public void disableAll() {
      for (int i = 1; i <= _count; i++) {
         getButton(i).disableButton();
      }

   }

   /**
    * Set the height of the breakpoint buttons.
    */
   public void setButtonHeight(int height) {
      if (height != 0) HEIGHT = height;

   }

   private class MyButton extends JButton {

      private final int _line;
      private final Action _action;
      private boolean _on;
      public static final int WIDTH = 20;


      public MyButton(int line) {
         super();
         _line = line;
         _on = false;
         _action = new MyAction();
         addActionListener(_action);
         setForeground(Color.blue);
         setBackground(Color.blue);


         setPreferredSize(new Dimension(WIDTH, BreakPointCollection.HEIGHT));
      }

      public void enableButton() {
         setEnabled(true);
         _action.setEnabled(true);
         showIcon();
      }

      public void disableButton() {
         setEnabled(false);
         _action.setEnabled(false);
      }

      public void toggle() {
         _action.actionPerformed(null);
      }

      private void toggleMe() {
         _on = ! _on;
         toggleBreakPoint(_line);
         showIcon();
         //System.out.println(getSize());
         //System.out.println(getMargin());
         //System.out.println(getBounds());
      }











      private void showIcon() {
         if (_on) {
            setBackground(Color.red);
         } else {
            setBackground(Color.blue);
         }

      }

      private class MyAction
         extends AbstractAction {

         public MyAction() {}











         public void actionPerformed(ActionEvent e) {
            if (this.isEnabled()) {
               //System.out.println(getSize());
               toggleMe();
            }

         }

      }

   }

}
