/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/GuiMain.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.gui;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import be.ac.kuleuven.cs.drama.gui.statemachine.GuiStateMachine;
import be.ac.kuleuven.cs.drama.util.StatistiekModule;

/**
 * Main class that creates the gui.
 *
 * @version 1.0.0 09/06/2015
 * @author Tom Schrijvers
 * @author Jo-Thijs Daelman
 */

public class GuiMain {

    private static final String HELP_INDEX = "help/index.htm";
    private static final String ABOUT = "help/about.htm";

    private final ActionManager _actionManager;

    private final GuiStateMachine _stateMachine;
    private final ExecutionManager _executionManager;

    private final EditFrame _editFrame;
    private final ExecuteFrame _executeFrame;

    /**
     * Initialize.
     */
    public GuiMain() {

        _actionManager = new ActionManager();

        addRunStatisticsAction();
        addCompileStatisticsAction();
        addHelpIndexAction();
        addAboutAction();
        addCloseExecuteFrameAction();
        addShowExecuteFrameAction();

        _stateMachine = new GuiStateMachine(this, _actionManager);
        _executionManager = new ExecutionManager(this);

        _editFrame = new EditFrame(_actionManager);
        _executeFrame = new ExecuteFrame(_actionManager);

        _executionManager.init();

        _executeFrame.setExecutionState(ExecutionState.NO_CODE);

        Image icon = new ImageIcon(Settings.class.getResource(Settings.LOGO_ICON)).getImage();
        _editFrame.setIconImage(icon);
        _executeFrame.setIconImage(icon);
    }

    public ActionManager getActionManager() {
        return _actionManager;
    }

    public EditFrame getEditFrame() {
        return _editFrame;
    }

    public ExecuteFrame getExecuteFrame() {
        return _executeFrame;
    }

    public GuiStateMachine getStateMachine() {
        return _stateMachine;
    }

    public ExecutionManager getExecutionManager() {
        return _executionManager;
    }

    /**
     * Makes the gui visible.
     */
    public void start() {
        _stateMachine.start();
        _editFrame.setVisible(true);
    }

    // statistics

    private void addCompileStatisticsAction() {
        _actionManager.setCompileStatisticsAction(new ActionImpl() {
            public void react() {
                String stats = new StatistiekModule().readFile("compstat.txt");
                showOKMessage(stats, "Compile time statistieken");
            }
        });
    }

    private void addRunStatisticsAction() {
        _actionManager.setRunStatisticsAction(new ActionImpl() {
            public void react() {
                String stats = new StatistiekModule().readFile("runstat.txt");
                showOKMessage(stats, "Runtime time statistieken");
            }
        });
    }

    private void addCloseExecuteFrameAction() {
        _actionManager.setCloseExecuteFrameAction(new ActionImpl() {
            public void react() {
                getExecuteFrame().setVisible(false);
            }
        });
    }

    private void addShowExecuteFrameAction() {
        _actionManager.setShowExecuteFrameAction(new ActionImpl() {
            public void react() {
                getExecuteFrame().setVisible(true);
            }
        });
    }

    // help actions

    private void addHelpIndexAction() {
        _actionManager.setHelpIndexAction(new ActionImpl() {
            public void react() {
                openBrowser(HELP_INDEX);
            }
        });
    }

    private void addAboutAction() {
        _actionManager.setAboutAction(new ActionImpl() {
            public void react() {
                openBrowser(ABOUT);
            }
        });
    }

    private void openBrowser(String file) {
        try {
            File htmlFile = new File(file);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            showOKMessage(
                    "Probleem bij het openen van de browser.\n"
                            + "Open zelf " + file + " in een browser.",
                    "Environment probleem");
        }
    }

    private void showOKMessage(String message, String title) {
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    }

}
