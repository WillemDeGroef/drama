/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ExecutionEnvironment.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import java.util.Hashtable;

/**
 * Interfaces for classes that can use
 * the GUIController.
 *
 * @version 1.0.0 09/08/2000
 * @author  Tom Schrijvers
 */

public interface ExecutionEnvironment {

   public void systemMessage(String message);

   public Hashtable getBreakPoints();

   public String getFileName();

   void programLoaded();

   void programRunning();

   void programStepping();

   void programHalted();

   void programFinished();

}
