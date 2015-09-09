/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/SimpleAction.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * Simple implementation of the AbstractAction
 * class that executes the react method of
 * an ActionImpl when enabled.
 * 
 * @version 1.0.0 09/04/2000
 * @author  Tom Schrijvers
 */

public class SimpleAction

   extends AbstractAction {
	private static final long serialVersionUID = 0L;


   private final ActionImpl _actionImpl;

   public SimpleAction(String name, ActionImpl ai) {
      super(name);
      _actionImpl = ai;
   }

   public SimpleAction(String name, Icon icon, ActionImpl ai) {
      super(name, icon);
      _actionImpl = ai;
   }

   public void actionPerformed(ActionEvent ae) {
      if (isEnabled()) {
         _actionImpl.react();
      }

   }

}


