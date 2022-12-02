/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleRAM.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.simulator.Memory;
import be.ac.kuleuven.cs.drama.simulator.basis.DramaRegister;
import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import be.ac.kuleuven.cs.drama.events.MemoryEventManager;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Implementation of a RAM memory component
 *
 * @version 1.0.0 08/11/2000
 * @author  Tom Schrijvers
 */

public class SimpleRAM
   implements Memory {
   // the memory cells
   private final DramaRegister[] _cell;

   /** Initialize a new SimpleRAM with a given number of
    * cells.
    */
   public SimpleRAM(int size) {
      _cell = new DramaRegister[size];

      for (int index = 0; index < size; index++) {
         _cell[index] = new DramaRegister();
      }

   }

   /**
    * @reset the cells of this RAM
    */
   public void reset() {
      for (int i = 0; i < _cell.length; i++) {
         setCell(i, 0);
      }

   }

   /**
    * Set the contents of the given cell.
    */
   public void setCell(int address, long value) {
      checkValue(value);
      checkAddress(address);
      _cell[address].setValue(value);

      MemoryEventManager.fireEvent(address, value);
   }

   /**
    * @return the contents of the given cell
    */
   public long getCell(int address) {
      //System.out.println("getCell("+address+")");
      checkAddress(address);
      return _cell[address].getValue();
   }

   // just another alias :)
   public long cell(int address) {
      return getCell(address);
   }

   private void checkValue(long value) {
      if (! NumberFormat.isDramaNumber(value)) {
         throw new FatalMachineError( "Ongeldige waarde voor geheugencel:" + value);
      }

   }

   private void checkAddress(int address) {
      if (address < 0 || address >= _cell.length) {
         throw new FatalMachineError( "Ongeldig geheugenadres: " + address);
      }

   }

   /**
    * Load the program in the specified file
    * into this RAM starting from address 0.
    */
   public void loadProgram(String filename) {
      try {
         File file = new File(filename);
         BufferedReader reader = new BufferedReader(new FileReader(file));

         String line = null;
         int lineNumber = 0;

         while ((line = reader.readLine()) != null) {

            lineNumber = handleLine(line, lineNumber);

         }
         
         reader.close();
      } catch (IOException ioe) {
         throw new FatalMachineError( "Fout bij het inlezen van het programma.");
      }

   }

   /*
    * handle a program line of an input file
    */
   private int handleLine(String line, int lineNumber) {

      line = StringUtils.trimSpaces(line).toLowerCase();

      if (line.startsWith("#symbool")) {
         return 0;
      }

      if (line.startsWith("#locatie")) {
         return Integer.parseInt(StringUtils.trimLeftSpaces(line.substring("#locatie ".length())));
      }

      long cellValue = Long.parseLong(line);
      setCell(lineNumber, cellValue);
      return lineNumber + 1;
   }

   // MEMORY INTERFACE

   public long getData(int address) {
      return getCell(address);
   }

}
