/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/DummyState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * Dummy GuiState implementation
 * that only writes out the names
 * of the invoked methods.
 *
 * @version 1.0.0 09/06/2000
 * @author  Tom Schrijvers
 */

class DummyState

   extends GuiState {
   DummyState(GuiStateMachine stateMachine) {
      super(stateMachine);
   }

   void initActionStates() {
      System.out.println("DummyState.initActionStates()");
   }

   void newFile() {
      System.out.println("DummyState.newFile()");
   }

   void saveFile() {
      System.out.println("DummyState.saveFile()");
   }

   void openFile() {
      System.out.println("DummyState.openFile()");
   }

   void precompile() {
      System.out.println("DummyState.precompile()");
   }

   void compile() {
      System.out.println("DummyState.compile()");
   }

}
