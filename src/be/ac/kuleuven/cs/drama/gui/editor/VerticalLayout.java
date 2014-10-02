/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/gui/editor/VerticalLayout.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.gui.editor;

import java.awt.*;

/**
 * Simple LayoutManager that lays out all components
 * vertically. All with size equall to the preferred size
 * of the first component. There is a 3 pixel margin
 * between the first component and the top border.
 *
 * @version 1.0.0 08/28/2000
 * @author  Tom Schrijvers
 */

public class VerticalLayout
   implements LayoutManager {

   /**
    * Adds the specified component with the specified name to
    * the layout.
    * @param name the component name
    * @param comp the component to be added
    */
   public void addLayoutComponent(String name, Component comp) {}

   /**
    * Removes the specified component from the layout.
    * @param comp the component ot be removed
    */
   public void removeLayoutComponent(Component comp) {}

   /**
    * Calculates the preferred size dimensions for the specified 
    * panel given the components in the specified parent container.
    * @param parent the component to be laid out
    *  
    * @see #minimumLayoutSize
    */
   public Dimension preferredLayoutSize(Container parent) {
      return layoutSize(parent);
   }

   /**
    * Calculates the minimum size dimensions for the specified 
    * panel given the components in the specified parent container.
    * @param parent the component to be laid out
    * @see #preferredLayoutSize
    */
   public Dimension minimumLayoutSize(Container parent) {
      return layoutSize(parent);
   }

   /**
    * Lays out the container in the specified panel.
    * @param parent the component which needs to be laid out 
    */
   public void layoutContainer(Container parent) {

      if (parent.getComponentCount() > 0) {

         int width = (int) parent.getComponent(0).getPreferredSize().getWidth();
         int height = (int) parent.getComponent(0).getPreferredSize().getHeight();

         int y = 3;

         for (int i = 0; i < parent.getComponentCount(); i++) {
            parent.getComponent(i).setBounds(0, y, width, height);
            y += height;
         }

      }

   }

   private Dimension layoutSize(Container parent) {
      if (parent.getComponentCount() == 0) {
         return new Dimension(0, 0);
      } else {
         return new Dimension(
                   (int) parent.getComponent(0).getPreferredSize().getWidth(),
                   (int) (parent.getComponentCount() * parent.getComponent(0).getPreferredSize().getHeight()));
      }

   }

}
