package com.example.chrisetheridge.littleschemer;

/**
 * Created by chrisetheridge on 5/22/16.
 */
public class ColorScheme {
    public ColorScheme(String[] colors, String name, Boolean liked) {
        Colors = colors;
        UserName = name;
        Liked = liked;
    }

    public ColorScheme() {}

    public String UserName;

    public String[] Colors;

    public boolean Liked;

    // preps the scheme for writing to file
    public String getSchemeForWriting() {
        return Colors[0] + ","
                + Colors[1] + ","
                + Colors[2] + ","
                + Colors[3] + ","
                + UserName + ","
                + Liked;
    }

    public String getUserName() {
        return UserName;
    }

    public String[] getColors() {
        return Colors;
    }

    public void setUserName(String name) {
        UserName = name;
    }

    public void setColors(String[] colors) {
        Colors = colors;
    }
}
