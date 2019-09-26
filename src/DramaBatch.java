/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/DramaBatch.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */

import be.ac.kuleuven.cs.drama.gui.DramaRuntime;
import be.ac.kuleuven.cs.drama.simulator.ControllableMachine;
import be.ac.kuleuven.cs.drama.simulator.simple.SimpleMachine;
import be.ac.kuleuven.cs.drama.vertalerpack.macro.MacroPreprocessor;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.Vertaler2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;


/**
 * DramaBatch : klasse voor batch verwerking
 *
 * @author Tom Vekemans
 * @version 1.0 09-APR-1999
 */

public class DramaBatch implements DramaRuntime {
    /**
     * debug mode ? (drukt extra boodschappen af)
     */
    public static final boolean debug = false;
    /**
     * continue: nodig voor lus
     */
    public boolean cont = true;

    // output for compile errors
    private PrintWriter errorFile = null;

    private boolean useErrorFile = false;

    /**
     * initialiseer een nieuwe DramaBatch
     */
    public DramaBatch() {
    }

    /**
     * main methode, opgeroepen vanaf de command prompt
     *
     * @param args[] typ java DramaBatch ? voor de mogelijke opties
     */
    public static void main(String[] args) {
        DramaBatch batch = new DramaBatch();
        batch.verwerk(args);
    }

    /**
     * druk een gegeven boodschap af naar System.out
     *
     * @param message de af te drukken boodschap
     * @param title   titel van de boodschap
     */
    public void showOKMessage(String message, String title) {
        System.out.println(title + ":");
        System.out.println(message);
        cont = false;
    }

    /**
     * druk een boodschap af
     *
     * @param message de af te drukken boodschap
     */
    public void systemMessage(String message) {
        System.out.println(message);

        //cont=false;
    }

    public void halted() {
    }

    /**
     * druk een boodschap af naar de foutstroom (System.err)
     *
     * @param message de af te drukken boodschap
     */
    public void systemErrorMessage(String message) {

        if (useErrorFile)
            errorFile.println(message);
        else
            System.err.println(message);

        cont = false;
    }

    /**
     * verwerk de gegeven parameters/programma's
     *
     * @param args[] de te verwerken argumenten (zie main() methode)
     */
    private void verwerk(String arg[]) {
        ControllableMachine machine = new SimpleMachine(this, false);

        System.out.println("Batch verwerking begint om " + getTime());

        boolean useInputFile = false;
        boolean runProgram = true;
        int sleepTime = 10000; //default sleep time per execution
        String sleepTimeString = "10";
        String globalInputFile = "";

        int nbOfArgs = arg.length;

        //Copy to Vector for easier handling
        Vector arguments = new Vector(nbOfArgs);

        for (int i = 0; i < nbOfArgs; i++) {
            arguments.add(arg[i]);
        }

        //First treatment of arguments
        cont = true;

        while (cont) {
            if (arguments.isEmpty()) {
                System.err.println("Er is geen DRAMA programma gespecifieerd!");
                System.exit(0);
            }

            String firstElement = (String) arguments.elementAt(0);

            if (firstElement.equals("-i")) {
                useInputFile = true;
            } else if (firstElement.equals("-c")) {
                runProgram = false;
            } else if (firstElement.equals("-t:")) {
                sleepTimeString = firstElement.substring(3);

                try {
                    sleepTime = Integer.parseInt(sleepTimeString);
                    sleepTime *= 1000;
                } catch (Exception e) {
                    sleepTime = 10000;
                }

            } else if (firstElement.startsWith("-g:")) {
                globalInputFile = firstElement.substring(3);
                useInputFile = true;
            } else if (firstElement.startsWith("-e")) {
                useErrorFile = true;
            } else if (firstElement.startsWith("-")) {
                System.err.println("Ongeldige optie: " + firstElement);

            } else if (firstElement.endsWith("?")) {
                printHelpText();
                System.exit(0);
            } else
                cont = false;
            if (cont)
                arguments.remove(0);
        }

        if (debug) {
            System.out.println("There are " + nbOfArgs + " arguments");

            for (int i = 0; i < nbOfArgs; i++) {
                System.out.println("arg (" + i + ") = " + arg[i]);
            }

            System.out.println("useInputFile=" + useInputFile);
            System.out.println("useErrorFile=" + useErrorFile);
            System.out.println("globalInputFile=" + globalInputFile);
            System.out.println("runProgram=" + runProgram);
            System.out.println("sleepTime=" + sleepTime);
            System.out.println("File(s) = " + arguments.toString());
        }

        //Vervolg
        File infile, outfile, mapfile;
        String filenaam, filenaam_zonder_ext, ext;

        MacroPreprocessor mvv;

        Vertaler2 vertaler;
        cont = true;

        while (!arguments.isEmpty()) {
            cont = true;
            filenaam = (String) arguments.elementAt(0);
            filenaam_zonder_ext = filenaam.substring(0, filenaam.length() - 4);
            ext = filenaam.substring(filenaam.lastIndexOf('.'));

            if (debug) System.out.println("Treating file " + filenaam);


            try {
                if (useErrorFile)
                    errorFile = new PrintWriter(new FileWriter(filenaam_zonder_ext + ".err"));
            } catch (IOException ioe) {
                systemErrorMessage(ioe.toString());
            }

            //Voorvertaling
            if (ext.toLowerCase().equals(".dra")) {
                System.out.println(filenaam + " wordt voorvertaald");
                infile = new File(filenaam);
                outfile = new File(filenaam_zonder_ext + ".pre");
                mapfile = new File(filenaam_zonder_ext + ".map");

                try {
                    mvv = new MacroPreprocessor(infile, outfile, mapfile);
                    mvv.process();
                    System.out.println("Voorvertaling van " + filenaam + " geslaagd");
                    filenaam = filenaam_zonder_ext + ".pre";
                    ext = ".pre"; //fixme: maybe not clean
                } catch (Exception e) {
                    systemErrorMessage(e.toString());
                }

            }

            //Vertaling
            if (cont && ext.toLowerCase().equals(".pre")) {
                System.out.println(filenaam + " wordt vertaald");
                infile = new File(filenaam);
                outfile = new File(filenaam_zonder_ext + ".out");
                File map = new File(filenaam_zonder_ext + ".map");

                try {
                    vertaler = new Vertaler2(infile, outfile, map);
                    vertaler.process();
                    System.out.println("Vertaling van " + filenaam + " geslaagd");
                    filenaam = filenaam_zonder_ext + ".out";
                    ext = ".out";
                } catch (Exception e) {
                    System.out.println("probleem met Vertaler");
                    systemErrorMessage(e.toString());
                }

            }

            //Uitvoering
            if (runProgram && cont) {
                machine = new SimpleMachine(this, false);

                if (useInputFile) {
                    if (!globalInputFile.isEmpty())
                        infile = new File(globalInputFile);
                    else
                        infile = new File(filenaam_zonder_ext + ".geg");

                    try {
                        machine.setInputStream(new BufferedInputStream(new FileInputStream(infile)));
                    } catch (FileNotFoundException fnfe1) {
                        try {
                            if (globalInputFile.isEmpty())
                                infile = new File(filenaam_zonder_ext + ".GEG");

                            machine.setInputStream(new BufferedInputStream(new FileInputStream(infile)));
                        } catch (FileNotFoundException fnfe2) {
                            System.out.println("Invoerbestand voor " + filenaam_zonder_ext + " niet gevonden:" +
                                    infile.getName());
                        }

                    }

                }

                try {
                    machine.setOutputStream(new BufferedOutputStream(new FileOutputStream(
                            new File(filenaam_zonder_ext + ".res"))));
                    machine.loadProgram(filenaam, new Hashtable());
                    machine.cont();

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ie) {
                        System.out.println("Batch execution interrupted");
                    }

                    machine.halt();
                } catch (IOException ioe) {
                    System.out.println("***Kon niet uitvoeren wegens " + ioe.toString());
                }

            }

            //next file
            arguments.remove(0);

            if (useErrorFile)
                errorFile.close();
        }

        System.out.println("Batch verwerking eindigt om " + getTime());

//        System.exit(0);
    }

    /**
     * schrijft de help-text van het DramaBatch programma naar de standaard uitvoer
     */
    private void printHelpText() {
        System.out.println("Gebruik : java DramaBatch [-optie1 [-optie2 [...]] bestandsnaam1 [bestandsnaam2[...]]");
        System.out.println("Elk bestand moet een .dra, .pre of .out extensie hebben !!");
        System.out.println("opties:");
        System.out.println(" -i   = gebruik invoerbestand van het programma (='bestandnaam.geg')");
        System.out.println(" -g:naam = gebruik dit bestand als invoerbestand voor alle programma\'s");
        System.out.println(" -e = creeer een bestand met compilatie fouten (bestandsnaam.err) voor alle programma's");
        System.out.println(" -c    = enkel vertaling, geen uitvoering");
        System.out.println(" -t:time = processortijd die elk programma krijgt toegewezen (default 10 seconden)");
        System.out.println(" -? = help (deze tekst)");
    }

    /**
     * geef de huidige tijd
     *
     * @return de actuele tijd (HH24:MM:SS)
     */
    public String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

}
