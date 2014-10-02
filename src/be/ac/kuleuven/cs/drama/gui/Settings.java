/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/Settings.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui;

import be.ac.kuleuven.cs.drama.simulator.ControllableMachine;

import java.util.Properties;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.File;

import java.lang.reflect.Constructor;
import java.lang.ClassLoader;

import java.util.Map;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class to retrieve and provide the settings stored in the settings file.
 *
 * @version 1.0.0 08/28/2000
 * @author Tom Schrijvers
 */

public class Settings {

   /**
    * The current directory for FileChooserDialogs
    */
   public static File CURRENT_DIRECTORY = new File("./");

   /*
    * The icons
    */
   private static final Map ICON_MAP = new HashMap();

   /**
    * Icon id constants
    */
   public static final String NEW_FILE_ICON = "new.gif";
   public static final String OPEN_FILE_ICON = "open.gif";
   public static final String SAVE_FILE_ICON = "save.gif";
   public static final String PRECOMPILE_ICON = "mcompile.gif";
   public static final String COMPILE_ICON = "compile.gif";
   public static final String RUN_CONTINUE_ICON = "cont.gif";
   public static final String STEP_ICON = "step.gif";
   public static final String LOAD_ICON = "run.gif";
   public static final String STOP_ICON = "stop.gif";
   public static final String BREAKPOINT_ICON = "hand.gif";
   public static final String INTERNAL_MACHINE_ICON = "intern.gif";
   public static final String CPU_ICON = "showCVO.gif";
   public static final String BUILD_MACHINE_ICON = "build.gif";
   public static final String EXECUTE_ICON = "execute.gif";
   public static final String IRQ_ON_ICON = "irqon.gif";
   public static final String IRQ_OFF_ICON = "irqoff.gif";

   /*
    * The properties
    */
   private static final Properties _props;

   /**
    * Returns the icon corresponding to the given id.
    *
    * @return the icon with given id.
    */
   public static Icon getIcon(String id) {
      if (! ICON_MAP.containsKey(id)) {
         ICON_MAP.put(id, new ImageIcon(Settings.class.getResource("images/" + id)));
      }

      return (Icon) ICON_MAP.get(id);
   }



   // settings

   /*
    * Load the settings.
    */
   static {

      _props = new Properties();

      try {
         //System.out.println(ClassLoader.getSystemResource("settings.prop"));
         InputStream in = ClassLoader.getSystemResourceAsStream("settings.prop");
         _props.load(in);
      } catch (Exception e) {
         System.out.println("settings.prop: " + e.getMessage());
      }

   }

   /**
    * @param runtime the runime environment for the machine
    * @return the ControllableMachine to be used
    */
   public static ControllableMachine getMachine(DramaRuntime runtime) {

      try {

         String className = _props.getProperty("machine", "be.ac.kuleuven.cs.drama.simulator.simple.SimpleMachine");

         Constructor constructor = Class.forName(className).getConstructor(new Class[]{DramaRuntime.class});
         return (ControllableMachine) constructor.newInstance(new Object[]{runtime});

      } catch (Throwable t) {
         return new be.ac.kuleuven.cs.drama.simulator.simple.SimpleMachine(runtime);
      }

   }

   /**
    * @return wether the user is allowed to select the complexity level
    */
   public static boolean getComplexityAvailable() {
      try {
         return Boolean.valueOf(_props.getProperty("complexity", "false")).booleanValue();
      } catch (Throwable t) {
         return false;
      }

   }

   /**
    * @return wether the user should be allowed to build a machine
    */
   public static boolean getBuilderAvailable() {
      try {
         return Boolean.valueOf(_props.getProperty("builder"/*, "false"*/)).booleanValue();
      } catch (Throwable t) {
         t.printStackTrace();
         return false;
      }

   }

}
