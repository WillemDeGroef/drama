/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/EditFrameMenuFactory.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

/**
 * Creates the menu bar for the
 * editor frame.
 *
 * @version 1.0.0 09/06/2000
 * @author  Tom Schrijvers
 */

public class EditFrameMenuFactory {

   private final ActionManager _actionManager;
   private JMenuBar _menuBar;

   public EditFrameMenuFactory(ActionManager actionManager) {
      _actionManager = actionManager;
   }

   public JMenuBar getMenuBar() {
      if (_menuBar == null) {
         createMenuBar();
      }

      return _menuBar;
   }

   private void createMenuBar() {
      _menuBar = new JMenuBar();
      createFileMenu();
      createCompileMenu();
      createHelpMenu();
   }

   private void createFileMenu() {
      JMenu menu = new JMenu("Bestand");

      menu.add(_actionManager.getNewFileAction());
      menu.add(_actionManager.getOpenFileAction());
      menu.add(_actionManager.getSaveFileAction());
      menu.add(_actionManager.getSaveAsFileAction());

      menu.addSeparator();

      menu.add(_actionManager.getQuitAction());

      _menuBar.add(menu);
   }

   private void createCompileMenu() {
      JMenu menu = new JMenu("Vertalen");

      menu.add(_actionManager.getPrecompileAction());
      menu.add(_actionManager.getCompileAction());
      menu.add(_actionManager.getCompileStatisticsAction());
      menu.add(_actionManager.getShowExecuteFrameAction());

      _menuBar.add(menu);
   }

   private void createHelpMenu() {
      JMenu menu = new JMenu("Help");
      menu.add(_actionManager.getHelpIndexAction());
      menu.add(_actionManager.getAboutAction());
      _menuBar.add(menu);
   }


}
