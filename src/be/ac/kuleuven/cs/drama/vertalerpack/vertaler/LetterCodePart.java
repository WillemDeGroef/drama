/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/LetterCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

/**
 * A LetterCodePart is a superclass for all
 * ActiveCodeParts that have a keyword.
 *
 * @version 1.0.0 08/03/2000
 * @author Tom Schrijvers
 */

public abstract class LetterCodePart extends ActiveCodePart {

    // the arguments behind the keyword
    private String _argline;
    // the vertaler
    private Vertaler2 _vertaler;

    /**
     * @return the keyword
     */
    public abstract String keyword();

    /**
     * set the argumentline to the given string
     */
    protected void setArgLine(String argline) {
        //System.out.println("argline: " + argline);
        _argline = argline;
    }

    /**
     * @return the argument line
     */
    protected String getArgLine() {
        return _argline;
    }

    /**
     * set the vertaler
     */
    protected void setVertaler(Vertaler2 vertaler) {
        _vertaler = vertaler;
    }

    /**
     * @return the vertaler
     */
    protected Vertaler2 getVertaler() {
        return _vertaler;
    }

    /**
     * return a new instance of the same type
     * with a new argumentline and vertaler
     */
    public LetterCodePart newInstance(String argline, Vertaler2 vertaler) {
        LetterCodePart lcp = sibling();
        lcp.setArgLine(argline);
        lcp.setVertaler(vertaler);
        return lcp;
    }

    /**
     * @return a new instance of the same type
     */
    protected abstract LetterCodePart sibling();

}
