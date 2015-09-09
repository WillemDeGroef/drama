/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ExecuteFrameMenuFactory.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
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
 * Creates the menu bar of the
 * execute frame.
 *
 * @version 1.0.0 09/06/2000
 * @author  Tom Schrijvers
 */

public class ExecuteFrameMenuFactory {

   private final ActionManager _actionManager;
   private JMenuBar _menuBar;

   public ExecuteFrameMenuFactory(ActionManager actionManager) {
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

      addExecuteMenu();
      addVisualisationMenu();
      addBuildMenu();
      addHelpMenu();
   }

   private void addExecuteMenu() {
      JMenu menu = new JMenu("Uitvoering");
      menu.add(_actionManager.getContinueAction());
      menu.add(_actionManager.getStepAction());
      menu.add(_actionManager.getStopAction());
      menu.add(_actionManager.getResetAction());
      menu.add(_actionManager.getClearFrameAction());
      menu.add(_actionManager.getRunStatisticsAction());
      menu.addSeparator();
      menu.add(_actionManager.getCloseExecuteFrameAction());
      _menuBar.add(menu);
   }

   private void addVisualisationMenu() {
      JMenu menu = new JMenu("Visualisatie");
      menu.add(_actionManager.getInternalMachineAction());
      menu.add(_actionManager.getCpuAction());

      JMenu complexityMenu = new JMenu("Complexiteit");
      complexityMenu.add(_actionManager.getSimpleComplexityAction());
      complexityMenu.add(_actionManager.getComplexComplexityAction());
      menu.add(complexityMenu);

      _menuBar.add(menu);
   }

   private void addBuildMenu() {
      JMenu menu = new JMenu("Machine");
      menu.add(_actionManager.getBuildMachineAction());

      JMenu inputMenu = new JMenu("Invoer");
      inputMenu.add(_actionManager.getInputFromScreenAction());
      inputMenu.add(_actionManager.getInputFromFileAction());
      menu.add(inputMenu);

      JMenu outputMenu = new JMenu("Uitvoer");
      outputMenu.add(_actionManager.getOutputToScreenAction());
      outputMenu.add(_actionManager.getOutputToFileAction());
      menu.add(outputMenu);

      _menuBar.add(menu);
   }

   private void addHelpMenu() {
      JMenu menu = new JMenu("Help");
      menu.add(_actionManager.getHelpIndexAction());
      menu.add(_actionManager.getAboutAction());
      _menuBar.add(menu);
   }

}
