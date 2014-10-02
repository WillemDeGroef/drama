/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleInstructionDecoder.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.simulator.simple.decoders.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Decoder(factory) of all drama instructions.
 *
 * @version 1.0.0 08/09/2000
 * @author  Tom Schrijvers
 */

public class SimpleInstructionDecoder {

   private final InternalMachine _machine;

   /**
    * Initialize a new SimpleInstructionDecoder for the given machine.
    */
   public SimpleInstructionDecoder(InternalMachine machine) {
      _machine = machine;
   }

   /**
    * Decode the current instruction of the machine.
    */
   public void decode() {
      Instruction instruction = new Instruction(_machine.cpu().currentInstruction());
      OpcodeDecoder decoder = getDecoder(instruction);

      if (decoder != null) {
         decoder.decode(instruction, _machine);
      } else {
         throw new FatalMachineError("Niet decodeerbare instructie: " + instruction);
      }

   }

   /**
    * @return the OpcodeDecoder for the given instruction
    */
   private OpcodeDecoder getDecoder(Instruction instruction) {
      return getDecoder(instruction.opcode());
   }

   /**
    * @return the opcode decoder for the given opcode
    */
   private OpcodeDecoder getDecoder(int opcode) {
      return (OpcodeDecoder) _decoders.get(new Integer(opcode));
   }

   // map of opcode decoders
   private static final Map _decoders;

   static{

      // init en fill the map of opcode decoders

      _decoders = new HashMap();
      addDecoder(new HIADecoder());

      addDecoder(new OPTDecoder());
      addDecoder(new AFTDecoder());
      addDecoder(new VERDecoder());
      addDecoder(new DELDecoder());
      addDecoder(new MODDecoder());

      addDecoder(new BIGDecoder());

      addDecoder(new VGLDecoder());

      addDecoder(new VSPDecoder());
      addDecoder(new SPRDecoder());

      addDecoder(new SBRDecoder());
      addDecoder(new KTGDecoder());

      addDecoder(new LEZDecoder());
      addDecoder(new DRUDecoder());
      addDecoder(new NWLDecoder());
      addDecoder(new NTSDecoder());

      addDecoder(new STPDecoder());
   }

   /*
    * add the given opcode decoder to the map
    */
   private static void addDecoder(OpcodeDecoder decoder) {
      _decoders.put(new Integer(decoder.opcode()), decoder);
   }

}
