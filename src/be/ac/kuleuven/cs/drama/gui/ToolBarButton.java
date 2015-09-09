/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ToolBarButton.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Subclass of JButton
 * implementing different properties from action.
 * Only the icon of an action is displayed on the button.
 * The name appears in the tooltip.
 *
 * @version 1.0.0 09/04/2000
 * @author  Tom Schrijvers
 */

public class ToolBarButton

   extends JButton {
	private static final long serialVersionUID = 0L;

   public ToolBarButton() {
      super();
   }

   public ToolBarButton(Action action) {
      super(action);
   }

   protected void configurePropertiesFromAction(Action a) {
      setIcon((a != null ? (Icon)a.getValue(Action.SMALL_ICON) : null));
      setEnabled((a != null ? a.isEnabled() : true));
      setToolTipText((a != null ? (String)a.getValue(Action.NAME) : null));

      if (a != null) {
         Integer i = (Integer)a.getValue(Action.MNEMONIC_KEY);

         if (i != null)
            setMnemonic(i.intValue());
      }

   }

}


