/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleCPU.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.events.*;

import be.ac.kuleuven.cs.drama.simulator.CVOInterface;
import be.ac.kuleuven.cs.drama.simulator.basis.DramaRegister;

import be.ac.kuleuven.cs.drama.simulator.devices.CVO.PTW;
import be.ac.kuleuven.cs.drama.simulator.devices.CVO.DramaPTW;

import java.util.Hashtable;

/**
 * Class that represents the CPU functions.
 *
 * @version 1.0.0 08/11/2000
 * @author  Tom Schrijvers
 */

public class SimpleCPU
   implements CVOInterface {

   private static final boolean debug = false;

   private static final int REGISTER_COUNT = 10;

   // reference to the other parts of the machine
   private final InternalMachine _machine;

   private Hashtable _breakPoints;

   private final DramaRegister _instructionRegister;
   private final DramaRegister[] _register;
   private final PTW _ptw;

   private final SimpleInstructionDecoder _decoder;

   private boolean _noSecondCall = true;

   private final Executer _executer;



   /**
    * Initialise a new SimpleCPU as part of the given
    * InternalMachine
    */
   public SimpleCPU(InternalMachine machine) {
      _machine = machine;

      _ptw = new DramaPTW();

      _instructionRegister = new DramaRegister();
      _register = new DramaRegister[REGISTER_COUNT];

      for (int index = 0; index < REGISTER_COUNT; index++) {
         _register[index] = new DramaRegister();
      }

      setRegister(9, 9000);

      _decoder = new SimpleInstructionDecoder(_machine);

      _executer = new Executer();
      _executer.start();
   }

   // RUNNABLE INTERFACE

   /**
    * Start the execution of the loaded program.
    */
   public void start() {
      _executer.add(new Continue());
   }

   /**
    * Execute a step.
    */
   public void singleStep() {
      _executer.add(new Step());
   }


   private boolean debugStop() {
      boolean result =         /* _machine.isDebug() && */ _breakPoints.containsKey(new Integer((int) ptw().getBT())) && _noSecondCall;
      _noSecondCall = result ? false : true;
      return result;
   }

   /**
    * set the table of breakpoints
    */
   public void setBreakPoints(Hashtable bps) {
      _breakPoints = bps;
   }

   /**
    * Fetch and decode the next instruction
    */
   public void step() {
      fetchInstruction();
      _decoder.decode();
   }

   /**
    * Fetch the next instruction and increment the BT
    */
   private void fetchInstruction() {
      int address = (int) ptw().getBT(); // STUPID CAST HERE
      setCurrentInstruction(_machine.ram().getCell(address));
      ptw().setBT(address + 1);
   }

   // DATA MEMBERS

   /**
    * Reset this CPU.
    */
   public void reset() {
      for (int i = 0; i < _register.length; i++) {
         setRegister(i, 0);
      }

      setRegister(9, 9000);

      ptw().init();
      ptw().setBT(0);
      setCurrentInstruction(0);
      _noSecondCall = false;

      _executer.clear();
   }

   public void halt() {
      _executer.clear();
   }

   /**
    * @return the PTW
    */
   public PTW ptw() {
      return _ptw;
   }

   /**
    * @return the register with given index
    */
   public long register(int index) {
      return _register[index].getValue();
   }

   /**
    * set the value of the given register
    */
   public void setRegister(int index, long value) {
      if (debug) System.out.println("R" + index + " = " + value);

      _register[index].setValue(value);

      try {
         ((RegChangeListener)GeneralEventAdapter.instance().getEventAdapter("RegChangeEventAdapter")).RegChange(new RegChangeEvent(this, index, value));
      } catch (Throwable t) {
         t.printStackTrace();
      }

   }

   /**
    * @return the current drama instruction
    */
   public long currentInstruction() {
      return _instructionRegister.getValue();
   }

   /*
    * set the current drama instruction
    */
   private void setCurrentInstruction(long value) {
      _instructionRegister.setValue(value);
      InstructionRegisterEventManager.fireEvent();

   }


   // CVOInterface implementation

   public long getBT() {
      return getPTW().getBT();
   }

   public PTW getPTW() {
      return ptw();
   }

   public long getReg(int index) {
      return register(index);
   }

   public long getBevReg() {
      return currentInstruction();
   }

   // Jobs

   private class Step

      implements Job {

      public Step() {}








      public void execute() {
         try {
            step();
            _noSecondCall = true;
         } catch (FatalMachineError fme) {
            _machine.systemMessage("Er is een fatale fout opgetreden en de uitvoering is gestopt: ");
            _machine.systemMessage(fme.getMessage());
         }
         catch (Throwable t) {
            _machine.systemMessage("Er is een onverwachte fout opgetreden en de uitvoering is gestopt: ");
            _machine.systemMessage(t.getMessage());
         }

         _machine.halted();
      }

   }

   private class Continue

      implements Job {

      public Continue() {}








      public void execute() {
         _machine.systemMessage("Uitvoering gestart...");

         try {
            while (! (_machine.isHalted() || debugStop())) {
               step();
            }
         } catch (FatalMachineError fme) {
            _machine.systemMessage("Er is een fatale fout opgetreden en de uitvoering is gestopt: ");
            _machine.systemMessage(fme.getMessage());
         }
         catch (Throwable t) {
            t.printStackTrace();
            _machine.systemMessage("Er is een onverwachte fout opgetreden en de uitvoering is gestopt: ");
            _machine.systemMessage(t.getMessage());
         }

         _machine.halt();
         _machine.systemMessage("Uitvoering beëindigd");
         _machine.halted();
      }

   }

}
