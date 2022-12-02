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

import javax.swing.JOptionPane;

/**
 * State indicating that the program text is dirty.
 *
 * @version 1.0.0 09/06/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

class DirtyState

extends GuiState {
	DirtyState(GuiStateMachine stateMachine) {
		super(stateMachine);
	}

	void newFile() {
		try {
			if (askToSave())
				save();
			else
				getStateMachine().setCurrentState(
						getStateMachine().getSavedState());
			getStateMachine().newFile();
		} catch (CancelException ce) {
			getStateMachine().statusMessage("Openen van nieuw bestand geannuleerd.");
		}
	}

	void saveFile() {
		try {
			save();
		} catch (CancelException ce) {
			getStateMachine().statusMessage("Opslaan geannuleerd.");
		}
	}

	void saveAsFile() {
		saveFile();
	}

	void openFile() {
		try {
			if (askToSave())
				save();
			else
				getStateMachine().setCurrentState(getStateMachine().getSavedState());
			getStateMachine().openFile();
		} catch (CancelException ce) {
			getStateMachine().statusMessage("Openen geannuleerd.");
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
			getStateMachine().statusMessage("Compilatie niet geslaagd.");
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

	private void save() throws CancelException {
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

	private boolean askToSave() throws CancelException {
		int reply = JOptionPane.showOptionDialog(null, "Wil je het huidige project opslaan?", "Opslaan?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Ja", "Nee", "Annuleer"}, "Annuleer");
		if (reply == 2)
			throw new CancelException("User chose not save.");
		return reply == 0;
	}
}
