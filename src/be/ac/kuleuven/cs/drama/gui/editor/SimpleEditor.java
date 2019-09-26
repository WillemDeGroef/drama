/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/SimpleEditor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.SourceLine;

/**
 * Editor class.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class SimpleEditor extends JPanel {
    private static final long serialVersionUID = 0L;

    private JScrollPane _scrollPane;
    private JPanel _innerPanel;

    private ColorLineTextPane _textPane;
    private JLabel _coordinates;

    private BreakPointCollection _breakPointCollection;
    private DocumentListener _bpListener;


    /**
     * Initialize
     */
    public SimpleEditor() {
        super();

        setLayout(new BorderLayout());

        _breakPointCollection = new BreakPointCollection(this);

        _innerPanel = new JPanel();
        _innerPanel.setLayout(new BorderLayout());

        _scrollPane = new JScrollPane(_innerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        _textPane = new ColorLineTextPane(Color.white, Color.blue);
        _textPane.setFont(new Font("monospaced", Font.PLAIN, 12));

        _textPane.addCaretListener(new CoordinateController());
        _coordinates = new JLabel("1:1");
        _innerPanel.add(_breakPointCollection.getBox(), BorderLayout.WEST);
        _innerPanel.add(_textPane, BorderLayout.CENTER);
        add(_scrollPane, BorderLayout.CENTER);
        add(_coordinates, BorderLayout.SOUTH);

        _bpListener = new MyDocumentListener();

        _textPane.getStyledDocument().addDocumentListener(_bpListener);
    }


    private class CoordinateController
            implements CaretListener {

        public void caretUpdate(CaretEvent ce) {
            _coordinates.setText(_textPane.getCurrentRow() + ":" + _textPane.getCurrentColumn());
        }

    }

    /**
     * set the text
     */
    public void setText(String text) {
        _textPane.getStyledDocument().removeDocumentListener(_bpListener);
        _breakPointCollection.clear();
        _textPane.setText(text);
        buildBreakPointCollection();
        _textPane.getStyledDocument().addDocumentListener(_bpListener);
        _breakPointCollection.setButtonHeight(_textPane.getLineHeight());

    }

    /**
     * @return the text
     */
    public String getText() {
        return _textPane.getText();
    }

    /**
     * toggle the breakpoint of the current line
     * no effect if disabled
     */
    public void toggleBreakPointOfCurrentLine() {
        toggleBreakPoint(_textPane.getCurrentRow());
    }

    /**
     * toggle the breakpoint of the given object code line
     */
    public void toggleBreakPoint(int realLineNumber) {
        _breakPointCollection.toggle(realLineNumber);
    }

    /**
     * @return whether the text has been edited
     */
    public boolean isDirty() {
        return _textPane.isDirty();
    }

    /**
     * set whether the text has been edited
     */
    public void setDirty(boolean state) {
        if ((!state) && isDirty()) {
            _breakPointCollection.clear();
            buildBreakPointCollection();
            enableBreakPoints();
        }

        _textPane.setDirty(state);
    }

    /**
     * @return a table of breakpoints
     * keys are Integer objects representing object code line numbers
     */
    public Hashtable getBreakPointList() {
        return _breakPointCollection.breakPoints();
    }

    public void buildBreakPointCollection() {

        _breakPointCollection.clear();

        if (_textPane.getText().length() == 0) {
            return;
        }

        try {
            TextUI mapper = _textPane.getUI();
            Rectangle r = mapper.modelToView(_textPane, 0);
            _breakPointCollection.setButtonHeight(r.height);
        } catch (Exception e) {
        }

        int lc = _textPane.getLineCount();

        for (int i = 1; i <= lc; i++) {
            _breakPointCollection.addLine();
            int lines = 0;

            try {
                lines = new SourceLine(_textPane.getLine(i), null).nbObjectLines();
            } catch (AbnormalTerminationException ate) {
            }

            if (lines != 1) {
                _breakPointCollection.disableLine(i);
            }

        }

    }

    /**
     * reset all text attributes to the default attributes
     */
    public void clearLineColor() {
        _textPane.applyDefaults();
    }

    /**
     * set the foreground color of the given source code line
     */
    public void setLineColor(int effectiveLineNumber, Color color) {
        setLineColor(effectiveLineNumber, color, null);
    }

    public void setLineColor(int effectiveLineNumber, Color foreground, Color background) {
        _textPane.setColorOfLine(getRealLineNumber(effectiveLineNumber), foreground, background);
    }

    private int getRealLineNumber(int effectiveLineNumber) {
        int realln = 0;
        int effectiveln = -1;
        int max = getText().split("\n").length;

        while (effectiveln < effectiveLineNumber) {
            if (++realln > max) {
                realln = -1;
                break;
            }

            try {
                effectiveln += new SourceLine(_textPane.getLine(realln), null).nbObjectLines();
            } catch (AbnormalTerminationException ate) {
                System.out.println("Programma moet gehercompileerd worden");
            }

        }

        return realln;

    }

    private int getEffectiveLineNumber(int realLineNumber) {

        int realln = 0;
        int effectiveln = -1;

        while (realln < realLineNumber) {
            realln += 1;

            try {
                effectiveln += new SourceLine(_textPane.getLine(realln), null).nbObjectLines();
            } catch (AbnormalTerminationException ate) {
                System.out.println("Programma moet gehercompileerd worden");
            }

        }

        return effectiveln;
    }

    /**
     * get the key for the breakpoint list
     * for the given source code line
     */
    public Object getKey(int line) {
        return getEffectiveLineNumber(line);
    }

    /**
     * enable the use of breakpoints
     */
    public void enableBreakPoints() {
        _breakPointCollection.clear();
        buildBreakPointCollection();
    }

    /**
     * disable the use of breakpoints
     */
    private void disableBreakPoints() {
        _breakPointCollection.disableAll();
    }

    public void setEditable(boolean on) {
        _textPane.setEditable(on);
    }

    public void setColors(Color fg, Color bg) {
        _textPane.setColors(fg, bg);
    }

    private class MyDocumentListener
            implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            disableBreakPoints();
        }

        public void removeUpdate(DocumentEvent e) {
            disableBreakPoints();
        }

        public void changedUpdate(DocumentEvent e) {
        }

    }

}
