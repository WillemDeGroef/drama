/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/SimpleEditor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.editor;

import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.SourceLine;
import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.TextUI;
import java.awt.*;
import java.awt.event.*;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

/**
 * Editor class.
 * 
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class SimpleEditor extends JPanel {

   private JScrollPane _scrollPane;
   private JPanel _innerPanel;

   private ColorLineTextPane _textPane;
   private JLabel _coordinates;

   // private boolean           _debugMode;

   private BreakPointCollection _breakPointCollection;
   private DocumentListener _bpListener;
   



   /**
    * Initialize
    */
   public SimpleEditor() {
      super();

      setLayout(new BorderLayout());

      _breakPointCollection = new BreakPointCollection(this);

      _innerPanel = new JPanel();
      _innerPanel.setLayout(new BorderLayout());

      _scrollPane = new JScrollPane(_innerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      _textPane = new ColorLineTextPane(Color.white, Color.blue);
      _textPane.setFont(new Font("monospaced", Font.PLAIN, 12));

      // _debugMode = false;
      //buildBreakPointCollection();

      _textPane.addCaretListener(new CoordinateController());
      _coordinates = new JLabel("1:1");
      _innerPanel.add(_breakPointCollection.getBox(), BorderLayout.WEST);
      //_breakPointCollection.getBox().setVisible(false);
      _innerPanel.add(_textPane, BorderLayout.CENTER);
      add(_scrollPane, BorderLayout.CENTER);
      add(_coordinates, BorderLayout.SOUTH);

      _bpListener = new MyDocumentListener();

      _textPane.getStyledDocument().addDocumentListener(_bpListener);
   }


   private class CoordinateController
      implements CaretListener {

      public void caretUpdate(CaretEvent ce) {
         //_breakPointCollection.setButtonHeight(_textPane.getLineHeight());
         _coordinates.setText(_textPane.getCurrentRow() + ":" + _textPane.getCurrentColumn());
      }

   }

   /**
    * set the text
    */
   public void setText(String text) {
      _textPane.getStyledDocument().removeDocumentListener(_bpListener);
      _breakPointCollection.clear();
      //text = text.replace('\t', ' ');
      _textPane.setText(text);
      buildBreakPointCollection();
      //disableBreakPoints();
      _textPane.getStyledDocument().addDocumentListener(_bpListener);
      _breakPointCollection.setButtonHeight(_textPane.getLineHeight());

   }

   /**
    * @return the text
    */
   public String getText() {
      return _textPane.getText();
   }

   /*
   public void toggleMode(){
   _debugMode = ! _debugMode;

   if (_debugMode){
    setDebugMode();
} else {
    setNormalMode();
}
}
   */

   /*
   private void setDebugMode(){
   _breakPointCollection.clear();
   buildBreakPointCollection();
   _breakPointCollection.getBox().setVisible(true);
   _textPane.setDefaultForeground(Color.yellow);
   _textPane.applyDefaults();
}
   */

   /*
   private void setNormalMode(){
   _textPane.setDefaultForeground(Color.white);
   _textPane.applyDefaults();
   _breakPointCollection.getBox().setVisible(false);
}
   */

   /**
    * toggle the breakpoint of the current line
    * no effect if disabled
    */
   public void toggleBreakPointOfCurrentLine() {
      toggleBreakPoint(_textPane.getCurrentRow());
   }

   /**
    * toggle the breakpoint of the given obejct code line
    */
   public void toggleBreakPoint(int realLineNumber) {
      _breakPointCollection.toggle(realLineNumber);
   }

   /*
   public boolean isDebugMode(){
   return _debugMode;
}
   */

   /**
    * @return wether the text has been edited
    */
   public boolean isDirty() {
      return _textPane.isDirty();
   }

   /**
    * set wether the text has been edited
    */
   public void setDirty(boolean state) {
      if ((! state) /* && (isDebugMode()) */ && isDirty()) {
         _breakPointCollection.clear();
         buildBreakPointCollection();
         enableBreakPoints();
      }

      _textPane.setDirty(state);
   }

   /**
    * @return a table of breakpoints
    * keys are Integer objects representing object code line numbers
    */
   public Hashtable getBreakPointList() {
      return _breakPointCollection.breakPoints();
   }

   public void buildBreakPointCollection() {

      _breakPointCollection.clear();

      if (_textPane.getText().length() == 0) {
         return ;
      }

      try {
         TextUI mapper = _textPane.getUI();
         Rectangle r = mapper.modelToView(_textPane, 0);
         _breakPointCollection.setButtonHeight(r.height);
      } catch (Exception e) {
         //e.printStackTrace();
      }











      int lc = _textPane.getLineCount();

      for (int i = 1; i <= lc; i++) {
         _breakPointCollection.addLine();
         int lines = 0;

         try {
            lines = new SourceLine(_textPane.getLine(i), null).nbObjectLines();
         } catch (AbnormalTerminationException ate) {
         }










         if (lines != 1) {
            _breakPointCollection.disableLine(i);
         }

      }

   }

   /**
    * reset all text attributes to the default attributes
    */
   public void clearLineColor() {
      _textPane.applyDefaults();
   }

   /**
    * set the foreground color of the given source code line 
    */
   public void setLineColor(int effectiveLineNumber, Color color) {
      setLineColor(effectiveLineNumber, color, null);
   }
   public void setLineColor(int effectiveLineNumber, Color foreground, Color background) {
      _textPane.setColorOfLine(getRealLineNumber(effectiveLineNumber), foreground, background);
   }

   private int getRealLineNumber(int effectiveLineNumber) {
      int realln = 0;
      int effectiveln = -1;

      while (effectiveln < effectiveLineNumber) {
         realln += 1;

         try {
            effectiveln += new SourceLine(_textPane.getLine(realln), null).nbObjectLines();
         } catch (AbnormalTerminationException ate) {
            System.out.println("Programma moet gehercompileerd worden");
         }

      }

      //System.out.println(effectiveLineNumber + " => " + realln);
      return realln;

   }

   private int getEffectiveLineNumber(int realLineNumber) {

      int realln = 0;
      int effectiveln = -1;

      while (realln < realLineNumber) {
         realln += 1;

         try {
            effectiveln += new SourceLine(_textPane.getLine(realln), null).nbObjectLines();
         } catch (AbnormalTerminationException ate) {
            System.out.println("Programma moet gehercompileerd worden");
         }

      }

      return effectiveln;
   }

   /**
    * get the key for the breakpoint list
    * for the given source code line
    */
   public Object getKey(int line) {
      return new Integer(getEffectiveLineNumber(line));
   }

   /**
    * enable the use of breakpoints
    */
   public void enableBreakPoints() {
      _breakPointCollection.clear();
      buildBreakPointCollection();
   }

   /**
    * disable the use of breakpoints
    */
   private void disableBreakPoints() {
      _breakPointCollection.disableAll();
   }

   public void setEditable(boolean on) {
      _textPane.setEditable(on);
   }

   public void setColors(Color fg, Color bg) {
      _textPane.setColors(fg, bg);
   }

   private class MyDocumentListener
      implements DocumentListener {

      public void insertUpdate(DocumentEvent e) {
         disableBreakPoints();
      }

      public void removeUpdate(DocumentEvent e) {
         disableBreakPoints();
      }

      public void changedUpdate(DocumentEvent e) {}










   }

}
