/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/GuiState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * Superclass of all states of a gui state machine.
 *
 * @version 1.1.0 09/06/2000
 * @author Tom Schrijvers
 */

abstract class GuiState {

    private final GuiStateMachine _stateMachine;

    protected GuiState(GuiStateMachine stateMachine) {
        _stateMachine = stateMachine;
    }

    protected GuiStateMachine getStateMachine() {
        return _stateMachine;
    }

    abstract void newFile();

    abstract void saveFile();

    abstract void openFile();

    abstract void precompile();

    abstract void compile();

    void quit() {
        getStateMachine().realQuit();
    }

    void notifyDirty() {
        getStateMachine().setCurrentState(getStateMachine().getDirtyState());
    }

    void saveAsFile() {
        try {
            getStateMachine().realSaveAsFile();
            getStateMachine().setCurrentState(getStateMachine().getSavedState());
        } catch (CancelException ce) {
            getStateMachine().statusMessage("'Opslaan als' geanulleerd.");
        }

    }

}
