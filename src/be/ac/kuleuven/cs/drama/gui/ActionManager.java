/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ActionManager.java,v 1.1.1.1 2001/09/07 09:41:37 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui;

import javax.swing.Action;

/**
 * Class that manages the necessary set of actions for the gui.
 *
 * @version 1.0.0 09/06/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public class ActionManager {

    private Action _newFile;
    private Action _saveFile;
    private Action _saveAsFile;
    private Action _openFile;

    private Action _quit;

    private Action _precompile;
    private Action _compile;

    private Action _continue;
    private Action _step;
    private Action _stop;
    private Action _reset;

    private Action _internalMachine;
    private Action _cpu;

    private Action _buildMachine;
    private Action _simpleComplexity;
    private Action _complexComplexity;

    private Action _helpIndex;
    private Action _about;

    private Action _compileStatistics;
    private Action _runStatistics;

    private Action _inputFromScreen;
    private Action _inputFromFile;

    private Action _outputToScreen;
    private Action _outputToFile;

    private Action _screenInput;

    private Action _closeExecuteFrame;
    private Action _showExecuteFrame;

    private Action _clearFrame;

    private Action _ideBlackWhite;
    private Action _ideLight;
    private Action _ideDark;

    private Action _stopPrecompilation;

    public void setNewFileAction(ActionImpl actionImpl) {
        _newFile = new SimpleAction("Nieuw",
                Settings.getIcon(Settings.NEW_FILE_ICON), actionImpl);
    }

    public Action getNewFileAction() {
        if (_newFile == null) {
            setNewFileAction(new DummyActionImpl("new file action"));
        }

        return _newFile;
    }

    public void setSaveFileAction(ActionImpl actionImpl) {
        _saveFile = new SimpleAction("Opslaan",
                Settings.getIcon(Settings.SAVE_FILE_ICON), actionImpl);
    }

    public Action getSaveFileAction() {
        if (_saveFile == null) {
            setSaveFileAction(new DummyActionImpl("save file action"));
        }

        return _saveFile;
    }

    public void setSaveAsFileAction(ActionImpl actionImpl) {
        _saveAsFile = new SimpleAction("Opslaan als", actionImpl);
    }

    public Action getSaveAsFileAction() {
        if (_saveAsFile == null) {
            setSaveAsFileAction(new DummyActionImpl("save as file action"));
        }

        return _saveAsFile;
    }

    public void setOpenFileAction(ActionImpl actionImpl) {
        _openFile = new SimpleAction("Open",
                Settings.getIcon(Settings.OPEN_FILE_ICON), actionImpl);
    }

    public Action getOpenFileAction() {
        if (_openFile == null) {
            setOpenFileAction(new DummyActionImpl("open file action"));
        }

        return _openFile;
    }

    public void setQuitAction(ActionImpl actionImpl) {
        _quit = new SimpleAction("Afsluiten", actionImpl);
    }

    public Action getQuitAction() {
        if (_quit == null) {
            setQuitAction(new DummyActionImpl("quit action"));
        }

        return _quit;
    }

    public void setPrecompileAction(ActionImpl actionImpl) {
        _precompile = new SimpleAction("Voorvertalen",
                Settings.getIcon(Settings.PRECOMPILE_ICON), actionImpl);
    }

    public Action getPrecompileAction() {
        if (_precompile == null) {
            setPrecompileAction(new DummyActionImpl("precompile action"));
        }

        return _precompile;
    }

    public void setCompileAction(ActionImpl actionImpl) {
        _compile = new SimpleAction("Vertalen",
                Settings.getIcon(Settings.COMPILE_ICON), actionImpl);
    }

    public Action getCompileAction() {
        if (_compile == null) {
            setCompileAction(new DummyActionImpl("compile action"));
        }

        return _compile;
    }

    public void setContinueAction(ActionImpl actionImpl) {
        _continue = new SimpleAction("Starten/doorgaan",
                Settings.getIcon(Settings.RUN_CONTINUE_ICON), actionImpl);
    }

    public Action getContinueAction() {
        if (_continue == null) {
            setContinueAction(new DummyActionImpl("continue action"));
        }

        return _continue;
    }

    public void setStepAction(ActionImpl actionImpl) {
        _step = new SimpleAction("Stap", Settings.getIcon(Settings.STEP_ICON),
                actionImpl);
    }

    public Action getStepAction() {
        if (_step == null) {
            setStepAction(new DummyActionImpl("step action"));
        }

        return _step;
    }

    public void setStopAction(ActionImpl actionImpl) {
        _stop = new SimpleAction("Stop", Settings.getIcon(Settings.STOP_ICON),
                actionImpl);
    }

    public Action getStopAction() {
        if (_stop == null) {
            setStopAction(new DummyActionImpl("stop action"));
        }

        return _stop;
    }

    public void setResetAction(ActionImpl actionImpl) {
        _reset = new SimpleAction("Reset",
                Settings.getIcon(Settings.LOAD_ICON), actionImpl);
    }

    public Action getResetAction() {
        if (_reset == null) {
            setResetAction(new DummyActionImpl("reset action"));
        }

        return _reset;
    }

    public void setInternalMachineAction(ActionImpl actionImpl) {
        _internalMachine = new SimpleAction("Interne machine",
                Settings.getIcon(Settings.INTERNAL_MACHINE_ICON), actionImpl);
    }

    public Action getInternalMachineAction() {
        if (_internalMachine == null) {
            setInternalMachineAction(new DummyActionImpl(
                    "internal machine action"));
        }

        return _internalMachine;
    }

    public void setCpuAction(ActionImpl actionImpl) {
        _cpu = new SimpleAction("CVO", Settings.getIcon(Settings.CPU_ICON),
                actionImpl);
    }

    public Action getCpuAction() {
        if (_cpu == null) {
            setCpuAction(new DummyActionImpl("cpu action"));
        }

        return _cpu;
    }

    public void setBuildMachineAction(ActionImpl actionImpl) {
        _buildMachine = new SimpleAction("Bouw machine",
                Settings.getIcon(Settings.BUILD_MACHINE_ICON), actionImpl);
        _buildMachine.setEnabled(Settings.getBuilderAvailable());
    }

    public Action getBuildMachineAction() {
        if (_buildMachine == null) {
            setBuildMachineAction(new DummyActionImpl("build machine action"));
        }

        return _buildMachine;
    }

    public void setSimpleComplexityAction(ActionImpl actionImpl) {
        _simpleComplexity = new SimpleAction("Eenvoudig", actionImpl);
        _simpleComplexity.setEnabled(Settings.getComplexityAvailable());
    }

    public Action getSimpleComplexityAction() {
        if (_simpleComplexity == null) {
            setSimpleComplexityAction(new DummyActionImpl(
                    "simple complexity action"));
        }

        return _simpleComplexity;
    }

    public void setComplexComplexityAction(ActionImpl actionImpl) {
        _complexComplexity = new SimpleAction("Complex", actionImpl);
        _complexComplexity.setEnabled(false);
    }

    public Action getComplexComplexityAction() {
        if (_complexComplexity == null) {
            setComplexComplexityAction(new DummyActionImpl(
                    "complex complexity action"));
        }

        return _complexComplexity;
    }

    public void setHelpIndexAction(ActionImpl actionImpl) {
        _helpIndex = new SimpleAction("Index", actionImpl);
    }

    public Action getHelpIndexAction() {
        if (_helpIndex == null) {
            setHelpIndexAction(new DummyActionImpl("help index action"));
        }

        return _helpIndex;
    }

    public void setAboutAction(ActionImpl actionImpl) {
        _about = new SimpleAction("Over", actionImpl);
    }

    public Action getAboutAction() {
        if (_about == null) {
            setAboutAction(new DummyActionImpl("about action"));
        }

        return _about;
    }

    public void setCompileStatisticsAction(ActionImpl actionImpl) {
        _compileStatistics = new SimpleAction("Vertaalstatistieken", actionImpl);
    }

    public Action getCompileStatisticsAction() {
        if (_compileStatistics == null) {
            setCompileStatisticsAction(new DummyActionImpl(
                    "compile statistics action"));
        }

        return _compileStatistics;
    }

    public void setRunStatisticsAction(ActionImpl actionImpl) {
        _runStatistics = new SimpleAction("Uitvoeringsstatistieken", actionImpl);
    }

    public Action getRunStatisticsAction() {
        if (_runStatistics == null) {
            setRunStatisticsAction(new DummyActionImpl("run statistics action"));
        }

        return _runStatistics;
    }

    public void setInputFromScreenAction(ActionImpl actionImpl) {
        _inputFromScreen = new SimpleAction("Invoer van scherm", actionImpl);
    }

    public Action getInputFromScreenAction() {
        if (_inputFromScreen == null) {
            setInputFromScreenAction(new DummyActionImpl(
                    "input from screen action"));
        }

        return _inputFromScreen;
    }

    public void setInputFromFileAction(ActionImpl actionImpl) {
        _inputFromFile = new SimpleAction("Invoer van bestand", actionImpl);
    }

    public Action getInputFromFileAction() {
        if (_inputFromFile == null) {
            setInputFromFileAction(new DummyActionImpl("input from file action"));
        }

        return _inputFromFile;
    }

    public void setOutputToScreenAction(ActionImpl actionImpl) {
        _outputToScreen = new SimpleAction("Uitvoer naar scherm", actionImpl);
    }

    public Action getOutputToScreenAction() {
        if (_outputToScreen == null) {
            setOutputToScreenAction(new DummyActionImpl(
                    "output to screen action"));
        }

        return _outputToScreen;
    }

    public void setOutputToFileAction(ActionImpl actionImpl) {
        _outputToFile = new SimpleAction("Uitvoer naar bestand", actionImpl);
    }

    public Action getOutputToFileAction() {
        if (_outputToFile == null) {
            setOutputToFileAction(new DummyActionImpl("output to file action"));
        }

        return _outputToFile;
    }

    public void setScreenInputAction(ActionImpl actionImpl) {
        _screenInput = new SimpleAction("Invoer", actionImpl);
    }

    public Action getScreenInputAction() {
        if (_screenInput == null) {
            setScreenInputAction(new DummyActionImpl("screen input action"));
        }

        return _screenInput;
    }

    public void setCloseExecuteFrameAction(ActionImpl actionImpl) {
        _closeExecuteFrame = new SimpleAction("Sluiten", actionImpl);
    }

    public Action getCloseExecuteFrameAction() {
        if (_closeExecuteFrame == null) {
            setCloseExecuteFrameAction(new DummyActionImpl(
                    "close execute frame action"));
        }

        return _closeExecuteFrame;
    }

    public void setShowExecuteFrameAction(ActionImpl actionImpl) {
        _showExecuteFrame = new SimpleAction("Uitvoeringsvenster",
                Settings.getIcon(Settings.EXECUTE_ICON), actionImpl);
    }

    public Action getShowExecuteFrameAction() {
        if (_showExecuteFrame == null) {
            setShowExecuteFrameAction(new DummyActionImpl(
                    "show execute frame action"));
        }

        return _showExecuteFrame;
    }

    public void setClearFrameAction(ActionImpl actionImpl) {
        _clearFrame = new SimpleAction("Maak scherm schoon",
                Settings.getIcon(Settings.CLEAR_ICON), actionImpl);
    }

    public Action getClearFrameAction() {
        if (_clearFrame == null) {
            setClearFrameAction(new DummyActionImpl("clear frame action"));
        }

        return _clearFrame;
    }

    public void setIdeBlackWhiteAction(ActionImpl actionImpl) {
        _ideBlackWhite = new SimpleAction("Zwart op wit", null, actionImpl);
    }

    public Action getIdeBlackWhiteAction() {
        if (_ideBlackWhite == null)
            setIdeBlackWhiteAction(new DummyActionImpl("black on white action"));

        return _ideBlackWhite;
    }

    public void setIdeLightAction(ActionImpl actionImpl) {
        _ideLight = new SimpleAction("Licht thema", null, actionImpl);
    }

    public Action getIdeLightAction() {
        if (_ideLight == null)
            setIdeLightAction(new DummyActionImpl("light theme action"));

        return _ideLight;
    }

    public void setIdeDarkAction(ActionImpl actionImpl) {
        _ideDark = new SimpleAction("Donker thema", null, actionImpl);
    }

    public Action getIdeDarkAction() {
        if (_ideDark == null)
            setIdeDarkAction(new DummyActionImpl("dark theme action"));

        return _ideDark;
    }

    public void setStopPrecompilationAction(ActionImpl actionImpl) {
        _stopPrecompilation = new SimpleAction("Stop voorvertaling", Settings.getIcon(Settings.STOP_PRECOMPILE_ICON), actionImpl);
    }

    public Action getStopPrecompilationAction() {
        if (_stopPrecompilation == null)
            setStopPrecompilationAction(new DummyActionImpl("stop precompilation action"));

        return _stopPrecompilation;
    }

    // debug

    private static class DummyActionImpl implements ActionImpl {
        private final String _text;

        public DummyActionImpl(String text) {
            _text = text;
        }

        public void react() {
            System.out.println(_text);
        }

    }

}
