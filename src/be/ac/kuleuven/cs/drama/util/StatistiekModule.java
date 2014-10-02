/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/util/StatistiekModule.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.util;

import java.io.*;

import java.util.Map;
import java.util.HashMap;

//import be.ac.kuleuven.cs.drama.gui.*;

//import be.ac.kuleuven.cs.drama.simulator.basis.*;

/** Klasse voor het bijhouden van statistieken in verband met
 * DRAMA instructies
 *
 * @version 1.0 14 MAY 1999
 * @author Tom Vekemans
 */

public class StatistiekModule {

   private static final Map RAM_FILES = new HashMap();

   private int[] _instr;

   /**instantieer een nieuwe StatistiekModule
   */
   public StatistiekModule() {
      init();
   }

   /**initialiseer deze StatistiekModule (alle waarden worden
   terug op 0 gezet)
   */
   public void init() {
      _instr = new int[100];
   }

   /**voeg een voorkomen van een gegeven instructie toe
   @param opcode de (numerieke) opcode van de instructie 
   */
   public void addOccurence(int opcode) {
      _instr[opcode]++;
   }

   /**geef een String representatie van deze StatistiekModule
   @return een String representatie van deze StatistiekModule
   */
   public String toString() {
      StringBuffer buf = new StringBuffer();
      InverseCodeGenerator decomp = new InverseCodeGenerator();

      for (int i = 0;i < 100;i++) {
         if (_instr[i] > 0) {
            buf.append(decomp.getSource("" + i));
            buf.append(": ");
            buf.append("" + _instr[i]);
            buf.append('\n');
         }

      }

      return buf.toString();
   }

   /**schrijf de inhoud van deze StatistiekModule naar een bestand,
     onder de vorm van een String
   @param filenaam de naam van het bestand waarnaar de inhoud
    geschreven moet worden
   */
   public void writeFile(String filenaam) {
      //try{

      //System.out.println("Writing: " + filenaam);

      String fileName = filenaam;
      RAM_FILES.put(fileName.toLowerCase(), toString());

      // File statfile=new File(DramaTaskBar.DRAMADIR, filenaam);
      // File statfile=new File("." + File.separator, filenaam);
      // FileOutputStream fo=new FileOutputStream(statfile);
      // BufferedOutputStream bos=new BufferedOutputStream(fo);
      // PrintStream printStream=new PrintStream(bos);

      // printStream.print(this.toString());

      // printStream.close();
      // bos.close();
      // fo.close();
      //}
      //catch(IOException ioe){
      // System.out.println(ioe.toString());
      //}
   }

   /**lees de inhoud van een bestand en geef deze gegevens terug
    als String
   @param filenaam de naam van het bestand waarvan de inhoud
    gelezen moet worden runstat.txt
   */
   public String readFile(String filenaam)
   //throws IOException
   {
      //StringBuffer buf=new StringBuffer();
      //String lijn;
      //try{
      // BufferedReader reader= new BufferedReader(new FileReader("." + File.separator + filenaam));
      // while((lijn=reader.readLine()) != null) {
      //  buf.append(lijn);
      //  buf.append('\n');
      // }
      // reader.close();
      //}
      //catch(FileNotFoundException fnfe){
      // System.out.println(fnfe.toString());
      //}
      //catch(IOException ioe){
      // System.out.println(ioe.toString());
      //}
      //return buf.toString();

      //System.out.println("Reading: " + filenaam);

      return (String) RAM_FILES.get(filenaam.toLowerCase());
   }

}


