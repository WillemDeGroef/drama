/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/EventAdapter.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;

import be.ac.kuleuven.cs.drama.gui.*;
import be.ac.kuleuven.cs.drama.exception.*;

//import java.util.TooManyListenersException;
/**De EventAdapter klasse (zie thesis hoofdstuk 7)
*
* @version 1.0 21 APR 1999
* @author Stijn Van den Enden & Tom Vekemans
*/

public class EventAdapter implements java.io.Serializable {
   // an array of Class objects.
   private transient Class[] $params;
   // a vector containing EventHandlerContainer objects.
   private transient java.util.Vector $eventHandlerList;

   /**
     initialiseer een nieuwe EventAdapter
    */
   public EventAdapter() {
      $eventHandlerList = new Vector();

      setParams(EventObject.class);
   }

   /**
      Voeg een EventHandler toe aan de lijst van EventHandlers. Een EventHandler
      moet zichzelf opgeven alsook de methode die bij de EventHandler moet worden
      opgeroepen bij het voorkomen van het Event horend bij deze EventAdapter
      
      @param handler het object dat als EventHandler zal optreden (één van de...)
      @param methodName de methode die bij het object moet worden opgeroepen als het Event voorvalt
      
      @exception NoSuchMethodExcpetion als een onbestaande methode (in de klasse van de handler) wordt opgegeven
      @excpetion TooManyListenersException als er al te veel geregistreerde EventHandlers geregistreerd zijn
   */

   public synchronized void addEventHandler(Object handler, String methodName) throws NoSuchMethodException, TooManyListenersException {

      Method method = handler.getClass().getMethod(methodName, $params) ;
      EventHandlerContainer container = new EventHandlerContainer(handler, method);

      for (int i = 0 ; i < $eventHandlerList.size() ; i++) {
         if ( ((EventHandlerContainer)$eventHandlerList.elementAt(i)).getEventHandler().equals(handler) )
            throw new TooManyListenersException();
      }

      $eventHandlerList.addElement(container);
   }

   /**
      verwijder het gegeven object als EventHandler
      
      @param handler het te verwijderen object
      @exception NoSuchHandlerException als het opgegeven object niet als EventHandler geregistreerd was
   */
   public synchronized void removeEventHandler(Object handler) throws NoSuchHandlerException {

      boolean removed = false;
      int i = 0;

      while ( !removed && (i < $eventHandlerList.size()) ) {
         if ( ((EventHandlerContainer)$eventHandlerList.elementAt(i)).getEventHandler().equals(handler) ) {
            $eventHandlerList.removeElementAt(i);
            removed = true;
         }

         i++;
      }

      if ( !removed )
         throw new NoSuchHandlerException();

   }

   /**
      verwittig alle geregistreerde EventHandlers
      
      @param evt het event dat voorgevallen is
   */
   public void notifyHandlers(EventObject evt) {

      for (int i = 0; i < $eventHandlerList.size() ; i++) {
         try {
            Object handler = ((EventHandlerContainer)$eventHandlerList.elementAt(i)).getEventHandler();
            Method method = ((EventHandlerContainer)$eventHandlerList.elementAt(i)).getMethod();
            Object params[] = {evt};

            //System.out.println("adapter: " + this + ", handler: " + handler +  ", params: " + params);

            method.invoke(handler, params);
         } catch (IllegalAccessException e) {
            System.out.println("EventAdapter error : " + e);
         }
         catch (InvocationTargetException e) {

            e.getTargetException().printStackTrace();
            System.out.println("EventAdapter error : " + e);
            e.printStackTrace();

         }

      }

   }

   /**
   zet de parameters 
    */
   protected void setParams(Class param) {

      $params = new Class[1];
      $params[0] = param;

   }

   /**
      geef de lijst van EventHandlers
      
      @return de lijst van EventHandlers
    */
   protected Vector getEventHandlerList() {

      return $eventHandlerList;
   }

   // ---------------------------------------------------------------------------------------
   // ---------------------------------------------------------------------------------------
   // ---------------------------------------------------------------------------------------
   // an array of Class objects.
   //      private Class[] $params;
   // a vector containing EventHandlerContainer objects.
   //      private java.util.Vector $eventHandlerList;

   // Methods needed for serialization of this class:

   private void writeObject( ObjectOutputStream stream ) throws IOException {
      // use default serialization for the non-transient and non-static members ($eventHandlerList):
      stream.defaultWriteObject();

      // serialize Class[] $params:
      // get a clone of the array:
      Class[] temp;

      synchronized ($params) {
         temp = (Class[])$params.clone();
      }

      Class current = (Class) temp[0];
      stream.writeObject( current );
   }

   private void readObject( ObjectInputStream stream ) throws IOException {
      try {
         // use default serialization for the non-transient and non-static members ($eventHandlerList):
         stream.defaultReadObject();

         // initialize the $splits 'array':
         $params = new Class[1];
         $params[0] = (Class) stream.readObject();

         $eventHandlerList = new Vector();
      } catch (Exception exc) {
         throw new IOException();
      }

   }


}
