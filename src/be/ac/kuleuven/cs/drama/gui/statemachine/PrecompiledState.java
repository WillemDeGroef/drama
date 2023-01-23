/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/PrecompiledState.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

/**
 * State indicating that the program text is precompiled.
 *
 * @version 1.0.0 09/06/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

class PrecompiledState

extends GuiState {
	PrecompiledState(GuiStateMachine stateMachine) {
		super(stateMachine);
	}

	void newFile() {
		if (getStateMachine().isEnabled()) {
			getStateMachine().realNewFile();
			getStateMachine().setCurrentState(getStateMachine().getSavedState());
		}
	}

	void saveFile() {
		// NOTHING NECESSARY
	}

	void openFile() {
		if (getStateMachine().isEnabled())
			try {
				getStateMachine().realOpenFile();
				getStateMachine().setCurrentState(getStateMachine().getSavedState());
			} catch (CancelException ce) {
				getStateMachine().statusMessage("Openen geannuleerd.");
			}
		}

	void precompile() {
		// NOTHING NECESSARY
	}

	void compile() {
		getStateMachine().setEnabled(false);
		try {
			getStateMachine().realCompile();
			getStateMachine().setCurrentState(getStateMachine().getCompiledState());
		} catch (CancelException ce) {
			getStateMachine().statusMessage("Compilatie niet geslaagd.");
		} finally {
			getStateMachine().setEnabled(true);
		}
	}
}
