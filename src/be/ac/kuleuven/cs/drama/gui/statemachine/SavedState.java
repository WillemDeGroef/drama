/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/SavedState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * State that represents the clean
 * situation of the program text,
 * either not edited or new.
 * Precompilation of compilation has
 * not occured yet.
 *
 * @version 0.7.0 09/06/2000
 * @author  Tom Schrijvers
 */

class SavedState extends GuiState {

   SavedState(GuiStateMachine stateMachine) {
      super(stateMachine);
   }

   void initActionStates() {
      getStateMachine().setExecuteActionsEnabled(false);
   }

   void newFile() {
      getStateMachine().realNewFile();
   }

   void saveFile() {
      if (getStateMachine().isNewFile()) {
         try {
            getStateMachine().realSaveAsFile();
         } catch (CancelException ce) {
            getStateMachine().statusMessage("Opslaan geanulleerd.");
         }

      }

   }

   void openFile() {
      try {
         getStateMachine().realOpenFile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Openen geanulleerd.");
      }

   }

   void precompile() {
      try {
         if (getStateMachine().isNewFile()) {
            getStateMachine().realSaveAsFile();
         }

         getStateMachine().realPrecompile();
         getStateMachine().setCurrentState(getStateMachine().getPrecompiledState());
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Precompilatie niet geslaagd.");
      }

   }

   void compile() {
      try {
         if (getStateMachine().isNewFile()) {
            getStateMachine().realSaveAsFile();
         }

         getStateMachine().realPrecompile();
         getStateMachine().setCurrentState(getStateMachine().getPrecompiledState());
         getStateMachine().compile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Compilatie niet geslaagd.");
      }

   }

}
