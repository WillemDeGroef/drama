/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/basis/Register.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.basis;

/** Klasse voor bewerkingen op registers
 *
 * @version 1.0.1 13-jan-1999
 * @author Tom Vekemans
 */

public abstract class Register {
    /**initialiseer dit Register (zet op 0)
     */
    public abstract void init();

    /**geef het aantal posities in dit Register
     @return het aantal posities in dit Register
     */
    public int getSize() {
        return _size;
    }

    /**zet het aantal posities in dit Register
     @param size het aantal posities in dit Register
     */
    public void setSize(int size) {
        _size = size;
    }

    /**zet de waarde van dit Register
     @param val de te zetten waarde
     */
    public abstract void setValue(long val);

    /**zet een gegeven element op een gegeven waarde
     @param index de index van het te zetten element
     @param val de te zetten waarde
     */
    public abstract void setElement(int index, int val);

    /**geef de waarde van dit register
     *@return de waarde van dit Register
     */
    public abstract long getValue();

    /**geef het element van dit register met de gegeven index
     *@param index de index van het te geven element
     *@return het element van dit register met de gegeven index
     */
    public abstract int getElement(int index);

    /**geef een getal dat bestaat uit de elementen tussen twee
     * gegeven posities
     * @param start de index van het eerste element
     * @param stop de index van het laatste element
     * @return het getal gevormd door alle elementen tussen start
     *  en stop (inclusief)
     */
    public abstract int getElements(int start, int stop);

    /**geef een String voorstelling van dit Register
     *@return een String voorstelling van dit Register
     */
    public abstract String toString();

    /**het aantal elementen van dit register*/
    protected int _size;
}
