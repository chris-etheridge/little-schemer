package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisetheridge on 5/22/16.
 */
public class ColorsUtil {

    public static class FileUtil {

        private static BufferedReader bufr;

        // main method which loads our data from a file PATH
        // also parses the data with a delimiter
        public static List<String[]> loadData(String path, String deli, Context ctx) throws IOException {
            AssetManager as = ctx.getAssets();
            List<String[]> cs = new ArrayList();

            bufr = new BufferedReader(new InputStreamReader(as.open(path)));
            String line = bufr.readLine();

            while(line != null) {
                // parse the lines
                String[] d = parseLine(line, deli);

                if(d != null && d.length > 0) {
                    // add our set of colors to the list
                    cs.add(d);
                }

                line = bufr.readLine();
            }

            bufr.close();

            return cs;
        }

        // saves a color to the file
        public void saveColor(String[] colors, String username, boolean liked) {

        }

        // parses a single line from a data blob
        // requires a delimiter to be provided
        private static String[] parseLine(String line, String deli) {
            return line.split(deli);
        }

    }

    // generic utilities
    public static class Util {

        // converts an rgb code to a hex code
        public static String rgbToHex(int r, int g, int b) {
            return String.format( "#%02x%02x%02x", r, g, b );
        }
    }

}
