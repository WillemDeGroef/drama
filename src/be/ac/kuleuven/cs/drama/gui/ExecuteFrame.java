/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ExecuteFrame.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import be.ac.kuleuven.cs.drama.gui.editor.SimpleEditor;

/**
 * Execute frame.
 *
 * @version 0.5.0 09/05/2000
 * @author  Tom Schrijvers
 */

public class ExecuteFrame

   extends JFrame {
	private static final long serialVersionUID = 0L;

   private ActionManager _actionManager;
   
   private SimpleEditor _simpleEditor;

   private JTextArea _statusWindow;
   private JPanel _panel;
   
   private String _fileTitle = "Naamloos";
   private ExecutionState _state = ExecutionState.NO_CODE;
   
   private int _currentInstructionAddress = -1;
   private int _currentBT = -1;

   public ExecuteFrame(ActionManager _actionManager) {
      this._actionManager = _actionManager;
      updateFrameTitle();
      init(_actionManager);
      setSize(800, 500);
   }
   
   public void setFileTitle(String fileTitle) {
      _fileTitle = fileTitle;
      updateFrameTitle();
   }
   
   public void setExecutionState(ExecutionState state) {
      _state = state;
      updateFrameTitle();
      _actionManager.getContinueAction().setEnabled((state.isInitial() || state.isStarted()) && !state.isExecuting());
      _actionManager.getStepAction().setEnabled((state.isInitial() || state.isStarted()) && !state.isExecuting());
      _actionManager.getStopAction().setEnabled(state.isExecuting());
      _actionManager.getResetAction().setEnabled(state.canExecute() && !state.isInitial() && !state.isExecuting());
      _actionManager.getResetAction().setEnabled(true);
      ((SimpleAction)_actionManager.getContinueAction()).putValue(SimpleAction.SHORT_DESCRIPTION, state.isStarted() ? "Doorgaan" : "Starten");
      ((SimpleAction)_actionManager.getContinueAction()).putValue(SimpleAction.LONG_DESCRIPTION, state.isStarted() ? "Doorgaan" : "Starten");
      
      updateHighlights();
   }
   
   public void updateFrameTitle() {
      setTitle("DramaSimulator - Uitvoering - " + _fileTitle + " - [" + _state.getName() + "]");
   }

   private void init(ActionManager _actionManager) {
      getContentPane().setLayout(new BorderLayout());

      JToolBar toolBar = new ExecuteFrameToolBarFactory(_actionManager).getToolBar();
      getContentPane().add(toolBar, BorderLayout.NORTH);

      JMenuBar menuBar = new ExecuteFrameMenuFactory(_actionManager).getMenuBar();
      setJMenuBar(menuBar);

      _panel = new JPanel();
      _panel.setLayout(new BorderLayout());

      JSplitPane innerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _panel, createSimpleEditor());

      JSplitPane outerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerSplitPane, createStatusWindow());
      outerSplitPane.setResizeWeight(.8);
      getContentPane().add(outerSplitPane, BorderLayout.CENTER);
   }

   private JPanel createSimpleEditor() {
      _simpleEditor = new SimpleEditor();
      _simpleEditor.setEditable(false);
      _simpleEditor.setColors(Color.black, Color.white);

      this.addWindowListener(
         new WindowAdapter() {
            public void windowOpened(WindowEvent we) {
               _simpleEditor.buildBreakPointCollection();
            }
         }
      );

      return _simpleEditor;
   }

   private JScrollPane createStatusWindow() {

      _statusWindow = new JTextArea();
      _statusWindow.setEditable(false);
      _statusWindow.addMouseListener(new ClearingMouseListener(_statusWindow));
      _statusWindow.setRows(5);

      JScrollPane scrollPane = new JScrollPane(_statusWindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      return scrollPane;
   }

   public void insertInputComponent(JComponent input) {
      _panel.add(input, BorderLayout.SOUTH);
   }

   public void insertOutputComponent(JTextArea output) {
      JScrollPane scroll = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      output.addMouseListener(new ClearingMouseListener(output));
      _panel.add(scroll, BorderLayout.CENTER);
   }

   public void statusMessage(String message) {
      _statusWindow.append(message);
      _statusWindow.setCaretPosition(_statusWindow.getText().length());
      _statusWindow.append("\n");
   }

   public void setText(String text) {
      _simpleEditor.setText(text);
   }

   public Hashtable getBreakPoints() {
      return _simpleEditor.getBreakPointList();
   }

   public void toggleBreakPointOfCurrentLine() {
      _simpleEditor.toggleBreakPointOfCurrentLine();
   }

   public void currentInstructionChanged(int address) {
      _currentInstructionAddress = address;
      updateHighlights();
   }

   public void updateHighlights() {
      _simpleEditor.clearLineColor();
      if (0 <= _currentInstructionAddress)
         _simpleEditor.setLineColor(_currentInstructionAddress, Color.red);
      if (_state.isStarted() && !_state.isExecuting() && _currentBT >= 0)
         _simpleEditor.setLineColor(_currentBT, null, Color.yellow);
   }

   void programLoaded() {
      setExecutionState(ExecutionState.NOT_STARTED);
   }

   void programRunning() {
      setExecutionState(ExecutionState.RUNNING);
   }

   void programStepping() {
      setExecutionState(ExecutionState.STEPPING);
   }

   void programHalted(int bt) {
      _currentBT = bt;
      setExecutionState(ExecutionState.SUSPENDED);
   }

   void programFinished() {
      setExecutionState(ExecutionState.FINISHED);
   }

   public void programLoaded(File outFile, String text) {
      setFileTitle(outFile.getName());
      _simpleEditor.setText(text);
      setExecutionState(ExecutionState.NOT_STARTED);
   }

}
