package com.example.chrisetheridge.littleschemer.model;

/**
 * Created by chrisetheridge on 5/22/16.
 */
public class ColorScheme {
    public ColorScheme(int id, String[] colors, String name, Boolean liked) {
        Colors = colors;
        UserName = name;
        Liked = liked;
        _id = id;
    }

    public ColorScheme(String[] colors, String name, Boolean liked) {
        Colors = colors;
        UserName = name;
        Liked = liked;
    }

    public ColorScheme() {}

    public String UserName;

    public String[] Colors;

    public boolean Liked;

    public int _id;

    // preps the scheme for writing to file
    public String getSchemeForWriting() {
        return Colors[0] + ","
                + Colors[1] + ","
                + Colors[2] + ","
                + Colors[3] + ","
                + UserName + ","
                + Liked;
    }
}
