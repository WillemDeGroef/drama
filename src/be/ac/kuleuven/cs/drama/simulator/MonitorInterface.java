/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/MonitorInterface.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator;

//import java.awt.TextArea;

import javax.swing.JTextArea;

/**
 * A MonitorInterface has to be implemented 
 * by monitors for the GUIController
 *
 * @version 1.0.0 08/09/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public abstract class MonitorInterface
        extends JTextArea {
    private static final long serialVersionUID = 0L;


    /**
     * Initialisation of the TextArea
     */
    protected MonitorInterface(String text, int rows, int columns /*, int scrollbars*/) {
        super(text, rows, columns /*, scrollbars */);
        this.setTabSize(6);
        this.setWrapStyleWord(true);
    }

    /**
     * Has to be implemented to run
     * in separate thread.
     *
     * IMPOSED BY DramaMachine
     */
    public void start() {
    }


}
