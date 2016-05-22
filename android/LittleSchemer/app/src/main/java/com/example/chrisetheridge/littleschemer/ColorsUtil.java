package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        public static ArrayList<ColorScheme> loadData(String path, String deli, Context ctx) throws IOException {
            AssetManager as = ctx.getAssets();
            ArrayList<ColorScheme> cs = new ArrayList();

            bufr = new BufferedReader(new InputStreamReader(as.open(path)));
            String line = bufr.readLine();

            while(line != null) {
                // parse the lines
                String[] d = parseLine(line, deli);

                if(d != null && d.length > 0) {
                    // we know the first 4 of the array is the scheme
                    String[] colors = {d[0], d[1], d[2], d[3]};
                    // we know the username is the 5th item
                    String name = d[4];
                    // we know the like status is the last item
                    Boolean liked = Boolean.parseBoolean(d[5]);

                    ColorScheme s = new ColorScheme(colors, name, liked);

                    cs.add(s);
                }

                line = bufr.readLine();
            }

            bufr.close();

            return cs;
        }

        // saves a color to the file
        public void saveColor(ArrayList<ColorScheme> schemes, String path, Context ctx) throws IOException {
            try {
                FileOutputStream fout = ctx.openFileOutput(path, Context.MODE_PRIVATE);
                PrintWriter wrtr = new PrintWriter(fout, true);

                for(ColorScheme sc : schemes) {
                    String line = sc.getSchemeForWriting();

                    wrtr.println(line);
                }

                wrtr.flush();
                wrtr.close();

            } catch (IOException e) {

            }
        }

        // sets up the data
        // saves our seed colors to the phone
        public void setupData(String path, String deli, Context ctx) {

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
