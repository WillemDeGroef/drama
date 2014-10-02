/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/EditFrame.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.gui.editor.ColorLineTextPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentListener;

/**
 * The edit frame.
 *
 * @version 0.6.0 09/06/2000
 * @author  Tom Schrijvers
 */

public class EditFrame

   extends JFrame {
   private ColorLineTextPane _textPane;
   private JTextArea _statusWindow;

   public EditFrame(ActionManager _actionManager) {
      super("DramaSimulator - Editor - Naamloos");
      init(_actionManager);
      setSize(800, 500);
   }

   private void init(final ActionManager _actionManager) {
      getContentPane().setLayout(new BorderLayout());

      JToolBar toolBar = new EditFrameToolBarFactory(_actionManager).getToolBar();
      getContentPane().add(toolBar, BorderLayout.NORTH);

      JMenuBar menuBar = new EditFrameMenuFactory(_actionManager).getMenuBar();
      setJMenuBar(menuBar);

      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTextArea(), createStatusWindow());
      splitPane.setResizeWeight(1);

      getContentPane().add(splitPane, BorderLayout.CENTER);

      addWindowListener(
         new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
               _actionManager.getQuitAction().actionPerformed(null);
            }

         }
      );

      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE );
   }

   private JPanel createTextArea() {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());

      final JLabel label = new JLabel("0:0");
      label.setHorizontalAlignment(JLabel.RIGHT);

      _textPane = new ColorLineTextPane(Color.black, Color.white);
      _textPane.setFont(new Font("Courier", Font.PLAIN, 12));

      _textPane.addCaretListener(new CaretListener() {
                                    public void caretUpdate(CaretEvent ce) {
                                       label.setText(_textPane.getCurrentRow() + ":" + _textPane.getCurrentColumn());
                                    }

                                 }

                                );

      JScrollPane scrollPane = new JScrollPane(_textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      panel.add(label, BorderLayout.SOUTH);
      panel.add(scrollPane, BorderLayout.CENTER);
      return panel;
   }

   private JScrollPane createStatusWindow() {

      _statusWindow = new JTextArea();
      _statusWindow.setEditable(false);
      _statusWindow.addMouseListener(new ClearingMouseListener(_statusWindow));
      _statusWindow.setRows(5);

      JScrollPane scrollPane = new JScrollPane(_statusWindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      return scrollPane;
   }

   public void setText(String text) {
      _textPane.setText(text);
   }

   public String getText() {
      return _textPane.getText();
   }

   public void statusMessage(String message) {
      _statusWindow.append(message);
      _statusWindow.setCaretPosition(_statusWindow.getText().length());
      _statusWindow.append("\n");
   }

   public void clearStatusWindow() {
      _statusWindow.setText("");
   }

   public void addDocumentListener(DocumentListener dl) {
      _textPane.addDocumentListener(dl);
   }

}
