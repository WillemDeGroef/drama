/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/EditFrame.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

import be.ac.kuleuven.cs.drama.gui.codecoloring.IdeColorSet;
import be.ac.kuleuven.cs.drama.gui.codecoloring.TextDecorator;
import be.ac.kuleuven.cs.drama.gui.editor.ColorLineTextPane;

/**
 * The edit frame.
 *
 * @version 0.6.0 09/06/2000
 * @author Tom Schrijvers
 */

public class EditFrame extends JFrame {
    private static final long serialVersionUID = -8495118020391480328L;

    private ColorLineTextPane _textPane;
    private JTextArea _statusWindow;

    private TextDecorator decorator;

    public EditFrame(ActionManager _actionManager) {
        super("DramaSimulator - Editor - Naamloos");
        init(_actionManager);
        setSize(800, 500);
    }

    private void init(final ActionManager _actionManager) {
        _actionManager.setIdeBlackWhiteAction(new ActionImpl() {
            public void react() {
                setDecorator((byte) 0);
            }
        });
        _actionManager.setIdeLightAction(new ActionImpl() {
            public void react() {
                setDecorator((byte) 1);
            }
        });
        _actionManager.setIdeDarkAction(new ActionImpl() {
            public void react() {
                setDecorator((byte) 2);
            }
        });

        getContentPane().setLayout(new BorderLayout());

        JToolBar toolBar = new EditFrameToolBarFactory(_actionManager).getToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);

        JMenuBar menuBar = new EditFrameMenuFactory(_actionManager).getMenuBar();
        setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTextArea(), createStatusWindow());
        splitPane.setResizeWeight(1);

        getContentPane().add(splitPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                _actionManager.getQuitAction().actionPerformed(null);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.decorator = new TextDecorator(this._textPane, new IdeColorSet(Settings.getIdeColors()));
    }

    private JPanel createTextArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JLabel label = new JLabel("Huidige plaats:  ( 1 , 1 )");
        label.setHorizontalAlignment(JLabel.RIGHT);

        _textPane = new ColorLineTextPane(Color.black, Color.white);
        _textPane.setFont(new Font("Courier", Font.BOLD, 12));

        _textPane.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

        // Create the right-click menu
        JPopupMenu popup = new JPopupMenu();
        JMenuItem item;
        item = new JMenuItem("Knippen");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _textPane.getTransferHandler().exportToClipboard(_textPane, Toolkit.getDefaultToolkit().getSystemClipboard(), TransferHandler.COPY);
                _textPane.replaceSelection("");
            }
        });
        popup.add(item);
        item = new JMenuItem("Kopi�ren");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _textPane.getTransferHandler().exportToClipboard(_textPane, Toolkit.getDefaultToolkit().getSystemClipboard(), TransferHandler.COPY);
            }
        });
        popup.add(item);
        item = new JMenuItem("Plakken");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _textPane.getTransferHandler().importData(_textPane, Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null));
            }
        });
        popup.add(item);
        popup.addSeparator();
        item = new JMenuItem("Ongedaan maken");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _textPane.getActionMap().get("Undo").actionPerformed(null);
            }
        });
        popup.add(item);
        item = new JMenuItem("Overdoen");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _textPane.getActionMap().get("Redo").actionPerformed(null);
            }
        });
        popup.add(item);
        _textPane.setComponentPopupMenu(popup);

        // Make IDE color the text with a delay
        _textPane.getDocument().addDocumentListener(new DocumentListener() {
            private Timer timer = new Timer();
            private TimerTask task = null;

            public void changedUpdate(DocumentEvent arg0) {
            }

            public void insertUpdate(DocumentEvent arg0) {
                this.notifyUpdate();
            }

            public void removeUpdate(DocumentEvent arg0) {
                this.notifyUpdate();
            }

            private void notifyUpdate() {
                if (task != null)
                    task.cancel();
                timer.purge();
                task = new TimerTask() {
                    public void run() {
                        decorator.decorate();
                    }
                };
                timer.schedule(task, 500L);
            }
        });

        // Enable Ctrl+Z and Ctrl+Y
        final UndoManager undoMngr = new UndoManager();
        _textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent arg0) {
                if (arg0.getEdit().getPresentationName().equals("addition") || arg0.getEdit().getPresentationName().equals("deletion"))
                    undoMngr.addEdit(arg0.getEdit());
            }
        });
        _textPane.getActionMap().put("Undo", new AbstractAction() {
            private static final long serialVersionUID = 0L;

            public void actionPerformed(ActionEvent arg0) {
                if (undoMngr.canUndo())
                    undoMngr.undo();
            }
        });
        _textPane.getActionMap().put("Redo", new AbstractAction() {
            private static final long serialVersionUID = 0L;

            public void actionPerformed(ActionEvent arg0) {
                if (undoMngr.canRedo())
                    undoMngr.redo();
            }
        });
        _textPane.getActionMap().put("Reset", new AbstractAction() {
            private static final long serialVersionUID = 0L;

            public void actionPerformed(ActionEvent arg0) {
                undoMngr.discardAllEdits();
            }
        });
        _textPane.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        _textPane.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");

        // Update text in bottom right corner, indicating cursor position
        _textPane.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent ce) {
                label.setText("Huidige plaats:  ( " + _textPane.getCurrentRow() + " , " + _textPane.getCurrentColumn() + " )");
            }
        });

        JScrollPane scrollPane = new JScrollPane(_textPane,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(label, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane createStatusWindow() {

        _statusWindow = new JTextArea();
        _statusWindow.setEditable(false);
        _statusWindow.addMouseListener(new ClearingMouseListener(_statusWindow));
        _statusWindow.setRows(5);

        return new JScrollPane(_statusWindow,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

    private void setDecorator(byte theme) {
        this.decorator = new TextDecorator(this._textPane, new IdeColorSet(theme));
        Settings.setIdeColors(theme);
    }

    public void reset() {
        _textPane.getActionMap().get("Reset").actionPerformed(null);
    }
}
