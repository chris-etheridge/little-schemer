package com.example.chrisetheridge.littleschemer.model;

// color scheme model class
public class ColorScheme {

    // constructor that takes an id (v2)
    public ColorScheme(int id, String[] colors, String name, Boolean liked) {
        Colors = colors;
        UserName = name;
        Liked = liked;
        _id = id;
    }

    // constructor that does not take an id (v1)
    public ColorScheme(String[] colors, String name, Boolean liked) {
        Colors = colors;
        UserName = name;
        Liked = liked;
    }

    // default constructor
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
