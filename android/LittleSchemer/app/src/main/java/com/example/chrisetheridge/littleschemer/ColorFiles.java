package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

        // parses a single line from a data blob
        // requires a delimiter to be provided
        private static String[] parseLine(String line, String deli) {
            return line.split(deli);
        }

    }

}
