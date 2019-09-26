/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/StreamMonitor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;

/**
 * Output device usable by other
 * machine components.
 *
 * @version 08/11/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public class StreamMonitor

        implements InternalMonitor {

    private final PrintWriter _out;
    private static final boolean DEBUG = false;

    public StreamMonitor(OutputStream stream) {
        _out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
    }

    /**
     * write the given drama number to the output
     */
    public void writeLong(long value) {
        if (DEBUG) System.out.println("PrintMonitor.writeLong()");

        _out.print(NumberFormat.toJavaNumber(value));
        _out.print('\t');
    }

    /**
     * write the given drama character to the output
     */
    public void writeChar(char value) {
        if (DEBUG) System.out.println("PrintMonitor.writeChar()");

        _out.print(Character.toString(value));
    }

    /**
     * write a newline to the output
     */
    public void newLine() {
        _out.println();
    }

    /**
     * write an input request to the output
     */
    public void requestInput() {
    }

    /**
     * write an input confirmation to the output
     */
    public void confirmInput(long value) {
    }


    public void close() {
        _out.close();
    }

    public void clear() {
    }

    public void delLastChar() {
    }

}
