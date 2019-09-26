/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/NoCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

/**
 * The NoCodePart is an ActiveCodePart for an empty line.
 *
 * @version 1.0.0 08/03/2000
 * @author Tom Schrijvers
 */

public class NoCodePart extends ActiveCodePart {

    /**
     * Initialize a new NoCodePart
     */
    public NoCodePart() {
    }


    public int nbObjectLines() {
        return 0;
    }

    public String[] getObjectLines(int objectln) {
        return new String[]{};
    }

}
