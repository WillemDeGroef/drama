/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/SymbolTableParser.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.util.List;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

/**
 * Parses an object code file to load the symbol table
 * to allow the machine visualisation to display label names.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class SymbolTableParser {

   private String[] _labels;
   private int[] _addresses;

   /**
    * Initialize
    */
   public SymbolTableParser() {}

   /**
    * Parse the given file.
    */
   public void parseFile(String file) {
      List labels = new ArrayList();
      List addresses = new ArrayList();

      try {

         BufferedReader in = new BufferedReader(new FileReader(file));

         String line = null;

         while ( (line = in.readLine()) != null) {
            parseLine(line, labels, addresses);
         }

      } catch (IOException ioe) {
         System.out.println("Fout in symbooltabel");
      }


      _labels = new String[labels.size()];
      _addresses = new int[addresses.size()];

      for (int i = 0; i < _labels.length; i++) {
         _labels[i] = (String) labels.get(i);
         _addresses[i] = ((Integer) addresses.get(i)).intValue();
      }

   }

   /*
    * parse a line
    */
   private void parseLine(String line, List labels, List addresses) {

      //System.out.println(line);

      if (line.startsWith("#symbool")) {

         //System.out.println(line);

         addresses.add(new Integer(line.substring(line.length() - 4)));

         labels.add(StringUtils.trimSpaces(line.substring(9, 43)));

      }

   }

   /**
    * @return the labels of the most recently parsed file
    */
   public String[] getLabels() {
      return _labels;
   }

   /**
    * @return the addresses of the labels of the most recently parsed file
    */
   public int[] getAddresses() {
      return _addresses;
   }

}
