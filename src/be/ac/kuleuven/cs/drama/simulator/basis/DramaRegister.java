/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/basis/DramaRegister.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.basis;

/** Klasse voor bewerkingen op DRAMA Registers
 *
 * @version 1.1.2 13-jan-1999
 * @author Tom Vekemans
 */

public class DramaRegister extends Register {

    /**Initialiseer een nieuw DramaRegister
     */
    public DramaRegister() {
        _size = 10;
        _element = new int[_size];
        init();
    }

    /**Zet de waarde van dit DramaRegister op 0
     */
    public void init() {
        setValue(0);
    }

    /**Geef de waarde van dit DramaRegister
     *
     * @return de waarde van dit DramaRegister
     */
    public final /*synchronized*/ long getValue() {
        long value = 0;

        for (int i = 0; i < _size; i++) {
            value = 10 * value + getElement(i);
        }

        return value;
    }

    /**Zet de waarde van dit DramaRegister op de gegeven waarde
     *
     * @param value de nieuwe waarde van dit DramaRegister
     */

    public synchronized void setValue(long value) {
        for (int i = _size - 1; i >= 0; i--) {
            setElement(i, (int) (value % 10));
            value /= 10;
        }

    }

    /**Geef de waarde van het element met gegeven index
     *
     * @param index de index van het element waarvan de waarde gevraagd wordt
     * @return de waarde van het element met gegeven index
     */
    public final int getElement(int index) {
        return _element[index];
    }

    /**Geef een getal bestaande uit de elementen met index tussen de twee gegeven waarden
     *
     * @param start de index van het eerste element van de selectie
     * @param stop de index van het tweede element van de selectie
     * @return een getal bestaande uit de elementen met index tussen start en stop (inclusief)
     */
    public /*synchronized*/ int getElements(int start, int stop) {
        int ans = 0;

        for (int i = start; i <= stop; i++) {
            try {
                ans = (ans * 10) + getElement(i);
            } catch (IndexOutOfBoundsException iobe) {
            }


        }

        return ans;
    }

    /**Zet de waarde van het element met gegeven index op de gegeven waarde
     *
     * @param index de index van het te zetten getal
     * @param val de nieuwe waarde van het element met de gegeven index
     */
    public /*synchronized*/ void setElement(int index, int val) {
        _element[index] = val % 10;
    }

    /**Geef een String representatie van dit DramaRegister
     *
     * @return een String representatie van dit DramaRegister
     */
    public /*synchronized*/ String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < _size; i++) {
            builder.append(getElement(i));
        }

        return builder.toString();
    }

    private volatile int[] _element;
    /**de maximale waarde van een DramaRegister
     */
    public final static long MAXVAL = 9999999999L;

    /**de minimale waarde van een DramaRegister
     */
    public final static long MINVAL = 0L;
}
