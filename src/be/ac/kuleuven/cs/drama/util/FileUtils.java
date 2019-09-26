/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/util/FileUtils.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.util;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class FileUtils {

    public static String readFile(File file)
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append('\n');
        }

        reader.close();

        return builder.toString();
    }

    public static void writeFile(File file, String text)
            throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        writer.print(text);
        writer.close();
    }

    public static File ensureExtension(File file, String extension) {
        if (file.getName().endsWith(".".concat(extension))) {
            return file;
        } else {
            return new File(file.getAbsolutePath().concat(".").concat(extension));
        }

    }

    public static File otherExtension(File file, String newExtension) {
        return new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 3).concat(newExtension));
    }

}
