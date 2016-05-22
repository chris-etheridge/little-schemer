package com.example.chrisetheridge.littleschemer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chrisetheridge on 5/22/16.
 */
public class ColorFiles {

    public static class Util {

        private static BufferedReader bufr;

        // main method which loads our data from a file PATH
        // also parses the data with a delimiter
        public static List<String> loadData(String path, String deli, Context ctx) {
            File f = new File(path);
            List<String> cs = Arrays.asList("");

            try {
                bufr = new BufferedReader(new FileReader(path));
                String line = bufr.readLine()

                while(line != null) {

                }

            }

            catch (IOException e) {}

            return cs;
        }

        // parses a single line from a data blob
        // requires a delimiter to be provided
        private static String[] parseLine(String line, String deli) {
            return line.split(deli);
        }

    }

}
