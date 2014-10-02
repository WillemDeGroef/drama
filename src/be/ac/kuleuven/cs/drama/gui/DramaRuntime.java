/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/DramaRuntime.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

/**
   Deze interface moet geïmplementeerd worden door een programma dat
   de simulator controleert (bv. de IDE of het batch programma). Het 
   doel is boodschappen vanuit de vertaler of de machine op te vangen
   en te behandelen
  
   @version 1.0 19/03/1999
   @author Tom Vekemans
 */

public interface DramaRuntime {

   /**
     toon een gegeven boodschap, met een gegeven titel
     @param message de af te beelden boodschap
     @param title de titel van de boodschap
   */
   //public void showOKMessage(String message, String title);

   /**
      toon de gegeven boodschap
      @param message de te tonen boodschap (deze komt uit de vertaler
      module)
   */
   public void systemMessage(String message);

   void halted();
}
