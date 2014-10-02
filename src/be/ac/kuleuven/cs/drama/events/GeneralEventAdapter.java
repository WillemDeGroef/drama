/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/GeneralEventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.Vector;
import java.io.*;
import be.ac.kuleuven.cs.drama.exception.*;

/**singleton klasse die alle EventAdapters bevat
*
*@version 1.0 21 APR 1999
*@author Stijn Vanden Enden & Tom Vekemans
*/

public class GeneralEventAdapter implements java.io.Serializable {
   /**instantiatie van de singleton GeneralEventAdapter*/
   private static GeneralEventAdapter $instance = new GeneralEventAdapter();
   private static Vector $adapterList;
   /**initialisatie van de GeneralEventAdapter*/
   static {
      // initialisation of the default event adapters
      $instance.addEventAdapter(new RegChangeEventAdapter());
      $instance.addEventAdapter(new PTWChangeEventAdapter());
      $instance.addEventAdapter(new IRQChangeEventAdapter());
      $instance.addEventAdapter(new PortChangeEventAdapter());
      $instance.addEventAdapter(new BusChangeEventAdapter());
   }

   /**
      Creatie van een nieuwe GeneralEventAdapter
    */
   private GeneralEventAdapter() {
      $adapterList = new Vector();
   }

   /**
      geef de EventAdpter van de gegeven klasse
      @param className de klassenaam van de EventAdapter die men wil opvragen
      @return de EventAdpter van de gegeven klasse
      @exception NoSuchRegisteredEventAdapter als geen object van de gegeven klasse
       zich als EventAdapter geregistreerd heeft
    */
   public EventAdapter getEventAdapter(String className) throws NoSuchRegisteredEventAdapter {

      for (int i = 0; i < $adapterList.size(); i++) {
         EventAdapter adapter = (EventAdapter)$adapterList.elementAt(i);

         if (adapter.getClass().toString().endsWith(new String(className))) {
            return adapter;
         }

      }

      throw new NoSuchRegisteredEventAdapter();
   }

   /**
      voeg een gegeven EventAdapter toe aan de lijst van geregistreerde EventAdapters
      @param eventAdapter de toe te voegen EventAdapter
    */

   public synchronized void addEventAdapter(EventAdapter eventAdapter) {

      $adapterList.addElement(eventAdapter);
   }

   /**
      verwijder alle EventAdapters van de gegeven klasse
      @param className de klassenaam van de te verwijderen EventAdapter(s)
    */
   public synchronized void removeEventAdapter(String className) {

      for (int i = 0; i < $adapterList.size(); i++) {
         EventAdapter adapter = (EventAdapter)$adapterList.elementAt(i);

         if (adapter.getClass().toString().endsWith(new String(className))) {
            $adapterList.removeElement(adapter);
         }

      }

   }

   /**
      geef <I>de</I> instantie van deze GeneralEventAdapter
      @return de GeneralEventAdpater
    */
   public static GeneralEventAdapter instance() {

      return $instance;
   }

   // ---------------------------------------------------------------------------------------
   // ---------------------------------------------------------------------------------------
   // Methods needed for serialization of this class:
   /**serialization: schrijf dit object naar de uitvoer*/
   private void writeObject( ObjectOutputStream stream ) throws IOException {
      // use default serialization for the non-transient and non-static members:
      stream.defaultWriteObject();
   }

   /**serialization: schrijf dit object naar de invoer*/
   private void readObject( ObjectInputStream stream ) throws IOException {
      try {
         stream.defaultReadObject();
         GeneralEventAdapter.setInstance(new GeneralEventAdapter());
      } catch (Exception exc) {
         throw new IOException();
      }

   }

   /**zet een reeds bestaande instantie als <I>de</I> instantie van
   deze GeneralEventAdapter
   */
   private static void setInstance(GeneralEventAdapter fev) {
      $instance = fev;
   }

}
