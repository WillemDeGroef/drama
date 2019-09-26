/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/Vertaler2.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.util.StatistiekModule;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Vertaler2 generates a decimal code file from
 * a mnemonic source code file.
 * <p>
 * This class replaces the Vertaler class.
 *
 * @author Tom Schrijvers
 * @version 1.0.0 08/03/2000
 */

public class Vertaler2 {
    // input file
    private final File _in;
    // output file
    private final File _out;
    private final ArrayList<Integer> _linemap;
    // symbol table
    private final Map<String, Integer> _symbolTable = new HashMap<>();
    // the source lines
    private final List<SourceLine> _sourceLines = new ArrayList<>();
    // the statistics
    private StatistiekModule _statistics;

    /**
     * Initialize a new Vertaler.
     *
     * @param inputfile  the source file (.pre)
     * @param outputfile the output file (.uit)
     */
    public Vertaler2(File inputfile, File outputfile, File mapfile)
            throws AbnormalTerminationException, IOException {
        _in = inputfile;
        _out = outputfile;
        ObjectInputStream ins = new ObjectInputStream(new FileInputStream(mapfile));

        try {
            _linemap = (ArrayList<Integer>) ins.readObject();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            ins.close();
        }
    }

    public int translateLine(int line) {
        try {
            return _linemap.get(line - 1);
        } catch (Exception e) {
            System.out.println("*** " + e);
            return -1;
        }

    }

    /**
     * Read from the input file, build the symbol table
     * and write to the output file.
     */
    public void process()
            throws IOException, AbnormalTerminationException {
        _statistics = new StatistiekModule();

        readLines();
        buildSymbolTable();
        writeLines();

        _statistics.writeFile("compstat.txt");
    }

    public StatistiekModule statistics() {
        return _statistics;
    }

    /*
     * Read the DramaSourceLines into the _sourceLinesList
     */
    private void readLines()
            throws IOException {
        BufferedReader inReader = new BufferedReader(new FileReader(_in));
        String line;

        while ((line = inReader.readLine()) != null) {
            _sourceLines.add(new SourceLine(line, this));
        }

        inReader.close();
    }

    /*
     * build the _symbolTable from the _sourceLines
     */
    private void buildSymbolTable()
            throws AbnormalTerminationException {
        int objectln = 0;
        int sourceln = 0;

        try {
            for (; sourceln < _sourceLines.size(); sourceln++) {
                SourceLine line = _sourceLines.get(sourceln);

                if (line.hasLabel()) {
                    addSymbolDefinition(line.getLabel(), objectln);
                }

                objectln += line.nbObjectLines();

                if (line.isLastProgramLine()) {
                    break;
                }

            }
        } catch (AbnormalTerminationException ate) {
            //     ate.printStackTrace();
            throw new AbnormalTerminationException(sourceln + 1, translateLine(sourceln + 1),
                    ate.getMessage());
        }

        //System.out.println("Objectlines = " + objectln);
        if (objectln == 0) {
            throw new AbnormalTerminationException("Geen programma gedefinieerd!");
        }

    }


    /*
     * write the decimal object code to file
     */
    private void writeLines()
            throws IOException, AbnormalTerminationException {
        PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(_out)));
        int sourceln = 0;
        int objectln = 0;

        try {
            while (sourceln < _sourceLines.size()) {
                SourceLine sourceline = _sourceLines.get(sourceln);
                String[] lines = sourceline.getObjectLines(objectln);

                for (String line : lines) {
                    outWriter.println(line);
                }

                if (sourceline.isLastProgramLine()) {
                    break;
                }

                objectln += sourceline.nbObjectLines();
                sourceln++;
            }
        } catch (AbnormalTerminationException ate) {
            //ate.printStackTrace();
            throw new AbnormalTerminationException(sourceln + 1, translateLine(sourceln + 1),
                    ate.getMessage());
        }

        writeSymbolTable(outWriter);
        outWriter.close();
    }

    /**
     * @param symbol the symbol name
     * @return the numeric value of the symbol
     * @throws AbnormalTerminationException the symbol is not defined
     */
    public int getSymbolValue(String symbol)
            throws AbnormalTerminationException {
        Integer result = _symbolTable.get(symbol.toUpperCase());

        if (result == null)
            throw new AbnormalTerminationException("symbool " + symbol + " niet gedefinieerd");

        return result;

    }

    /**
     * Define a new symbol.
     *
     * @param symbol the new symbol
     * @param value  the numeric value of the new symbol
     * @throws AbnormalTerminationException the symbol is already defined
     */
    private void addSymbolDefinition(String symbol, int value)
            throws AbnormalTerminationException {
        if (_symbolTable.containsKey(symbol.toUpperCase()))
            throw new AbnormalTerminationException("symbool " + symbol + " al gedefinieerd");
        _symbolTable.put(symbol.toUpperCase(), value);
    }

    /*
     * write the symbol table to the given PrintWriter
     */
    private void writeSymbolTable(PrintWriter out)
            throws IOException {

        List<KeyValuePair<String, Integer>> list = new ArrayList<>();

        for (String key : _symbolTable.keySet()) {
            list.add(new KeyValuePair<>(key, _symbolTable.get(key)));
        }

        Collections.sort(list);

        for (KeyValuePair<String, Integer> kvPair : list) {
            out.println(buildLine(kvPair.key(), kvPair.value()));

        }

    }

    private class KeyValuePair<K, V> implements Comparable {

        private final K _key;
        private final V _value;

        public KeyValuePair(K key, V value) {
            _key = key;
            _value = value;
        }

        public K key() {
            return _key;
        }

        public V value() {
            return _value;
        }

        public int compareTo(Object obj) {
            return ((Comparable) value()).compareTo(((KeyValuePair) obj).value());
        }

    }

    /**
     * @return a line for the symbol table
     */
    private String buildLine(Object key, Object value) {
        StringBuilder builder = new StringBuilder();
        builder.append("#symbool ");

        String symb = key.toString();
        String adr = value.toString();

        adr = StringUtils.prependChars(adr, '0', 10);
        adr = StringUtils.prependChars(adr, ' ', 44 - symb.length());
        builder.append(symb);
        builder.append(adr);

        return builder.toString();
    }

}
