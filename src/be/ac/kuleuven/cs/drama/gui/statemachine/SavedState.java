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
 * State that represents the clean situation of the program text, either not
 * edited or new. Precompilation of compilation has not occured yet.
 *
 * @version 0.7.0 09/06/2015
 * @author  Tom Schrijvers
 * @author  Jo-Thijs Daelman
 */

class SavedState extends GuiState {

	SavedState(GuiStateMachine stateMachine) {
		super(stateMachine);
	}

	void newFile() {
		if (getStateMachine().isEnabled())
			getStateMachine().realNewFile();
	}

	void saveFile() {
		if (getStateMachine().isEnabled())
			if (getStateMachine().isNewFile()) {
				try {
					getStateMachine().realSaveAsFile();
				} catch (CancelException ce) {
					getStateMachine().statusMessage("Opslaan geanulleerd.");
				}
			}
	}

	void openFile() {
		if (getStateMachine().isEnabled())
			try {
				getStateMachine().realOpenFile();
			} catch (CancelException ce) {
				getStateMachine().statusMessage("Openen geanulleerd.");
			}
	}

	void precompile() {
		if (getStateMachine().isEnabled())
			new Thread(new Runnable() {
				public void run() {
					try {
						if (getStateMachine().isNewFile())
							getStateMachine().realSaveAsFile();
						
						getStateMachine().setEnabled(false);

						getStateMachine().realPrecompile();
						getStateMachine().setCurrentState(getStateMachine().getPrecompiledState());
					} catch (CancelException ce) {
						getStateMachine().statusMessage("Precompilatie niet geslaagd.");
					} finally {
						getStateMachine().setEnabled(true);
					}
				}
			}).start();
	}

	void compile() {
		if (getStateMachine().isEnabled())
			new Thread(new Runnable() {
				public void run() {
					try {
						if (getStateMachine().isNewFile())
							getStateMachine().realSaveAsFile();
						
						getStateMachine().setEnabled(false);
			
						getStateMachine().realPrecompile();
						getStateMachine().setCurrentState(getStateMachine().getPrecompiledState());
						getStateMachine().compile();
					} catch (CancelException ce) {
						getStateMachine().statusMessage("Compilatie niet geslaagd.");
					} finally {
						getStateMachine().setEnabled(true);
					}
			
				}
			}).start();
	}
}
