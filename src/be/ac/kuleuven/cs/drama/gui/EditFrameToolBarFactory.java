/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/EditFrameToolBarFactory.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import javax.swing.JToolBar;
import javax.swing.JButton;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * This class manages the creation
 * of the toolbar of the edit frame.
 *
 * @version 0.2.0 09/04/2000
 * @author  Tom Schrijvers
 */

public class EditFrameToolBarFactory {

   private final ActionManager _actionManager;
   private JToolBar _toolBar;

   /**
    * Init a new EditFrameToolBarFactory
    */
   public EditFrameToolBarFactory(ActionManager actionManager) {
      _actionManager = actionManager;
   }

   /**
    * returns the toolbar that is managed
    * @return the managed toolbar
    */
   public JToolBar getToolBar() {
      if (_toolBar == null) {
         createToolBar();
      }

      return _toolBar;
   }

   /*
    * Create the toolbar.
    * Call only once !
    */
   private void createToolBar() {
      _toolBar = new JToolBar();
      addNewFile();
      addOpenFile();
      addSaveFile();
      _toolBar.addSeparator();
      addPrecompile();
      addCompile();
      addShowExecuteFrame();
   }

   private void addNewFile() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getNewFileAction());
      _toolBar.add(button);
   }

   private void addOpenFile() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getOpenFileAction());
      _toolBar.add(button);
   }


   private void addSaveFile() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getSaveFileAction());
      _toolBar.add(button);
   }

   private void addPrecompile() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getPrecompileAction());
      _toolBar.add(button);
   }

   private void addCompile() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getCompileAction());
      _toolBar.add(button);
   }

   private void addShowExecuteFrame() {
      JButton button = new ToolBarButton();
      button.setAction(_actionManager.getShowExecuteFrameAction());
      _toolBar.add(button);
   }

}
