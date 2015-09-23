/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/ConstantCodePart.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;

/**
 * ActiveCodePart representing the definition of a number of constants and strings
 *
 * @version 1.0.0, 23/09/2015
 * @author Jo-Thijs Daelman
 */

public class LiteralCodePart extends ActiveCodePart {

	// maximum value in a cell
	public static final long MAX_VALUE = CELL_SIZE - 1;
	// minimum value in a cell
	public static final long MIN_VALUE = - (CELL_SIZE / 2);
	
	private final String _line;
	private final Vertaler2 _vertaler;

	/**
	 * Initialize a new LiteralCodePart for the given line and vertaler.
	 */
	public LiteralCodePart(String line, Vertaler2 vertaler) {
		this._line = line;
		this._vertaler = vertaler;
	}
	
	public int nbObjectLines()
			throws AbnormalTerminationException {
		int result = 1;
		int l = this._line.length();
		int inString = -1; // Amount of characters counted in the string so far (-1 if not in a string)
		for(int i = 0; i < l; i++)
			if (inString >= 0)
				switch (this._line.charAt(i)) {
				case '\\':
					i++;
					if (this._line.charAt(i) == 'x') {
						do
							i++;
						while (LiteralCodePart.isHexadecimal(this._line.charAt(i)));
						i--;
					} else
						for(int j = 0; j < 3; j++)
							if (!LiteralCodePart.isOctal(this._line.charAt(i))) {
								if (j != 0)
									i--;
								break;
							} else
								i++;
				default:
					inString++;
					continue;
				case '"':
					result += inString / 3; // 1 is added at ';'
					inString = -1;
					continue;
				}
			else
				switch (this._line.charAt(i)) {
				case ';':
					result++;
					continue;
				case '"':
					inString = 0;
					continue;
				}
		return result;
	}

	public String[] getObjectLines(int objectln)
			throws AbnormalTerminationException {
		String[] result = new String[this.nbObjectLines()];
		int l = this._line.length();
		int p = 0;
		for(int i = 0; i < result.length; i++) {
			if (p >= l)
				throw new AbnormalTerminationException("fout aantal ;'s");
			char c = this._line.charAt(p);
			IntStringCouple temp;
			if (c == '"') {
				temp = this.getStringObjectAt(p);
				String[] copyable = LiteralCodePart.values(temp.s);
				for(int j = 0; j < copyable.length; j++)
					result[i++] = copyable[j];
				i--;
			} else {
				temp = this.getConstantObjectAt(p);
				result[i] = temp.s;
			}
			if (temp.i < l && this._line.charAt(temp.i) != ';')
				throw new AbnormalTerminationException("fout aantal ;'s");
			p = this.skipWhiteSpaces(temp.i + 1);
		}
		if (p < l)
			throw new AbnormalTerminationException("fout aantal ;'s");
		return result;
	}
	
	private static boolean isHexadecimal(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c < 'G') || (c >= 'a' && c < 'g');
	}
	
	private static boolean isOctal(char c) {
		return c >= '0' && c < '8';
	}
	
	private IntStringCouple getStringObjectAt(int indexFrom)
			throws AbnormalTerminationException {
		String result = "";
		int l = this._line.length();
		while (++indexFrom < l) {
			char c = this._line.charAt(indexFrom);
			switch (c) {
			case '"':
				return new IntStringCouple(this.skipWhiteSpaces(indexFrom + 1), result);
			case '\\':
				switch (this._line.charAt(++indexFrom)) {
				case '\\': result += '\\'; break;
				case 'n': result += '\n'; break;
				case 't': result += '\t'; break;
				case 'v': result += '\13'; break;
				case 'b': result += '\b'; break;
				case '\'': result += '\''; break;
				case '"': result += '"'; break;
				case 'r': result += '\r'; break;
				case 'f': result += '\f'; break;
				case 'a': result += '\7'; break;
				case '?': result += '?'; break;
				case 'x':
				{
					int value = 0;
					int temp;
					while (++indexFrom < l) {
						temp = LiteralCodePart.hexadecimalValue(this._line.charAt(indexFrom));
						if (temp == -1)
							break;
						value = value * 16 + temp;
					}
					result += (char) (value % 256);
					indexFrom--;
					break;
				}
				default:
				{
					if (!LiteralCodePart.isOctal(this._line.charAt(indexFrom)))
						throw new AbnormalTerminationException("onbekend ASCII karakter \\" + c);
					int value = 0;
					for(int i = 0; i < 3; i++) {
						value = value * 8 + (this._line.charAt(indexFrom) - '0');
						if (++indexFrom == l)
							break;
						if (!LiteralCodePart.isOctal(this._line.charAt(indexFrom)))
							break;
					}
					result += (char) (value % 256);
					indexFrom--;
					break;
				}
				}
				break;
			default:
				result += c;
			}
		}
		throw new AbnormalTerminationException("string is niet gesloten");
	}
	
	private static int hexadecimalValue(char c) {
		return c >= '0' && c <= '9' ? (int)(c - '0') : c >= 'A' && c < 'G' ? (int)(c - 'A') + 10 :
				c >= 'a' && c < 'g' ? (int)(c - 'a') + 10 : -1;
	}
	
	private static String[] values(String s) {
		String[] result = new String[(s.length() / 3) + 1];
		
		long l = 0;
		int i, c = 0;
		for(i = 0; s.length() != i; ++i) {
			if (i != 0 && i%3 == 0) {
				result[c++] = Long.toString(l);
				l = 0;
			}
			l += ((long)s.charAt(i)) * Math.pow(1000, i%3);
		}
		result[c] = Long.toString(l);
		
		return result;
	}

	private IntStringCouple getConstantObjectAt(int indexFrom)
			throws AbnormalTerminationException {
		int newIndex = this._line.indexOf(';', indexFrom);
		if (newIndex < 0)
			newIndex = this._line.length();
		return new IntStringCouple(newIndex,
				value(evaluateLong(this._line.substring(indexFrom, newIndex).toUpperCase(), _vertaler)));
	}
	
	/*
	 * @return the value to be stored in the cell
	 */
	private String value(long v)
			throws AbnormalTerminationException {
		if ((v < MIN_VALUE) || (v > MAX_VALUE))
			throw new AbnormalTerminationException("waarde niet binnen " + MIN_VALUE + " en " + MAX_VALUE + " :" + v);
		
		if (v < 0)
			return Long.toString(v + CELL_SIZE);
		
		return StringUtils.prependChars(Long.toString(v), '0', 10);
	}
	
	private int skipWhiteSpaces(int indexFrom) {
		int l = this._line.length();
		while (indexFrom < l)
			if (!Character.isWhitespace(this._line.charAt(indexFrom)))
				break;
			else
				indexFrom++;
		return indexFrom;
	}
	
	private static final class IntStringCouple {
		public final int i;
		public final String s;
		
		public IntStringCouple(int i, String s) {
			this.i = i;
			this.s = s;
		}
	}
}
