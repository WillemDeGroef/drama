/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ExecuteFrameToolBarFactory.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import javax.swing.JToolBar;
import javax.swing.JButton;

/**
 * Factory for the toolbar of the execute frame.
 *
 * @version 1.0.0 09/05/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

public class ExecuteFrameToolBarFactory {

   private final ActionManager _actionManager;
   private JToolBar _toolBar;

   public ExecuteFrameToolBarFactory(ActionManager actionManager) {
      _actionManager = actionManager;
   }

   public JToolBar getToolBar() {
      if (_toolBar == null) {
         createToolBar();
      }

      return _toolBar;
   }

   private void createToolBar() {
      _toolBar = new JToolBar();

      addContinue();
      addStep();
      addStop();
      addReset();
      addClear();
      _toolBar.addSeparator();
      addInternalMachine();
      addCpu();
      _toolBar.addSeparator();
      addBuildMachine();

   }

   private void addContinue() {
      JButton button = new ToolBarButton(_actionManager.getContinueAction());
      _toolBar.add(button);
   }

   private void addStep() {
      JButton button = new ToolBarButton(_actionManager.getStepAction());
      _toolBar.add(button);
   }

   private void addStop() {
      JButton button = new ToolBarButton(_actionManager.getStopAction());
      _toolBar.add(button);

   }

   private void addReset() {
      JButton button = new ToolBarButton(_actionManager.getResetAction());
      _toolBar.add(button);
   }

   private void addInternalMachine() {
      JButton button = new ToolBarButton(_actionManager.getInternalMachineAction());
      _toolBar.add(button);
   }

   private void addCpu() {
      JButton button = new ToolBarButton(_actionManager.getCpuAction());
      _toolBar.add(button);
   }

   private void addBuildMachine() {
      JButton button = new ToolBarButton(_actionManager.getBuildMachineAction());
      _toolBar.add(button);
   }
   
   private void addClear() {
	   JButton button = new ToolBarButton(_actionManager.getClearFrameAction());
	   _toolBar.add(button);
   }

}
