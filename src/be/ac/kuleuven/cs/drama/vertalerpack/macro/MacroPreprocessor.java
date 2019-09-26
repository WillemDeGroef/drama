/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroPreprocessor.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Macropreprocessor.
 *
 * @version 1.0.0 08/29/2000
 * @author Tom Schrijvers
 */

public class MacroPreprocessor {

    private final File _inFile;
    private final File _outFile;
    private final File _mapFile;

    /**
     * Initialise a new preprocessor that reads from given input file and
     * writes to given output file.
     */
    public MacroPreprocessor(File input, File output, File mapfile) {
        _inFile = input;
        _outFile = output;
        _mapFile = mapfile;
    }


    /**
     * process the input file and write the output file
     */
    public void process()
            throws IOException, AbnormalTerminationException {
        BufferedReader in = new BufferedReader(new FileReader(_inFile));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(_outFile)));
        ObjectOutputStream map = new ObjectOutputStream(new FileOutputStream(_mapFile));

        try {
            ExpansionManager expander = new MacroDefiner(in).process();
            expander.expand(out);
            map.writeObject(expander.getLineMap());
        } finally {
            in.close();
            out.close();
            map.close();
        }

    }

}
