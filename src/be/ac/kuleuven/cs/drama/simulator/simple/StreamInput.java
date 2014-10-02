/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/StreamInput.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.simulator.basis.NumberFormat;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * StreamInput is an input handler that only handles
 * input from a stream.
 *
 * The input from outside is not used.
 *
 * @version 1.0.0 08/14/2000
 * @author  Tom Schrijvers
 */

public class StreamInput

   implements MachineInput {

   private final InternalMachine _machine;
   private final BufferedReader _reader;

   /**
    * Initialize a new StreamInput for the given machine.
    */
   public StreamInput(InternalMachine machine, InputStream stream) {
      _machine = machine;
      _reader = new BufferedReader(new InputStreamReader(stream));
   }

   /**
    * Cannot reset a stream.
    */
   public void reset() {}

   /**
    * @return a long read from the stream
    */
   public long readLong() {
      _machine.monitor().requestInput();

      String line = null;

      try {
         line = _reader.readLine();
      } catch (IOException ioe) {}








      if (line == null) {
         throw new FatalMachineError("Geen invoer beschikbaar.");
      }

      try {

         long result = Long.parseLong(StringUtils.trimSpaces(line));
         result = NumberFormat.toSafeDramaNumber(result);
         _machine.monitor().confirmInput(result);
         return result;
      } catch (NumberFormatException nfe) {
         throw new FatalMachineError("Ongeldige invoer: " + line);
      }

   }

   /**
    * Handle the given input.
    * No input is used.
    */

   public void setInput(String input) {}








}
