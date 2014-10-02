/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/SimpleMachine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import be.ac.kuleuven.cs.drama.gui.DramaRuntime;

import be.ac.kuleuven.cs.drama.simulator.ControllableMachine;
import be.ac.kuleuven.cs.drama.simulator.CVOInterface;
import be.ac.kuleuven.cs.drama.simulator.DeviceInterface;
import be.ac.kuleuven.cs.drama.simulator.DummyDeviceInterface;
import be.ac.kuleuven.cs.drama.simulator.Memory;
import be.ac.kuleuven.cs.drama.simulator.MonitorInterface;

//import be.ac.kuleuven.cs.drama.instantiation.DramaComponent;

import be.ac.kuleuven.cs.drama.util.StatistiekModule;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Hashtable;

/**
 * Simple inplementation of the ControllableMachine interface.
 * the number of used threads is limited to one.
 *
 * @version 1.0.0 08/09/2000
 * @author Tom Schrijvers
 */

public class SimpleMachine
   implements ControllableMachine, InternalMachine {
   private static final int MEMORY_SIZE = 10000;

   private static final boolean DEBUG = false;

   // the runtime environment
   private final DramaRuntime _runtime;

   private SimpleRAM _ram;
   private SimpleCPU _cpu;
   private InternalMonitor _monitor;
   private MonitorInterface _monitorInterface; // ONLY NEEDED BECAUSE OF BAD CONTROLLER DESIGN

   private MachineInput _input;

   private OutputStream _original_output_stream; // ONLY NEEDED FOR BACKWARD COMPATIBILITY
   private InputStream _original_input_stream; // ONLY NEEDED FOR BACKWARD COMPATIBILITY

   private boolean _halted = false;

   //private boolean       _debug  = false;

   private boolean _isFinished = true;

   private StatistiekModule _statistics;

   /**
    * Initialise a new SimpleMachine with
    * the given runtime environment.
    */
   public SimpleMachine(DramaRuntime runtime) {
      this(runtime, true);
   }

   public SimpleMachine(DramaRuntime runtime, boolean swing) {
      _runtime = runtime;

      loadDefaultComponents(swing);

      resetStats();
   }

   /*
    * set the default components of this machine
    */
   private void loadDefaultComponents(boolean swing) {
      _ram = new SimpleRAM(MEMORY_SIZE);
      _cpu = new SimpleCPU(this);

      if (swing) {
         SimpleMonitor monitor = new SimpleMonitor();
         _monitor = monitor;
         _monitorInterface = monitor;
      }

      _input = new SimpleInteractiveInput(this);
   }

   /*
    * reset this machine
    */
   private void reset() {
      _input.reset();
      _halted = false;
      ram().reset();
      cpu().reset();
      _isFinished = false;
   }

   // INTERNALMACHINE INTERFACE

   public SimpleRAM ram() {
      return _ram;
   }

   public SimpleCPU cpu() {
      return _cpu;
   }

   public InternalMonitor monitor() {
      return _monitor;
   }

   public InternalInput input() {
      return _input;
   }

   public StatistiekModule statistics() {
      return _statistics;
   }

   public boolean isHalted() {
      return _halted;
   }

   /*
   public boolean isDebug(){
   return _debug;
}
   */

   public void systemMessage(String message) {
      _runtime.systemMessage(message);
   }

   // CONTROLLABLEMACHINE INTERFACE

   public MonitorInterface getMonitorInterface() {
      return _monitorInterface;
   }

   public DeviceInterface getDeviceInterface(int index) {
      if (DEBUG) System.out.println( "SimpleMachine.getDeviceInterface()");

      return new DummyDeviceInterface();
   }

   public Memory getRAMMemory() {
      return ram();
   }

   public CVOInterface getCVOInterface() {
      return cpu();
   }

   public void buildMachine(String[] component, int[] address, int[] irq) {
      if (DEBUG) System.out.println( "SimpleMachine.buildMachine() not implemented");

   }

   public void loadProgram(String file, Hashtable breakpoints) {

      if (DEBUG) System.out.println("SimpleMachine.loadProgram()");

      systemMessage("Programma wordt geladen...");

      reset();

      _halted = false;
      //reset();
      try {
         ram().loadProgram(file);
         _cpu.setBreakPoints(breakpoints);
         // if (! _debug) {
         // System.out.println("Starting cpu...");
         // cpu().start();
         //}
      }
      catch (FatalMachineError fme) {
         systemMessage("Fout bij het laden van het programma.");
      }

   }

   /*
   public void startDebug(Hashtable breakpoints){
   if (DEBUG) System.out.println( "SimpleMachine.startDebug()");
   _debug = true;
   reset();
   _cpu.setBreakPoints(breakpoints);
}
   */

   /*
   public void stopDebug(){
   if (DEBUG) System.out.println("SimpleMachine.stopDebug()");
   _debug = false;
}
   */

   public void step() {
      if (DEBUG) System.out.println( "SimpleMachine.step()");

      cpu().singleStep();
   }

   public void halt() {
      if (DEBUG) System.out.println( "SimpleMachine.halt()");

      _halted = true;
      _monitor.close();
   }

   public void cont() {
      if (DEBUG) System.out.println( "SimpleMachine.cont()");

      _halted = false;
      cpu().start();
   }

   public void abort() {
      if (DEBUG)System.out.println( "SimpleMachine.abort()");

      halt();
      finish();
      //reset();
   }








   public void setMachineSleep(int millis) {
      if (DEBUG) System.out.println( "SimpleMachine.setMachineSleep() not implemented");

   }

   public InputStream getInputStream() {
      if (DEBUG) System.out.println( "SimpleMachine.getInputStream()");

      return _original_input_stream;
   }

   public void setInputStream(InputStream in) {
      if (DEBUG) System.out.println( "SimpleMachine.setInputStream()");

      if (in != null) {
         _input = new StreamInput(this, in);
      } else {
         _input = new SimpleInteractiveInput(this);
      }

   }

   public OutputStream getOutputStream() {
      if (DEBUG) System.out.println( "SimpleMachine.getOutputStream()");

      return _original_output_stream;
   }

   public void setOutputStream(OutputStream out) {
      if (DEBUG) System.out.println( "SimpleMachine.setOutputStream()");

      _original_output_stream = out;

      if (_monitor != null) {
         _monitor.close();
      }

      if (out != null) {
         _monitor = new StreamMonitor(out);
      } else {
         _monitor = (InternalMonitor) _monitorInterface;
      }

   }

   public void handleInput(String input) {
      // System.out.println( "SimpleMachine.handleInput() not implemented");
      _input.setInput(input);
   }

   public void resetStats() {
      //System.out.println( "SimpleMachine.resetStats() not implemented");
      _statistics = new StatistiekModule();
   }

   public void writeRunTimeStatsToFile() {
      //System.out.println( "SimpleMachine.writeRunTimeStatsToFile() not implemented");
      _statistics.writeFile("runstat.txt");
   }

   public boolean isFinished() {
      return _isFinished;
   }

   public void finish() {
      systemMessage("Programma beëindigd.");
      systemMessage("****************************************");
      _isFinished = true;
      _cpu.halt();
      _statistics.writeFile("runstat.txt");
   }

   public void halted() {
      _runtime.halted();
   }
}
