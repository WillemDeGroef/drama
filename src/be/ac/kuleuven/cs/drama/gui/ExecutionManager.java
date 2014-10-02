/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/ExecutionManager.java,v 1.2 2002/10/07 13:28:22 samm Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.events.InputRequestEventManager;
import be.ac.kuleuven.cs.drama.events.InputRequestListener;
import be.ac.kuleuven.cs.drama.events.InstructionRegisterEventManager;
import be.ac.kuleuven.cs.drama.events.InstructionRegisterListener;

import be.ac.kuleuven.cs.drama.util.FileUtils;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

import java.util.Hashtable;

public class ExecutionManager

   implements ExecutionEnvironment {

   private final GuiMain _main;
   private final GUIController _guiController;
   private File _loadedProgram; // .out file

   public ExecutionManager(GuiMain main) {
      _main = main;
      _guiController = new GUIController(this);
      initActions();

//      InstructionRegisterEventManager.addListener(
//         new InstructionRegisterListener() {
//            public void handleInstructionRegisterEvent() {
//               final int bt = (int) _guiController.getBT();
//               EventQueue.invokeLater(new Runnable() {
//                  public void run() {
//                     _main.getExecuteFrame().currentInstructionChanged(bt);
//                  }
//               });
//            }
//         }
//      );
   }

   private void initActions() {
      addContinueAction();
      addStepAction();
      addStopAction();
      addResetAction();
      addInternalMachineAction();
      addCpuAction();
      addComplexComplexityAction();
      addInputFromFileAction();
      addInputFromScreenAction();
      addOutputToFileAction();
      addOutputToScreenAction();
      addSimpleComplexityAction();
   }

   private void addContinueAction() {
      _main.getActionManager().setContinueAction(
         new ActionImpl() {
            public void react() {
               _guiController.cont();
            }

         }

      );
   }

   private void addStepAction() {
      _main.getActionManager().setStepAction(
         new ActionImpl() {
            public void react() {
               _guiController.step();
            }

         }

      );
   }

   private void addStopAction() {
      _main.getActionManager().setStopAction(
         new ActionImpl() {
            public void react() {
               _guiController.halt();
            }

         }

      );
   }

   private void addResetAction() {
      _main.getActionManager().setResetAction(
         new ActionImpl() {
            public void react() {
               _guiController.reset();
            }

         }

      );
   }

   private void addInternalMachineAction() {
      _main.getActionManager().setInternalMachineAction(
         new ActionImpl() {
            public void react() {
               _guiController.showInternalMachine();
            }

         }

      );
   }

   private void addCpuAction() {
      _main.getActionManager().setCpuAction(
         new ActionImpl() {
            public void react() {
               _guiController.showCVO();
            }

         }

      );
   }

   private void addSimpleComplexityAction() {
      _main.getActionManager().setSimpleComplexityAction(
         new ActionImpl() {
            public void react() {
               _guiController.setNiveau(1);
               _main.getActionManager().getSimpleComplexityAction().setEnabled(false);
               _main.getActionManager().getComplexComplexityAction().setEnabled(true);
            }

         }

      );
   }

   private void addComplexComplexityAction() {
      _main.getActionManager().setComplexComplexityAction(
         new ActionImpl() {
            public void react() {
               _guiController.setNiveau(2);
               _main.getActionManager().getSimpleComplexityAction().setEnabled(true);
               _main.getActionManager().getComplexComplexityAction().setEnabled(false);
            }

         }

      );
   }

   private void addInputFromScreenAction() {
      _main.getActionManager().setInputFromScreenAction(
         new ActionImpl() {
            public void react() {
               _guiController.resetInputStream();
               _main.getActionManager().getInputFromScreenAction().setEnabled(false);
               _main.getActionManager().getInputFromFileAction().setEnabled(true);
               _main.getExecuteFrame().statusMessage("Invoer van scherm.");
            }

         }

      );
      _main.getActionManager().getInputFromScreenAction().setEnabled(false);
   }

   private void addInputFromFileAction() {
      _main.getActionManager().setInputFromFileAction(
         new ActionImpl() {
            public void react() {
               JFileChooser chooser = new JFileChooser();
               chooser.setCurrentDirectory(Settings.CURRENT_DIRECTORY);
               //chooser.setCurrentDirectory(new File(Settings.DRAMADIR));
               ExtensionFileFilter filter = new ExtensionFileFilter();
               filter.addExtension("geg");
               filter.setDescription("drama invoer");
               chooser.setFileFilter(filter);
               int returnVal = chooser.showOpenDialog(_main.getExecuteFrame());
               Settings.CURRENT_DIRECTORY = chooser.getCurrentDirectory();

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  try {
                     File file = chooser.getSelectedFile();
                     BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                     _guiController.setInputStream(in);
                     _main.getActionManager().getInputFromScreenAction().setEnabled(true);
                     _main.getActionManager().getInputFromFileAction().setEnabled(false);
                     _main.getExecuteFrame().statusMessage(file.toString().concat(" ingesteld als invoerbestand."));
                  } catch (Exception e) {}







               } else {}







            }

         }

      );
   }

   private void addOutputToScreenAction() {
      _main.getActionManager().setOutputToScreenAction(
         new ActionImpl() {
            public void react() {
               _guiController.resetOutputStream();
               _main.getActionManager().getOutputToScreenAction().setEnabled(false);
               _main.getActionManager().getOutputToFileAction().setEnabled(true);
               _main.getExecuteFrame().statusMessage("Uitvoer naar scherm.");
            }

         }

      );
      _main.getActionManager().getOutputToScreenAction().setEnabled(false);
   }

   private void addOutputToFileAction() {
      _main.getActionManager().setOutputToFileAction(
         new ActionImpl() {
            public void react() {
               JFileChooser chooser = new JFileChooser();
               chooser.setCurrentDirectory(Settings.CURRENT_DIRECTORY);
               //chooser.setCurrentDirectory(new File(Settings.DRAMADIR));
               ExtensionFileFilter filter = new ExtensionFileFilter();
               filter.addExtension("txt");
               filter.setDescription("drama uitvoer");
               chooser.setFileFilter(filter);
               int returnVal = chooser.showOpenDialog(_main.getExecuteFrame());
               Settings.CURRENT_DIRECTORY = chooser.getCurrentDirectory();

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  try {
                     File file = FileUtils.ensureExtension(chooser.getSelectedFile(), "txt");
                     BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                     _guiController.setOutputStream(out);
                     _main.getActionManager().getOutputToScreenAction().setEnabled(true);
                     _main.getActionManager().getOutputToFileAction().setEnabled(false);
                     _main.getExecuteFrame().statusMessage(file.toString().concat(" ingesteld als uitvoerbestand."));
                  } catch (Exception e) {}







               } else {}







            }

         }

      );
   }

   public void init() {

      _main.getExecuteFrame().insertOutputComponent(_guiController.getMonitor());
      _main.getExecuteFrame().insertInputComponent(createInputPanel());


   }

   private JPanel createInputPanel() {
      final JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(2, 1));

      final JLabel label = new JLabel("Invoer:");
      label.setHorizontalAlignment(JLabel.LEFT);
      panel.add(label);

      final JTextField textField = new JTextField();

      ActionImpl actionImpl = new ActionImpl() {
                                 public void react() {
                                    _guiController.handleInput(textField.getText().concat("\n"));
                                    textField.setText("");

				    textField.setEnabled(false);
                                    label.setForeground(Color.gray);
                                    label.setEnabled(false);
                                 }

                              };

      _main.getActionManager().setScreenInputAction(actionImpl);
      textField.setAction(_main.getActionManager().getScreenInputAction());

      panel.add(textField);

      InputRequestEventManager.addListener(
         new InputRequestListener() {
            public void handleInputRequest() {
               EventQueue.invokeLater(new Runnable() {
                  public void run() {
                     textField.setEnabled(true);
                     textField.requestFocus();
                     label.setEnabled(true);
                     label.setForeground(Color.red);
                  }
               });
            }

         }

      );

      textField.setEnabled(false);
      label.setEnabled(false);
      label.setForeground(Color.gray);

      return panel;
   }

   // ExecutionEnvironment implementation


   public String getFileName() {
      return _loadedProgram.toString();
   }



   public void systemMessage(String message) {
      _main.getExecuteFrame().statusMessage(message);
   }



   public Hashtable getBreakPoints() {
      return _main.getExecuteFrame().getBreakPoints();
   }
   
   public void programLoaded() {
      _main.getExecuteFrame().programLoaded();
   }
   
   public void programRunning() {
      _main.getExecuteFrame().programRunning();
   }

   public void programStepping() {
      _main.getExecuteFrame().programStepping();
   }
   
   public void programHalted() {
      _main.getExecuteFrame().programHalted((int)_guiController.getBT());
   }

   public void programFinished() {
      _main.getExecuteFrame().programFinished();
   }

   public void loadProgram(File output) {
      _loadedProgram = output;
      _guiController.loadProgram();
   }
}

