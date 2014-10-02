/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/DirtyState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * State indicating that the program text is dirty.
 *
 * @version 0.3.0 09/06/2000
 * @author  Tom Schrijvers
 */

class DirtyState

   extends GuiState {
   DirtyState(GuiStateMachine stateMachine) {
      super(stateMachine);
   }

   void initActionStates() {
      getStateMachine().setExecuteActionsEnabled(false);
   }

   void newFile() {
      try {
         // IMPL prompt alvorens op te slaan ???
         save();
         getStateMachine().newFile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Openen van nieuw bestand geanulleerd.");
      }

   }

   void saveFile() {
      try {
         save();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Opslaan geanulleerd.");
      }

   }

   void saveAsFile() {
      saveFile();
   }

   void openFile() {
      try {
         // IMPL prompt alvorens op te slaan
         save();
         getStateMachine().openFile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Openen geanulleerd.");
      }

   }

   void precompile() {
      try {
         save();
         getStateMachine().precompile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Precompilatie niet geslaagd.");
      }

   }

   void compile() {
      try {
         save();
         getStateMachine().compile();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Comilatie niet geslaagd.");
      }

   }

   void quit() {
      try {
         getStateMachine().realOptionalSaveAsFile();
         getStateMachine().realQuit();
      } catch (CancelException ce) {
         getStateMachine().statusMessage("Afsluiten onderbroken.");
      }

   }

   private void save()
   throws CancelException {
      if (getStateMachine().isNewFile()) {
         getStateMachine().realSaveAsFile();
      } else {
         getStateMachine().realSaveFile();
      }

      getStateMachine().setCurrentState(getStateMachine().getSavedState());
   }

   void notifyDirty() {
      // DO NOTHING
   }








}
