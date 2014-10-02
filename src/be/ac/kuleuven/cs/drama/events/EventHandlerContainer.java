/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/events/EventHandlerContainer.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.events;

import java.lang.reflect.Method;
/**EventHandlerContainers bevatten objecten die geïnteresseerd zijn
in een bepaald event (de EventHandler), en de methode die bij het 
optreden van zo'n event bij die objecten moeten worden opgeroepen.
@version 1.0
@author Stijn Vanden Enden & Tom Vekemans
*/

public class EventHandlerContainer implements java.io.Serializable {
   private transient Object $eventHandler;
   private transient Method $method;

   /**
      maak een EventHandler met daarin een gegeven object en de methode
      die bij het optreden van een Event op dat object moet worden opgeroepen
      @param eventHandler het object dat op een event zal reageren
      @param method de methode die op het object moet worden opgeroepen
   */
   public EventHandlerContainer(Object eventHandler, Method method) {
      $eventHandler = eventHandler;
      $method = method;
   }

   /**
      geef de eventHandler van deze EventHandlerContainer
      @return de eventHandler van deze EventHandlerContainer
    */
   public Object getEventHandler() {
      return $eventHandler;
   }

   /**
      geef de methode die op de eventHandler van deze
      EventHandlerContainer moet worden opgeroepen
      
      @return de methode die op de eventHandler van deze
      EventHandlerContainer moet worden opgeroepen
   */
   public Method getMethod() {
      return $method;
   }

}
