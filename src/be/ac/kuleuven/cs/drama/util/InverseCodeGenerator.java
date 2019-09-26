/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/util/InverseCodeGenerator.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.util;

import java.util.Hashtable;
//import be.ac.kuleuven.cs.drama.exception.*;//ObjectNotFoundException.*;

/**
 Een klasse om te decompileren (dwz van een inwendige machine-
 instructie een uitwendige machine-instructie maken)
 Deze versie ondersteunt dit enkel voor de opcodes

 @version 1.1 05 APR 2015
 @author Tom Vekemans
 @author Jo-Thijs Daelman
 */

public class InverseCodeGenerator {
    private static Hashtable<String, String> _hash = new Hashtable<>();

    /**maak een nieuwe InverseCodeGenerator
     */
    public InverseCodeGenerator() {

        construct();
    }

    /**geef de uitwendige machine-instructie voor een gegeven
     inwendige machine-instructie
     @param code de om te zetten inwendige machine-instructie
     @return de overeenkomstige uitwendige machine-instructie<BR>
     In deze versie wordt enkel met de <B>opcodes</B> gewerkt (bv.
     het resultaat van <I>getSource("11")</I> is "<I>HIA</I>"
     */
    public static String getSource(String code) {
        return (_hash.get(code));
    }

    /**maak de decodeertabel aan*/

    protected void construct() {
        for (int i = 0; i < 10; i++) {
            _hash.put("" + i + " ", " ");
        }

        for (int i = 10; i < 100; i++) {
            _hash.put("" + i, " ");
        }

        _hash.put("11", "HIA");
        _hash.put("12", "BIG");
        _hash.put("21", "OPT");
        _hash.put("22", "AFT");
        _hash.put("23", "VER");
        _hash.put("24", "DEL");
        _hash.put("25", "MOD");
        _hash.put("31", "VGL");
        _hash.put("32", "SPR");
        _hash.put("33", "VSP");
        _hash.put("41", "SBR");
        _hash.put("42", "KTG");
        _hash.put("51", "MKL");
        _hash.put("52", "MKH");
        _hash.put("53", "TSM");
        _hash.put("54", "TSO");
        _hash.put("61", "OND");
        _hash.put("62", "KTO");
        _hash.put("71", "LEZ");
        _hash.put("72", "DRU");
        _hash.put("73", "NWL");
        _hash.put("79", "DRS");
        _hash.put("81", "INV");
        _hash.put("82", "UTV");
        _hash.put("99", "STP");
        _hash.put("91", "HIB");
        _hash.put("92", "SGI");
        _hash.put("93", "SGU");
    }

}






