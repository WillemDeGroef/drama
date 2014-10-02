/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ClearingMouseListener.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.text.JTextComponent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * MouseListener for a JTextComponent that 
 * opens a popup menu with a clear option.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class ClearingMouseListener

   extends MouseAdapter {

   private JTextComponent _text;

   /**
    * Initialize for the given JTextComponent
    */
   public ClearingMouseListener(JTextComponent text) {
      _text = text;
   }

   public void mouseClicked(MouseEvent e) {
      popup(e);
   }

   public void mouseReleased(MouseEvent e) {
      popup(e);
   }

   public void mousePressed(MouseEvent e) {
      popup(e);
   }

   private void popup(MouseEvent e) {
      if (e.isPopupTrigger()) {
         JPopupMenu menu = new JPopupMenu();
         menu.setInvoker(_text);
         //menu.setLocation(e.getX(), e.getY());
         JMenuItem item = new JMenuItem("Wis scherm");
         item.addActionListener(new TheAction());
         menu.add(item);
         //menu.setVisible(true);
         menu.show(_text, e.getX() - 10, e.getY() - 10);
      }

   }

   private class TheAction
      implements ActionListener {

      public void actionPerformed(ActionEvent ae) {
         _text.setText("");
      }

   }


}
