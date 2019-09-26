/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/devices/CVO/DramaPTW.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.devices.CVO;

//import be.ac.kuleuven.cs.drama.simulator.basis.*;

/** Klasse voor bewerkingen op DramaPTWs. Dit zijn
 * programmatoestandswoorden voor DRAMA machines.
 *
 * @version 1.1.0 17-nov-2015
 * @author Tom Vekemans
 * @author Jo-Thijs Daelman
 */

public class DramaPTW extends PTW {

    /**instantieer een nieuw DramaPTW
     */
    public DramaPTW() {
        _size = 25;
        _element = new int[_size];
        _interruptFlag = new boolean[9];
        init();
    }

    /**initialiseer dit DramaPTW. PTW[0..9] worden op 0 gezet,
     * en PTW[10..19] (de vlaggen) worden op 1 gezet.
     */
    public void init() {
        setValue(0);
        setMaskerValue(1111111111L);
        for (int i = 1; i <= 9; i++)
            setInterruptFlag(i, false);
    }

    /**geef de waarde van de bevelenteller
     *@return de waarde van de bevelenteller (PTW[6..9])
     */
    public /*synchronized*/ final long getBT() {
        long l = 0;

        for (int i = 6; i < 10; i++) {
            l = 10 * l + getElement(i);
        }

        return l;
    }

    /**geef de waarde van de conditiecode
     *@return de waarde van de conditiecode
     */
    public int getCC() {
        return _element[3];
    }

    /**geef het element met de gegeven index
     *@param index de index van het gewenste element
     *@return het element met de gegeven index (= PTW[index])
     */
    public int getElement(int index) {
        return _element[index];
    }

    /**geef een getal bestaande uit opeenvolgende elementen van het PTW,
     gelegen tussen twee gegeven indexen
     @param start de index van het eerste cijfer
     @param stop de index van het laatste cijfer
     @return een getal bestaande uit de cijfers gelegen tussen PTW[start]
     en PTW[stop] (inclusief)
     */
    public /*synchronized*/ int getElements(int start, int stop) {
        int ans = 0;

        for (int i = start; i <= stop; i++) {
            try {
                ans = (ans * 10) + getElement(i);
            } catch (IndexOutOfBoundsException iobe) {
                break;
                // small optimization
            }
        }

        return ans;
    }

    /**zet de bevelenteller op de gegeven waarde (dit genereert een event)
     @param value de nieuwe waarde van de bevelenteller
     */

    public synchronized void setBT(long value) {
        for (int i = 9; i >= 6; i--) {
            privSetElement(i, (int) (value % 10));
            value = value / 10;
        }

        notifyListeners();
    }

    /**zet de conditiecode op de gegeven waarde (dit genereert een event)
     @param value de nieuwe waarde van de conditiecode
     */
    public void setCC(int value) {
        privSetElement(3, value);
        notifyListeners();
    }

    /**zet het element met de gegeven index op de gegeven waarde (dit
     genereert een event)
     @param index de index van het te zetten element
     @param val de nieuwe waarde van het element
     */
    public void setElement(int index, int val) {
        _element[index] = val;
        notifyListeners();
        //Debug if(index>=10) System.out.println(this.toString());
    }

    //zet element PTW[index] op waarde val


    private final void privSetElement(int index, int val) {
        _element[index] = val;
        //Debug if(index>=10) System.out.println(this.toString());
    }

    /**geef een String voorstelling van dit DramaPTW
     @return een String voorstelling van dit DramaPTW
     */
    public /*synchronized*/ String toString() {
        StringBuffer s = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            s.append(getElement(i));
        }

        s.append("\n");

        for (int i = 10; i < 20; i++) {
            s.append(getElement(i));
        }

        return s.toString();
    }

    /**zet de waarde van de eerste 10 elementen van dit PTW (dit
     genereert een event)
     @param value de nieuwe waarde van het eerste deel van het PTW (=PTW[0..9])
     */
    public synchronized void setValue(long value) {
        for (int i = 9; i >= 0; i--) {
            privSetElement(i, (int) (value % 10));
            value /= 10;
        }

        notifyListeners();
    }

    /**zet de waarde van de laatste 10 elementen van het PTW (dit
     genereert een event)
     @param value de nieuwe waarde van het tweede deel van het PTW (=PTW[10..19])
     */
    public synchronized void setMaskerValue(long value) {
        for (int i = 19; i >= 10; i--) {
            privSetElement(i, (int) (value % 10));
            value /= 10;
        }

        notifyListeners();
    }

    /**geef de waarde van het eerste deel van het PTW
     *@return de waarde van het eerste deel van het PTW (=PTW[0..9])
     */
    public synchronized long getValue() {
        long value = 0;

        for (int i = 0; i < 10; i++) {
            value = 10 * value + getElement(i);
        }

        return value;
    }

    private volatile int _element[];
    private volatile boolean _interruptFlag[];
    /**index van de ONV indicator (onderbrekingsniveau)*/
    public final static int ONV = 0;
    /**index van de H/U indicator (halt/uitvoeringstoestand)*/
    public final static int H_U = 1;
    /**index van de S/P indicator (supervisie/probleemtoestand)*/
    public final static int S_P = 2;
    /**index van de conditiecode*/
    public final static int CC = 3;
    /**index van de overloopindicator*/
    public final static int OVI = 4;
    /**index van de stapeloverloop indicator*/
    public final static int SOI = 5;
    /**index van de G-vlag (geen PO's)*/
    public final static int G = 10;
    /**index van de GPF-vlag (geen programmafouten)*/
    public final static int GPF = 11;
    /**index van de WEK vlag*/
    public final static int WEK = 12;
    /**index van de DRK vlag*/
    public final static int DRK = 13;
    /**index van de IN vlag*/
    public final static int IN = 14;
    /**index van de UIT vlag*/
    public final static int UIT = 15;
    /**index van de SCH vlag*/
    public final static int SCH = 16;
    /**index van de OVL (overloop) vlag*/
    public final static int OVL = 17;
    /**index van de SPL (stapeloverloop) vlag*/
    public final static int SPL = 18;
    /**index van de MFT (machinefout) vlag*/
    public final static int MFT = 19;

    public long getGBE() {
        long l = 0;
        for (int i = 20; i < 25; ++i)
            l = l * 10 + _element[i];
        return l;
    }

    public void setGBE(long value) {
        for (int i = 24; i >= 20; --i) {
            _element[i] = ((int) value) % 10;
            value /= 10;
        }
        notifyListeners();
    }

    public boolean getOVI() {
        return _element[OVI] != 0;
    }

    public void setOVI(boolean value) {
        _element[OVI] = value ? 1 : 0;
        if (_element[OVI] != 0 && _element[GPF] == 0)
                setInterruptFlag(OVL - 10, true);
        notifyListeners();
    }

    public boolean getSOI() {
        return _element[SOI] != 0;
    }

    public void setSOI(boolean value) {
        _element[SOI] = value ? 1 : 0;
        if (_element[SOI] != 0 && _element[GPF] == 0)
                setInterruptFlag(SPL - 10, true);
        notifyListeners();
    }

    public boolean getInterruptFlag(int flag) {
        return _interruptFlag[flag - 1];
    }

    public void setInterruptFlag(int flag, boolean value) {
        _interruptFlag[flag - 1] = value;
    }

    public boolean getSupervisionState() {
        return getElement(S_P) != 0;
    }
}
