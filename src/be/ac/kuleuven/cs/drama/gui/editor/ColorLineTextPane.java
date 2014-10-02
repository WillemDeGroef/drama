/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/ColorLineTextPane.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.editor;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

/**
 * JTextpane that supports the notion of different lines.
 * 
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class ColorLineTextPane extends ColorTextPane {

   private MyDocumentListener _dirtyListener;

   private int _lineCount = 0;

   private List _documentListeners = new ArrayList();

   /**
    * Initialize with given background and foreground color.
    */
   public ColorLineTextPane(Color fg, Color bg) {
      super(fg, bg);
      MutableAttributeSet as = new SimpleAttributeSet();
      StyleConstants.setFontFamily(as, "Dialog");
      StyleConstants.setFontSize(as, 12);
      addDefaultAttributes(as);
      _dirtyListener = new MyDocumentListener();
      addDocumentListener(_dirtyListener);
   }

   public void addDocumentListener(DocumentListener documentListener) {
      _documentListeners.add(documentListener);
      getStyledDocument().addDocumentListener(documentListener);
   }


   public void setText(String text) {
      ListIterator it = _documentListeners.listIterator();

      while (it.hasNext()) {
         getStyledDocument().removeDocumentListener((DocumentListener) it.next());
      }

      super.setText(text);
      refreshLineCount();

      _dirtyListener.setDirty(false);

      while (it.hasPrevious()) {
         getStyledDocument().addDocumentListener((DocumentListener) it.previous());
      }

   }

   /**
    * @return wether text is edited.
    */
   public boolean isDirty() {
      return _dirtyListener.isDirty();
   }

   /**
    * set wether the text is edited
    */
   public void setDirty(boolean state) {
      _dirtyListener.setDirty(state);
   }

   /**
    * set the color of the a line
    */
   public void setColorOfLine(int line, Color color) {
      setColorOfLine(line, color, null);
   }
   public void setColorOfLine(int line, Color foreground, Color background) {

      if (nonEmpty() && validLineNumber(line)) {

         char[] chars = getText().toCharArray();

         int offset = 0;
         int ln = line - 1;

         while (ln > 0) {

            if (chars[offset] == '\n') {
               ln -= 1;
            }

            offset += 1;

         }

         int length = 0;

         while (((offset + length) <= chars.length) && (chars[offset + length] != '\n')) {
            length += 1;
         }

         if (foreground != null)
            setForegroundColor(foreground, offset, length);
         if (background != null)
            setBackgroundColor(background, offset, length);
      }

   }

   /*
    * get a line
    */
   public String getLine(int number) {

      if (nonEmpty() && validLineNumber(number)) {

         try {
            char[] chars = getText().toCharArray();

            int offset = 0;
            int ln = number - 1;

            while (ln > 0) {

               if (chars[offset] == '\n') {
                  ln -= 1;
               }

               offset += 1;

            }

            int length = 0;

            while (((offset + length) <= chars.length) && (chars[offset + length] != '\n')) {
               length += 1;
            }

            return new String(chars, offset, length);
         } catch (ArrayIndexOutOfBoundsException aiobe) {
            return "";
         }
      } else {
         return "";
      }

   }

   /**
    * get the number of lines
    */
   public int getLineCount() {

      if (isDirty()) {
         refreshLineCount();
      }

      return _lineCount;
   }

   private void refreshLineCount() {
      //System.out.println("refreshLineCount()");
      _lineCount = getRowNumber(getText().length() - 1);
   }

   private boolean nonEmpty() {
      return getText().length() > 0;
   }

   private boolean validLineNumber(int nb) {
      return nb > 0 && nb <= _lineCount;
   }

   private int getRowNumber(int position) {
      char[] chars = getText().toCharArray();

      int count = 0;

      for (int i = 0; i < position; i++) {
         if (chars[i] == '\n') {
            count = count + 1;
         }

      }

      return count + 1;
   }

   /**
    * @return the number of the current row
    */
   public int getCurrentRow() {
      return getRowNumber(getCaretPosition());
   }

   public int getCurrentColumn() {
      char[] chars = getText().toCharArray();

      int column = 1;
      int position = getCaretPosition() - 1;

      while (position >= 0 && chars.length != 0 && chars[position] != '\n') {
         position -= 1;
         column += 1;
      }

      return column;
   }

   /**
    * return the height of a line
    */
   public int getLineHeight() {
      //System.out.println(((DefaultCaret) getCaret()).getHeight());
      return (int) ((DefaultCaret) getCaret()).getHeight();
   }

   private class MyDocumentListener
      implements DocumentListener {

      private boolean _dirty = false;


      public void insertUpdate(DocumentEvent e) {
         _dirty = true;
      }

      public void removeUpdate(DocumentEvent e) {
         //System.out.println("removeUpdate");
         _dirty = true;
      }

      public void changedUpdate(DocumentEvent e) {
         //_dirty = true;
      }










      public boolean isDirty() {
         return _dirty;
      }

      public void setDirty(boolean state) {
         _dirty = state;
      }

   }

}
