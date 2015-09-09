/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/CompiledState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * State indicating that the program text is compiled.
 *
 * @version 0.9.0 09/06/2000
 * @author  Tom Schrijvers
 */

class CompiledState

extends GuiState {
	CompiledState(GuiStateMachine stateMachine) {
		super(stateMachine);
	}

	void newFile() {
		getStateMachine().realNewFile();
		getStateMachine().setCurrentState(getStateMachine().getSavedState());
	}

	void saveFile() {
		// NOTHING NECESSARY
	}

	void openFile() {
		try {
			getStateMachine().realOpenFile();
			getStateMachine().setCurrentState(getStateMachine().getSavedState());
		} catch (CancelException ce) {
			getStateMachine().statusMessage("Openen geanulleerd.");
		}

	}

	void precompile() {
		// NOTHING NECESSARY
	}

	void compile() {
		// NOTHING NECESSARY
	}

}
