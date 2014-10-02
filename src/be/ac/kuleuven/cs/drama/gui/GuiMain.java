/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/GuiMain.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.util.StatistiekModule;
import be.ac.kuleuven.cs.drama.gui.statemachine.GuiStateMachine;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.net.URL;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

/**
 * Main class that creates the gui.
 *
 * @version 0.0.2 09/06/2000
 * @author  Tom Schrijvers
 */

public class GuiMain {

   private static final String HELP_INDEX = "Help/index.htm";
   private static final String ABOUT = "Help/about.htm";

   private final ActionManager _actionManager;

   private final GuiStateMachine _stateMachine;
   private final ExecutionManager _executionManager;

   private final EditFrame _editFrame;
   private final ExecuteFrame _executeFrame;

   private String _browserCall;

   /**
    * Initialize.
    */
   public GuiMain() {

      _actionManager = new ActionManager();

      //addQuitAction();
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

   // quit action (temporary)

   private void addQuitAction() {
      _actionManager.setQuitAction(
         new ActionImpl() {
            public void react() {
               // IMPL
               _editFrame.setVisible(false);
               _editFrame.dispose();
               _executeFrame.setVisible(false);
               _executeFrame.dispose();
               System.exit(0);
            }

         }

      );
   }

   // statistics

   private void addCompileStatisticsAction() {
      _actionManager.setCompileStatisticsAction(
         new ActionImpl() {
            public void react() {
               //try{
               String stats = new StatistiekModule().readFile("compstat.txt");
               showOKMessage(stats, "Compile time statistieken");
               //} catch (IOException ioe){
               // showOKMessage("Er is een fout opgetreden bij het inlezen van compstat.txt", "I/O fout");
               //}
            }










         }

      );
   }

   private void addRunStatisticsAction() {
      _actionManager.setRunStatisticsAction(
         new ActionImpl() {
            public void react() {
               //try{
               String stats = new StatistiekModule().readFile("runstat.txt");
               showOKMessage(stats, "Runtime time statistieken");
               //} catch (IOException ioe){
               // showOKMessage("Er is een fout opgetreden bij het inlezen van runstat.txt", "I/O fout");
               //}
            }










         }

      );
   }

   private void addCloseExecuteFrameAction() {
      _actionManager.setCloseExecuteFrameAction(
         new ActionImpl() {
            public void react() {
               getExecuteFrame().setVisible(false);
            }

         }

      );
   }

   private void addShowExecuteFrameAction() {
      _actionManager.setShowExecuteFrameAction(
         new ActionImpl() {
            public void react() {
               getExecuteFrame().setVisible(true);
            }

         }

      );
   }

   // help actions

   private void addHelpIndexAction() {
      _actionManager.setHelpIndexAction(
         new ActionImpl() {
            public void react() {
               openBrowser(HELP_INDEX);
            }

         }

      );
   }

   private void addAboutAction() {
      _actionManager.setAboutAction(
         new ActionImpl() {
            public void react() {
               openBrowser(ABOUT);
            }

         }

      );
   }

   private void openBrowser(String file) {
      try {
         URL url = new URL("file://" + System.getProperty("user.dir") + "/" + file);
         Runtime.getRuntime().exec(getBrowserCall() + " " + url.toString());
      } catch (Exception e) {
         showOKMessage("Probleem bij het openen van de browser.\n\nOpen zelf " + file +
                       " in een browser.\nPas de settings.prop file aan om vanuit dit programma de browser te kunnen opstarten.", "Environment probleem");
      }

   }

   private void showOKMessage(String message, String title) {
      Object options[] = {"OK"};
      JOptionPane.showOptionDialog(
         null,
         message,
         title,
         JOptionPane.DEFAULT_OPTION,
         JOptionPane.INFORMATION_MESSAGE,
         null,
         options,
         options[0]);
   }

   private String getBrowserCall() {
      if (_browserCall == null) {
         _browserCall = readBrowserCall();
      }

      return _browserCall;
   }

   private String readBrowserCall() {
      Properties props = new Properties();
      String result = null;

      try {
         InputStream is = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("settings.prop"));
         props.load(is);
         result = props.getProperty("Browser", "");
      } catch (Exception e) {
         System.err.print("Fout bij het inlezen van de browservariabele: " + e);
      }

      return result == null ? "" : result;
   }

}
