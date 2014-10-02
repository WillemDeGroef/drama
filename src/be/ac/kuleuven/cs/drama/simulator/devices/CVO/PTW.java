/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/devices/CVO/PTW.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.devices.CVO;

import be.ac.kuleuven.cs.drama.simulator.basis.*;
import be.ac.kuleuven.cs.drama.events.*;

/** Klasse voor bewerkingen op PTWs (PTW=programmatoestandswoord)*
 * @version 1.2 19 APR 1999
 * @author Tom Vekemans
 */

public abstract class PTW extends Register {

   private PTWChangeEvent evt = new PTWChangeEvent(this, null);
   private PTWChangeListener _myListener;

   /**instantieer een nieuw PTW
    */
   public PTW() {
      try {
         _myListener = ((PTWChangeListener)GeneralEventAdapter.instance().
                        getEventAdapter("PTWChangeEventAdapter"));
      } catch (Exception e) {
         System.out.println(e.toString() + " : " + evt.toString());
      }

   }

   /**initialiseer dit PTW
    */
   public abstract void init();

   /**geef de bevelenteller van dit PTW
      @return de waarde van de bevelenteller van dit PTW
   */
   public abstract long getBT();

   /**zet de waarde van de bevelenteller
      @param value de nieuwe waarde van de bevelenteller
   */
   public abstract void setBT(long value);

   /**geef de conditiecode
      @return de waarde van de conditiecode van dit PTW
   */
   public abstract int getCC();

   /**zet de waarde van de conditiecode
      @param value de nieuwe waarde van de conditiecode
   */
   public abstract void setCC(int value);

   /**zet de waarde van het "waarde" deel van dit PTW. Het
      "waarde" deel is het deel van het PTW zonder maskervlaggen 
      @param value de waarde die het "waarde" deel moet krijgen
   */
   public abstract void setValue(long value);

   /**zet de waarde van het "masker" deel van dit PTW. Het
      "masker" deel bestaat uit de maskervlaggen van het PTW
      @param value de waarde die het "masker" deel moet krijgen
   */
   public abstract void setMaskerValue(long value);

   /**verwittig de objecten die geïnteresseerd zijn in veranderingen van het
      PTW dat er een wijziging is opgetreden
   */
   protected final void notifyListeners() {
      try {
         evt.setPTW(this);
         _myListener.PTWChange(evt);
      } catch (Exception e) {
         System.out.println(e.toString() + " : " + evt.toString());
      }

   }

}
