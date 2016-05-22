package com.example.chrisetheridge.littleschemer.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.chrisetheridge.littleschemer.model.ColorScheme;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
                ColorScheme c = parseLine(line, deli);

                cs.add(c);

                line = bufr.readLine();
            }

            bufr.close();

            return cs;
        }

        // saves color schemes to the file
        // we assumed that we can drop the old schemes
        public static void saveColorSchemes(ArrayList<ColorScheme> schemes, String path, Context ctx) throws IOException {
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
        public static void setupData(String inpath, String outpath, String deli, Context ctx) throws IOException {
            ArrayList<ColorScheme> cs = loadData(inpath, deli, ctx);

            // save our schemes to the file
            saveColorSchemes(cs, outpath, ctx);
        }

        // write just one scheme to the file
        // this appends to the file
        public static void saveOneSchemeToFile(ColorScheme cs, String path, Context ctx) {
            try {
                FileOutputStream fout = ctx.openFileOutput(path, Context.MODE_APPEND);
                PrintWriter wrtr = new PrintWriter(fout, true);
                String line = cs.getSchemeForWriting();

                wrtr.println(line);

                wrtr.flush();
                wrtr.close();

            } catch (IOException e) {

            }
        }

        // parses a single line from a data blob
        // requires a delimiter to be provided
        public static ColorScheme parseLine(String line, String deli) {
            String[] d = line.split(deli);
            ColorScheme s = new ColorScheme();

            if(d != null && d.length > 0) {
                // we know the first 4 of the array is the scheme
                String[] colors = {d[0], d[1], d[2], d[3]};
                // we know the username is the 5th item
                String name = d[4];
                // we know the like status is the last item
                Boolean liked = Boolean.parseBoolean(d[5]);

                s = new ColorScheme(colors, name, liked);
            }

            return s;
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
