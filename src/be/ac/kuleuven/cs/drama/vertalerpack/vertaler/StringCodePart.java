/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/StringCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2014
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;


/**
 * ActiveCodePart representing the definition of a number of constants
 *
 * @version 1.0.0, 01/10/2014
 * @author Jo-Thijs Daelman
 */

public class StringCodePart extends ActiveCodePart {

	static String convertBackslashes(String s){
		String r = "";
		char c;
		for(int i = 0; i < s.length(); ++i)
			if ((c = s.charAt(i)) == '\\')
				switch (c = s.charAt(++i)) {
				case '\\': r += c; break;
				case 'n': r += '\n'; break;
				case 't': r += '\t'; break;
				case 'v': r += '\013'; break;
				case 'b': r += '\b'; break;
				case '\'': r += c; break;
				case '"': r += c; break;
				case 'r': r += '\r'; break;
				case 'f': r += '\f'; break;
				case 'a': r += '\007'; break;
				case '?': r += c; break;
				case 'x': r += hexToChar(s.substring(i+1, i+4)); i += 3; break;
				case '0': if (!isDigit(s.charAt(i+1))) { r += '\0'; break; }
				default: r += octToChar(s.substring(i, i+3)); i += 2; break;
				}
			else
				r += c;
		return r;
	}

	private static boolean isDigit(char c) {
		return c <= '9' && c >= '0';
	}

	private static char hexToChar(String hex){
		hex = hex.toUpperCase();
		char c = '\0';
		for(int i = 0; i < hex.length(); ++i)
			c = (char)(c * 16 + "0123456789ABCDEF".indexOf(hex.charAt(i)));
		return c;
	}
	
	private static char octToChar(String oct) {
		char c = '\0';
		for(int i = 0; i < oct.length(); ++i)
			c = (char)(c * 8 + oct.charAt(i) - '0');
		return c;
	}

   // the line
   private final String _line;
   
   /**
    * Initialize a new ConstantCodePart for the given line and vertaler.
    */
   public StringCodePart(String line, Vertaler2 vertaler) {

      _line = line;

   }

   public int nbObjectLines() {
	  return (_line.length()+1)/3;
   }

   public String[] getObjectLines(int objectln)
   throws AbnormalTerminationException {

      String[] result = new String[nbObjectLines()];

      long l = 0;
      int i, c = 0;
      for(i = 1; _line.length() != i; ++i) {
    	  if (i != 1 && i%3 == 1) {
    		  result[c++] = Long.toString(l);
    		  l = 0;
    	  }
    	  if (i+1 != _line.length())
    		  l += ((long)_line.charAt(i)) * Math.pow(1000, (i-1)%3);
      }
      result[c] = Long.toString(l);

      return result;

   }

}
