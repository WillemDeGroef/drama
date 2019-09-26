/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/vertaler/StringUtils.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.vertalerpack.vertaler;

public final class StringUtils {

    private StringUtils() {
    }


    /**
     * @return the string equal to the given string except for leading spaces
     */
    public static String trimLeftSpaces(String string) {
        char[] chars = string.toCharArray();
        int index = 0;

        while ((index < chars.length) && isSpace(chars[index])) {
            index++;
        }

        return string.substring(index);
    }

    public static String trimRightSpaces(String string) {
        if (string.length() == 0) return "";
        char[] chars = string.toCharArray();
        int index = chars.length - 1;

        while ((index >= 0) && (isSpace(chars[index]))) {
            index--;
        }

        return string.substring(0, index + 1);
    }

    public static String trimSpaces(String string) {
        return trimRightSpaces(trimLeftSpaces(string));
    }

    /**
     * @return a string equal to the given string except for any spaces
     */
    public static String stripSpaces(String line) {
        StringBuilder builder = new StringBuilder();
        char[] chars = line.toCharArray();

        for (char let : chars) {
            if (!isSpace(let)) {
                builder.append(let);
            }

        }

        return builder.toString();
    }


    /**
     * @return wether the given char is considered a space
     */
    public static boolean isSpace(char c) {
        //      return c == ' ' || c == '\t';
        return java.lang.Character.isWhitespace(c);
    }

    /**
     * @return the number of occurences of the given char in the given string
     */
    public static int occurences(String s, char c) {
        char[] chars = s.toCharArray();
        int count = 0;

        for (char aChar : chars) {
            if (aChar == c) {
                count++;
            }

        }

        return count;
    }

    public static String prependChars(String s, char c, int len) {
        int count = len - s.length();
        StringBuilder builder = new StringBuilder();

        while (count > 0) {
            builder.append(c);
            count--;
        }

        builder.append(s);
        return builder.toString();
    }

    // TEST

    public static void main(String[] args) {
        String test = "  \tspacespacetab";
        System.out.println(trimLeftSpaces(test));
    }

}
