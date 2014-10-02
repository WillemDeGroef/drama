/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/statemachine/GuiStateMachine.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.statemachine;

import be.ac.kuleuven.cs.drama.gui.ExecutionState;
import be.ac.kuleuven.cs.drama.util.FileUtils;

import be.ac.kuleuven.cs.drama.gui.ActionManager;
import be.ac.kuleuven.cs.drama.gui.ActionImpl;
import be.ac.kuleuven.cs.drama.gui.GuiMain;

import be.ac.kuleuven.cs.drama.gui.ExtensionFileFilter;
import be.ac.kuleuven.cs.drama.gui.Settings;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.macro.MacroPreprocessor;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.Vertaler2;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Simple state machine for the transition
 * between the different states a program
 * can be in.
 *
 * @version 0.2.0 09/06/2000
 * @author  Tom Schrijvers
 */

public class GuiStateMachine {

   //private static final File PROGS_DIRECTORY = new File(Settings.DRAMADIR.concat("Progs/"));

   private final GuiMain _main;
   private final ActionManager _actionManager;

   private GuiState _currentState = new DummyState(this);

   private File _currentFile = null;

   private GuiState _dirtyState = null;
   private GuiState _savedState = null;
   private GuiState _precompiledState = null;
   private GuiState _compiledState = null;

   public GuiStateMachine(GuiMain main, ActionManager actionManager) {
      _main = main;
      _actionManager = actionManager;
      initActionManager();
   }

   public void start() {
      setCurrentState(getSavedState());
      _main.getEditFrame().addDocumentListener(
         new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
               notifyDirty();
            }

            public void removeUpdate(DocumentEvent e) {
               notifyDirty();
            }

            public void changedUpdate(DocumentEvent e) {}







         }

      );
   }

   private void initActionManager() {

      _actionManager.setNewFileAction(
         new ActionImpl() {
            public void react() {
               newFile();
            }

         }

      );

      _actionManager.setOpenFileAction(
         new ActionImpl() {
            public void react() {
               openFile();
            }

         }

      );


      _actionManager.setSaveFileAction(
         new ActionImpl() {
            public void react() {
               saveFile();
            }

         }

      );

      _actionManager.setSaveAsFileAction(
         new ActionImpl() {
            public void react() {
               saveAsFile();
            }

         }

      );

      _actionManager.setPrecompileAction(
         new ActionImpl() {
            public void react() {
               precompile();
            }

         }

      );

      _actionManager.setCompileAction(
         new ActionImpl() {
            public void react() {
               compile();
            }

         }

      );

      _actionManager.setQuitAction(
         new ActionImpl() {
            public void react() {
               quit();
            }

         }

      );
   }

   void setCurrentState(GuiState state) {
      _currentState = state;
      _currentState.initActionStates();
   }

   void newFile() {
      //System.out.println("GuiStateMachine.newFile()");
      _currentState.newFile();
   }

   void openFile() {
      //System.out.println("GuiStateMachine.openFile()");
      _currentState.openFile();
   }

   private void saveFile() {
      //System.out.println("GuiStateMachine.saveFile()");
      _currentState.saveFile();
   }

   private void saveAsFile() {
      _currentState.saveAsFile();
   }

   void precompile() {
      //System.out.println("GuiStateMachine.precompile()");
      _currentState.precompile();
   }

   void compile() {
      //System.out.println("GuiStateMachine.compile()");
      _currentState.compile();
   }

   private void quit() {
      _currentState.quit();
   }

   private void notifyDirty() {
      _currentState.notifyDirty();
   }

   void setExecuteActionsEnabled(boolean on) {
   }

   void realOpenFile()
   throws CancelException {

      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(Settings.CURRENT_DIRECTORY);
      ExtensionFileFilter filter = new ExtensionFileFilter();
      filter.addExtension("dra");
      filter.setDescription("drama programma's");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(_main.getEditFrame());
      Settings.CURRENT_DIRECTORY = chooser.getCurrentDirectory();

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         try {
            File file = chooser.getSelectedFile();
            String text = FileUtils.readFile(file);

            _main.getEditFrame().setText(text);
            _currentFile = file;
            _main.getEditFrame().setTitle("DramaSimulator - Editor - ".concat(_currentFile.getName()));
            _main.getEditFrame().clearStatusWindow();
         } catch (Exception e) {
            throw new CancelException();
         }
      } else {
         throw new CancelException();
      }

   }

   void realNewFile() {
      _currentFile = null;
      _main.getEditFrame().setText("");
      _main.getEditFrame().setTitle("DramaSimulator - Editor - Naamloos");
      _main.getEditFrame().clearStatusWindow();
   }

   void realSaveFile()
   throws CancelException {
      try {
         FileUtils.writeFile(_currentFile, _main.getEditFrame().getText());
         _main.getEditFrame().statusMessage("Bestand met succes opgeslagen.");
      } catch (Exception e) {
         throw new CancelException();
      }

   }

   void realSaveAsFile()
   throws CancelException {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(Settings.CURRENT_DIRECTORY);
      ExtensionFileFilter filter = new ExtensionFileFilter();
      filter.addExtension("dra");
      filter.setDescription("drama programma's");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showSaveDialog(_main.getEditFrame());
      Settings.CURRENT_DIRECTORY = chooser.getCurrentDirectory();

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         try {
            File file = FileUtils.ensureExtension(chooser.getSelectedFile(), "dra");
            FileUtils.writeFile(file, _main.getEditFrame().getText());
            _currentFile = file;
            _main.getEditFrame().setTitle("DramaSimulator - Editor - ".concat(_currentFile.getName()));
            _main.getEditFrame().statusMessage("Bestand met succes opgeslagen.");
         } catch (Exception e) {
            throw new CancelException();
         }
      } else {
         throw new CancelException();
      }

   }

   void realPrecompile() throws CancelException {

      File output = FileUtils.otherExtension(_currentFile, "pre");
      File map = FileUtils.otherExtension(_currentFile, "map");

      try {
         MacroPreprocessor mpp = new MacroPreprocessor(_currentFile, output, map);
         mpp.process();
         _main.getEditFrame().statusMessage("Voorvertaling met succes uitgevoerd.");
      } catch (AbnormalTerminationException ate) {
         statusMessage(ate.getMessage());
         throw new CancelException();
      }
      catch (IOException ioe) {
         statusMessage("I/O probleem.");
         throw new CancelException();
      }

   }

   void realCompile()
   throws CancelException {
      File input = FileUtils.otherExtension(_currentFile, "pre");
      File output = FileUtils.otherExtension(_currentFile, "out");
      File map = FileUtils.otherExtension(_currentFile, "map");

      try {
         Vertaler2 compiler = new Vertaler2(input, output, map);
         compiler.process();
         _main.getEditFrame().statusMessage("Vertaling met succes uitgevoerd.");
         _main.getEditFrame().statusMessage("********************************");
         _main.getExecuteFrame().programLoaded(output, FileUtils.readFile(input));
         _main.getExecutionManager().loadProgram(output);
      } catch (AbnormalTerminationException ate) {
         statusMessage(ate.getMessage());
         throw new CancelException();
      }
      catch (IOException ioe) {
         // IMPL
         throw new CancelException();
      }

   }

   void realQuit() {
      System.exit(0);
   }

   void realOptionalSaveAsFile()
   throws CancelException {
      int result = JOptionPane.showConfirmDialog(_main.getEditFrame(), "Wil je het programma opslaan?", "Programma is gewijzigd", JOptionPane.YES_NO_CANCEL_OPTION);

      if (result == JOptionPane.YES_OPTION) {
         realSaveAsFile();
      } else if (result == JOptionPane.NO_OPTION) {}
      else {
         throw new CancelException();
      }

   }

   boolean isNewFile() {
      return _currentFile == null;
   }

   GuiState getDirtyState() {
      if (_dirtyState == null) {
         _dirtyState = new DirtyState(this);
      }

      return _dirtyState;
   }

   GuiState getSavedState() {
      if (_savedState == null) {
         _savedState = new SavedState(this);
      }

      return _savedState;
   }

   GuiState getPrecompiledState() {
      if (_precompiledState == null) {
         _precompiledState = new PrecompiledState(this);
      }

      return _precompiledState;
   }

   GuiState getCompiledState() {
      if (_compiledState == null) {
         _compiledState = new CompiledState(this);
      }

      return _compiledState;
   }

   public File getCurrentFile() {
      return _currentFile;
   }

   void statusMessage(String message) {
      _main.getEditFrame().statusMessage(message);
   }

}
