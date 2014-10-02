/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/GUIController.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.lang.ClassLoader;

import be.ac.kuleuven.cs.drama.simulator.*;
import be.ac.kuleuven.cs.drama.events.*;
//import be.ac.kuleuven.cs.drama.gui.simulator.*;
//import be.ac.kuleuven.cs.drama.instantiation.*;
//import be.ac.kuleuven.cs.drama.simulator.bus.*;
//import be.ac.kuleuven.cs.drama.simulator.devices.CVO.*;
//import be.ac.kuleuven.cs.drama.simulator.devices.geheugen.*;
//import be.ac.kuleuven.cs.drama.simulator.devices.periferie.*;

import be.ac.kuleuven.cs.drama.exception.*;
import be.ac.kuleuven.cs.drama.util.*;

import be.ac.kuleuven.cs.drama.simulator.simple.SimpleMachine;

import be.ac.kuleuven.cs.drama.gui.visualisation.MachineVisualisation;


/** Klasse GUIController coördineert het drama-machine model en de
 *  verschillende GUI's (Graphical User Interface)  
 *
 *  @version 2.8 11 MAY 1999
 *  @author Tom Vekemans, Emmanuel Lambert, Erik Van Hoeymissen
 *
 *  Aanpassing:
 *   GUIController werkt nu met een ControllableMachine ipv met een DramaMachine
 *   9 augustus 2000
 *   Tom Schrijvers
 */

public class GUIController
   implements DramaRuntime {

   private ControllableMachine _machine;
   private ExecutionEnvironment /* DramaScherm */ _gui;

   // private MachineImage _machineGui;
   private MachineVisualisationInterface _machineGui;

   private int[] _adres = { -1, -1, -1, 3, 1, 7, 5, 8, 9};
   private int[] _irq = { -1, -1, -1, 5, 4, 6, 3, -1, 2};
   private int _numberOfDevices = 6;

   private String _browsercomdo;

   private InputStream _oldInStream;
   private OutputStream _oldOutStream;

   private final SymbolTableParser _labelParser = new SymbolTableParser();

   /**instantieer een nieuwe GUIController, voor een gegeven DramaScherm
      @param gui het DramaScherm dat via deze GUIController met een 
      ControllableMachine wil werken. De GUIController maakt bij instantiatie
      daarvoor een nieuwe ControllableMachine aan.
      @see DramaScherm
      @see ControllableMachine
   */
   public GUIController(ExecutionEnvironment /* DramaScherm */ gui) {
      _gui = gui;

      // _machineGui = new MachineImage(this);
      _machineGui = new MachineVisualisation(this);

      try {
         GeneralEventAdapter.instance().getEventAdapter("RegChangeEventAdapter").
         addEventHandler(this, "regChange");
         GeneralEventAdapter.instance().getEventAdapter("PTWChangeEventAdapter").
         addEventHandler(this, "ptwChange");
         GeneralEventAdapter.instance().getEventAdapter("IRQChangeEventAdapter").
         addEventHandler(this, "irqChange");
         GeneralEventAdapter.instance().getEventAdapter("PortChangeEventAdapter").
         addEventHandler(this, "portChange");
         GeneralEventAdapter.instance().getEventAdapter("BusChangeEventAdapter").
         addEventHandler(this, "busChange");
      } catch (Exception e) {
         System.out.println("Exception " + e.toString());
      }

      _machine = Settings.getMachine(this);
      // _machine = new SimpleMachine(this);
      // _machine = new DramaMachine(this);

      Frame f = _machineGui.getInternalRepresentationFrame();
      f.setVisible(false);

      Properties result = new Properties();

      try {
         InputStream is = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("settings.prop"));
         result.load(is);

         _browsercomdo = result.getProperty("Browser");
      } catch (Exception e) {
         System.err.println("PropertiesReader.. failed: " + e);
      }

      InstructionRegisterEventManager.addListener(new InstructionRegisterListener() {
               public void handleInstructionRegisterEvent() {
                  _machineGui.setBevReg(_machine.getCVOInterface().getBevReg());
               }

            }

                                                 );

      MemoryEventManager.addListener(new MemoryListener() {
                                        public void handleMemoryEvent(int address, long value) {
                                           _machineGui.updateMemory(address, value);
                                        }

                                     }

                                    );

      initGuiMachine();
   }

   private void initGuiMachine() {
      String[] devices = new String[6];

      for (int i = 0; i < 6; i++) {
         devices[i] = getDeviceType(i);
      }

      _machineGui.setDevices(6, devices);
   }

   /** Behandel invoer
    */
   public void handleInput(String input) {
      getMachine().handleInput(input);
   }

   /**geef de ControllableMachine, geassocieerd aan deze GUIController
      @return de ControllableMachine, geassocieerd aan deze GUIController
   */
   private final ControllableMachine getMachine() {
      return _machine;
   }

   public void reset() {
      loadProgram();
   }

   /**zet de geassocieerde ControllableMachine in debug mode
      @param breaklist de breekpuntenlijst die de ControllableMachine
      moet gebruiken bij het uitvoeren van programma's.
      @see ControllableMachine
   */
   /*
   public void startDebug(Hashtable breaklist){
   _machine.startDebug(breaklist);
}
   */

   /**stop de debug mode, en voer daarbij de nodige stappen uit
      in de GUI's en de ControllableMachine.
   */
   /*
   public void stopDebug(){
   _gui.clearLineColor();
   _machine.stopDebug();
   _machineGui.getCVOFrame().setVisible(false);
}
   */

   /**stop definitief de uitvoering van een programma op de
      geassocieerde ControllableMachine.
   */
   public void abort() {
      // _gui.clearLineColor();
      _machine.abort();
      _machine.resetStats();
      _machineGui.halt();
   }

   /**vervolg de uitvoering van een tijdelijk onderbroken programma
      op de geassocieerde ControllableMachine. Hierbij wordt ook het bijhorende
      DramaScherm aangepast (nodig als dit commando volgt op een step())
   */
   public void cont() {

      if (_machine.isFinished()) {
         reset();
         _gui.programRunning();
         _machine.cont();
      } else {
         // _gui.clearLineColor();
         _gui.programRunning();
         _machine.cont();
         _machineGui.cont();
      }

      //if (! _machine.isFinished()){
      //    int bt=(int)(_machine.getCVOInterface().getBT());
      //    _gui.setLineColor(bt,Color.red);
      //}

   }

   /**laat de geassocieerde ControllableMachine 1 instructie uivoeren op de
      ControllableMachine, en daarna wachten op een volgend commando van de
      gebruiker. Als het programma in de editor van het DramaScherm
      nog niet in het geheugen van de DramaMAchine geladen was, zal dit
      nu gebeuren. De huidige lijn in de code wordt in het DramaScherm gekleurd.
   */
   public void step() {

      if (_machine.isFinished()) {
         reset();
      }

      _gui.programStepping();
      _machine.step();

      int bt = (int)(_machine.getCVOInterface().getBT());
      //_gui.setLineColor(bt,Color.red);
      _machineGui.getCVOFrame().toFront();
   }

   /**
    * zet de geassocieerde ControllableMachine in de halttoestand
    */
   public void halt() {
      _machine.halt();
      _machineGui.halt();
   }

   /**geef de MonitorInterface van de ControllableMachine
      @return de MonitorInterface van de ControllableMachine
      @see MonitorInterface
   */
   public final MonitorInterface getMonitor() {
      return _machine.getMonitorInterface();
   }

   /**toon het inwendige van machine (een aparte GUI)
    */
   public void showInternalMachine() {
      setEventsEnabled(true);
      setCVOGui();
      _machineGui.getInternalRepresentationFrame().show();
      _machineGui.getInternalRepresentationFrame().setVisible(true);
      _machineGui.getInternalRepresentationFrame().toFront();
   }

   /**toon de voorstelling van de CVO
    */
   public void showCVO() {
      setCVOGui();
      _machineGui.getCVOFrame().show();
      _machineGui.getCVOFrame().setVisible(true);
      _machineGui.getCVOFrame().toFront();
   }

   /**actualiseer de waarden van de CVO-GUI
    */
   private void setCVOGui() {
      int i;
      CVOInterface cvo = _machine.getCVOInterface();

      for (i = 10;--i >= 0;) {
         _machineGui.setReg(i, cvo.getReg(i));
      }

      _machineGui.setPTW(cvo.getPTW());

      try {
         _machineGui.setBevReg(cvo.getBevReg());
      } catch (Exception e) {
         _machineGui.setBevReg(9999999999L);
      }

   }

   /**reageer op een RegChangeEvent (verandering van een register in de CVO)
      @param evt het RegChangeEvent waarop gereageerd moet worden
   */
   public void regChange(RegChangeEvent evt) {
      //System.out.println("regChange");
      _machineGui.setReg(evt.getRegIndex(), evt.getValue());
      // _machineGui.getInternalRepresentationFrame().repaint();
   }

   /**reageer op een ptwChangeEvent (verandering van het PTW van de CVO)
      @param evt het ptwChangeEvent waarop gereageerd moet worden
      @see PTW
   */
   public void ptwChange(PTWChangeEvent evt) {

      try {


         _machineGui.setPTW(evt.getPTW());

      } catch (Throwable t) {
         t.printStackTrace();
      }

      // _machineGui.getInternalRepresentationFrame().repaint();
   }

   /**reageer op een irqChangeEvent (verandering van een PO-vlag)
      @param evt het irqChangeEvent waarop gereageerd moet worden
      @see IRQLine
   */
   public void irqChange(IRQChangeEvent evt) {
      try {
         int irq = evt.getIRQIndex();
         boolean on = evt.getOn();
         _machineGui.setDeviceIRQ(getDeviceIndexForIRQ(irq), on);
         _machineGui.setIRQ(irq, on);
      } catch (AbnormalTerminationException ate) {
         // showOKMessage(ate.toString(),"Configuratie probleem");
         systemMessage("Configuratieprobleem: ".concat(ate.toString()));
      }

      // _machineGui.getInternalRepresentationFrame().repaint();
   }

   /**reageer op een portChangeEvent (verandering van een poort van een
      randapparaat)
      @param evt het portChangeEvent waarop gereageerd moet worden
   */
   public void portChange(PortChangeEvent evt) {
      int index = getDeviceIndexForAdres(evt.getPortAdres());

      if (evt.getSort() == PortChangeEvent.PT) {
         _machineGui.setRAPtTextField(index, evt.getValue());
      } else {
         _machineGui.setRAPogTextField(index, evt.getValue());
      }

   }

   /**reageer op een busChangeEvent (verandering van een deel van de bus)
      @param evt het BusChangeEvent waarop gereageerd moet worden
   */
   public void busChange(BusChangeEvent evt) {
      _machineGui.setBusAdresText(evt.getAdres());
      _machineGui.setBusDataText(evt.getData());
      _machineGui.setBusSignaalText(evt.getStrobe());
   }

   /**geef het randapparaat met de gegeven index*/
   private DeviceInterface getDevice(int index) {
      return _machine.getDeviceInterface(index);
   }

   /**geef de inhoud van de geheugenlocaties tussen de twee gegeven
      adressen
      @param startAdress het beginadres van de geheugenzone
      @param endAdress het eindadres van de geheugenzone
      @return een array met de inhoud van de geheugenlocaties met adres
      tussen startAdress en stopAdress (inclusief)
   */
   public long[] getMemoryRegion(int startAdress, int endAdress) {
      Memory ram = _machine.getRAMMemory();
      long[] region = new long[endAdress - startAdress];

      for (int i = startAdress; i < endAdress && i <= 9999; i++) {
         try {
            region[i - startAdress] = ram.getData(i);
            // System.out.println("mem["+i+"] ok");
         }
         catch (Exception e) {
            System.out.println(e.toString() + " : i=" + i);
         }

      }

      return region;
   }

   public long getRAMCell(int address) {
      if (_machine == null) {
         return 0L;
      }

      return _machine.getRAMMemory().getData(address);
   }

   /**bouw een ControllableMachine volgens de gegeven specificaties, en gebruik
      in het vervolg de resulterende ControllableMachine 
      @param components de klasse-namen van de onderdelen van de nieuwe 
      ControllableMachine
      @param adres de adressen van de componenten van de nieuwe ControllableMachine
      @param irq de PO-vlaggen die de componenten van de nieuwe ControllableMachine
      gebruiken
      @see ControllableMachine
      @see DramaComponent
   */
   public void buildMachine(String[] components, int[] adres, int[] irq) {
      _adres = adres;
      _irq = irq;
      _machine.buildMachine(components, adres, irq);

      // _machineGui = new MachineImage(this);

      _machineGui = new MachineVisualisation(this);

      _numberOfDevices = components.length - 3; //alle components - bus, cvo, geheugen
      for (int i = 0;i < components.length;i++)

         if (components[i].equals("(niets)") ) _numberOfDevices--;

      _machineGui.setDevices(_numberOfDevices, components);
      // setNiveau(getNiveau());
      _gui.systemMessage("aanmaak nieuwe machine klaar");
   }

   /**toon in het geassocieerde DramaScherm een gegeven boodschap met een
      gegeven titel
      @param message de af te beelden boodschap
      @param title de titel van de af te beelden boodschap
   */
   // public void showOKMessage(String message, String title){
   // _gui.showOKMessage(message,title);
   // }

   /**zet de uitvoerstroom van de geassocieerde ControllableMachine
      @param outstream de nieuwe uitvoerstroom van de ControllableMachine
      <BR> de GUIController zal een referentie naar de huidige uitvoerstroom 
      bewaren, en het menu van het DramaScherm aanpassen om dit aan te geven
   */
   public void setOutputStream(OutputStream outstream) {
      _oldOutStream = _machine.getOutputStream();
      // _gui.setResetOutputFileEnabled(true);
      BufferedOutputStream buf = new BufferedOutputStream(outstream);
      _machine.setOutputStream(buf);
   }

   /**vervang de uitvoerstroom van de geassocieerde ControllableMachine door
      de vorige uitvoerstroom
   */
   public void resetOutputStream() {
      try {
         _machine.getOutputStream().flush();
         _machine.getOutputStream().close();
      } catch (Exception ioe) {}

      //_machine.setOutputStream(_oldOutStream);










      _machine.setOutputStream(null);

      //_gui.setResetOutputFileEnabled(false);
   }

   /**zet de invoerstroom van de geassocieerde ControllableMachine
      @param instream de nieuwe invoerstroom van de ControllableMachine
      <BR> de GUIController zal een referentie naar de huidige invoerstroom 
      bewaren, en het menu van het DramaScherm aanpassen om dit aan te geven
   */
   public void setInputStream(InputStream instream) {
      if (!(instream.markSupported())) {
         BufferedInputStream buf = new BufferedInputStream(instream);
         buf.mark(100000);
         instream = buf;
      }

      _oldInStream = _machine.getInputStream();
      // _gui.setResetInputFileEnabled(true);
      _machine.setInputStream(instream);
   }

   /**vervang de invoerstroom van de geassocieerde ControllableMachine door
      de vorige invoerstroom
   */
   public void resetInputStream() {
      //_machine.setInputStream(_oldInStream);
      _machine.setInputStream(null);
      //_gui.setResetInputFileEnabled(false);
   }

   /**beeldt een systeemboodschap af in het geassocieerde DramaScherm
      @param message de af te beelden systeemboodschap
   */
   public void systemMessage(String message) {
      _gui.systemMessage(message);
   }
   
   public void halted() {
      final boolean finished = _machine.isFinished();
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            if (finished)
               _gui.programFinished();
            else
               _gui.programHalted();
         }
      });
   }

   /**zet het complexiteitsniveau (gebruikersniveau) van de
      visualisatieGUI's (zie gebruikshandleiding)
      @param i het in te stellen complexiteitsniveau (<=1:eenvoudig, 2:gemiddeld,
      >=3:complex)
   */
   public void setNiveau(int i) {

      switch (i) {

      case 2:
         _machineGui.setIRQPartEnabled(true);
         break;

      default:
         _machineGui.setIRQPartEnabled(false);

      }

      //_machineGui.setIRQPartEnabled(false);
      //_machineGui.setPTWPartEnabled(false);
      //if(i>1)
      //    _machineGui.setPTWPartEnabled(true);
      //if(i>2)
      //    _machineGui.setIRQPartEnabled(true);
   }

   /**geef het huidig ingestelde complexiteitsniveau (dit wordt gegeven
      door het geassocieerde DramaScherm)
      @return het huidig ingestelde complexiteitsniveau
      @see DramaMenuBar
   */
   // public int getNiveau(){
   //   return _gui.getNiveau();
   // }

   /**geef voor een gegeven adres het volgnummer van het apparaat
    *
    *@param adres het adres van een poort van het apparaat
    *@return het volgnummer van het apparaat dat het gegeven
    * adres heeft. Het scherm heeft altijd index 0.
    * (opm: er zijn 2 poorten => een apparaat kan 2 adressen hebben).
    * Als het adres NIET bij een apparaat hoort wordt -1 teruggegeven
    */
   private int getDeviceIndexForAdres(int adres) {
      for (int i = 0;i < _adres.length;i++)

         if (_adres[i] == adres) return i -3;  //Pog

      for (int i = 0;i < _adres.length;i++)

         if (_adres[i] - 1 == adres) return i -3; //Pt

      return -1;
   }

   /**geef voor een gegeven irq het volgnummer van het apparaat
    *
    *@param irq het irqnummer van het apparaat
    *@return het volgnummer van het apparaat dat het gegeven
    * irq heeft. 
    * Als de irq NIET bij een apparaat hoort wordt -1 teruggegeven
    */
   private int getDeviceIndexForIRQ(int irq) {
      for (int i = 0;i < _irq.length;i++)

         if (_irq[i] == irq) return i -3;

      return -1;
   }

   /**Geef, voor een gegeven volgnummer, het type van het apparaat
    *
    * @param index het volgnummer van het apparaat (begint vanaf 0)
    *     dit zijn de volgnummers in de GUI
    *
    * @return een String die het type aangeeft (Schijf, Klavier, ...)
    */
   public String getDeviceType(int index) {
      if (index == 0)
         return "Scherm";
      else
         return getDevice(index -1).getType();
   }

   /**activeer bus- en poortevents volgens de gegeven boolean
      @param b als b true is worden de bus- en poortevents geactiveerd,
      en anders gedesactiveerd
   */
   public void setEventsEnabled(boolean b) {
      //GenericBusDeviceCore.setEventsEnabled(b);
      //Bus.setEventsEnabled(b);
      //_machineGui.setEventsEnabled(b);
   }

   /**zet de pauze tussen 2 cycli van de ControllableMachine
      @param millis het aantal milliseconden dat de geassocieerde 
      ControllableMachine tussen 2 opeenvolgende cycli zal slapen
   */
   public void setMachineSleep(int millis) {
      _machine.setMachineSleep(millis);
   }

   /**toon de run-time statistieken
    */
   public void showRunTimeStats() {
      _machine.writeRunTimeStatsToFile();

      // Tom Schrijvers: beperking van de GUI-verantwoordelijkheden
      //String stats=(new StatistiekModule()).readFile("runstat.txt");
      //showOKMessage(stats,"Runtime statistieken");
   }

   /**herinitialiseer de runtime statistieken
    */
   public void resetStats() {
      _machine.resetStats();
   }

   //helper
   public void loadProgram() {
      try {

         String outfile = _gui.getFileName();
         getMachine().loadProgram(outfile, _gui.getBreakPoints());
         _machineGui.cont();

         _labelParser.parseFile(outfile);

         _machineGui.setLabels(_labelParser.getLabels(), _labelParser.getAddresses());
         
         _gui.programLoaded();

         /* System.out.println("programma " + outfile +
         " geladen");*/
      } catch (IndexOutOfBoundsException ioobe) {
         //showOKMessage("Geen programma geladen ! ",
         //  "Bedieningsfout");
         systemMessage("Bedieningsfout: geen programma geladen !");
      }

   }

   /**open de browser en laadt daarin een gegeven bestand
      @param filenaam het te openen bestand
   */
   //public void openBrowser(String filenaam){
   //try{
   //    URL url=new URL("file://"+ System.getProperty("user.dir") + "/" + filenaam);
   //    //System.out.println("url = " + url.toString());
   //    Runtime.getRuntime().exec(_browsercomdo + " " + url.toString());
   //}
   //catch(Exception e){
   //    //showOKMessage("Probleem: " + e.toString() + "\n\nStart " + filenaam +
   //   // " in een browser","Environment probleem");
   //}
   //  }

   /**geef de waarde van de bevelenteller van de CVO
      @return de waarde van de bevelenteller van de CVO
      
   */
   public long getBT() {
      return _machine.getCVOInterface().getBT();
   }

}
