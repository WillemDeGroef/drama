/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/ColorTextPane.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.editor;

import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/**
 * JTextPane with direct support for color and default
 * attributes.
 * 
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class ColorTextPane extends JTextPane {

   private MutableAttributeSet _defaultAttribs;

   /**
    * Initialize.
    */
   public ColorTextPane() {
      super();

      _defaultAttribs = getStyledDocument().getStyle(StyleContext.DEFAULT_STYLE);
   }

   /**
    * Initialize with given default background and foreground color.
    */
   public ColorTextPane(Color fgc, Color bgc) {
      this();

      setColors(fgc, bgc);
   }

   /**
    * set the background and foreground colors
    */
   public void setColors(Color fg, Color bg) {

      setBackground(bg);
      setForeground(fg);
      setCaretColor(fg);

      //setForegroundColor(fg, 0, getText().length());
      //setBackgroundColor(bg, 0, getText().length());

      setDefaultBackground(bg);
      setDefaultForeground(fg);
   }


   /**
    * set the foreground colro of the given text part
    */
   public void setForegroundColor(Color color, int offset, int length) {
      MutableAttributeSet as = new SimpleAttributeSet();
      StyleConstants.setForeground(as, color);
      getStyledDocument().setCharacterAttributes(offset, length, as, false);
   }
   
   public void setBackgroundColor(Color color, int offset, int length) {
      MutableAttributeSet as = new SimpleAttributeSet();
      StyleConstants.setBackground(as, color);
      getStyledDocument().setCharacterAttributes(offset, length, as, false);
   }
   
   /**
    * add the given attributes to the default attributes
    */
   protected void addDefaultAttributes(AttributeSet as) {
      _defaultAttribs.addAttributes(as);
   }

   /**
    * set the default foreground color
    */
   public void setDefaultForeground(Color c) {
      StyleConstants.setForeground(_defaultAttribs, c);
   }

   /**
    * set the default background color
    */
   public void setDefaultBackground(Color c) {
      StyleConstants.setBackground(_defaultAttribs, c);
   }


   //public void setBackgroundColor(Color color, int offset, int length){
   // MutableAttributeSet as = new SimpleAttributeSet();
   // StyleConstants.setBackground(as, color);
   // getStyledDocument().setCharacterAttributes(offset,length, as, false);
   //}

   public void setText(String text) {
      //System.out.println("Resetting text");
      super.setText(text);
      applyDefaults();
   }

   /**
    * Apply the default attributes to the entire text.
    */
   public void applyDefaults() {
      MutableAttributeSet attribs = getStyledDocument().getStyle(StyleContext.DEFAULT_STYLE);
      attribs.addAttributes(_defaultAttribs);
      _defaultAttribs = attribs;
      getStyledDocument().setCharacterAttributes(0, getText().length(), _defaultAttribs, false);
   }

}
