package com.example.chrisetheridge.littleschemer.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chrisetheridge.littleschemer.model.ColorScheme;

import java.io.IOException;
import java.util.ArrayList;

// our db 'adapter' utility class
public class DBUtil {
    // key field name constants
    private String KEY_ROWID = "_id";
    private String KEY_UNAME = "username";
    private String KEY_COLOR = "color_";

    // db table constants
    private String DB_NAME = "little_schemer_db";
    private String DB_TABLE_SCHEMES = "color_schemes";
    private String DB_TABLE_USER = "user_schemes";
    private String CHECK_TABLE_EXISTS = "SELECT name FROM sqlite_master WHERE type='table' AND name='color_schemes'";

    // our db version
    private int DB_VERSION = 1;

    // query to create scheme table
    final String CREATE_TABLE_SCHEMES = "create table " + DB_TABLE_SCHEMES +
            " (_id integer primary key autoincrement, username text not null," +
            " color_1 text not null, color_2 text not null, color_3 text not null," +
            " color_4 text not null)";

    // query to create user table
    final String CREATE_TABLE_USER = "create table " + DB_TABLE_USER +
            " (_id integer primary key autoincrement, color_scheme_id integer not null)";

    // context, database helper, and db variables
    final Context context;
    private DatabaseHelper _dbhelper;
    SQLiteDatabase db;

    // constructor that takes the current context
    public DBUtil(Context ctx)
    {
        this.context = ctx;
        _dbhelper = new DatabaseHelper(context);
    }

    // class for helping with the db
    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                // create our tables
                db.execSQL(CREATE_TABLE_SCHEMES);
                db.execSQL(CREATE_TABLE_USER);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        // drop & create our tables
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DB_TABLE_SCHEMES);
            db.execSQL("drop table if exists " + DB_TABLE_USER);

            onCreate(db);
        }
    }

    // essentially 'installs' the db
    // creates the db abd loads the seed data
    public void runSchemerInstall(String path, Context ctx) throws IOException {
        if(!schemerDbExists()) {
            try {
                db.execSQL(CREATE_TABLE_SCHEMES);
                db.execSQL(CREATE_TABLE_USER);

                // insert our seed data from the path
                insertSeedData(path, ",", ctx);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // loads the seed data into the database
    private void insertSeedData(String path, String deli, Context ctx) throws IOException {
        // load seed data from seed file
        ArrayList<ColorScheme> cs = ColorsUtil.FileUtil.loadData(path, deli, ctx);

        // build our inserts
        for(ColorScheme c : cs) {
            // insert the scheme
            insertScheme(c);
        }
    }

    // check if the db exists
    // assumes that if one table exists (schemes), then the db exists
    private boolean schemerDbExists() {
        Cursor c = db.rawQuery(CHECK_TABLE_EXISTS, null);

        return c != null;
    }

    // inserts one scheme into the db
    public long insertScheme(ColorScheme cs) {
        ContentValues initvals = new ContentValues();

        // put our values into the db
        initvals.put(KEY_COLOR + 1, cs.Colors[0]);
        initvals.put(KEY_COLOR + 2, cs.Colors[1]);
        initvals.put(KEY_COLOR + 3, cs.Colors[2]);
        initvals.put(KEY_COLOR + 4, cs.Colors[3]);
        initvals.put(KEY_UNAME, cs.UserName);

        // insert the values
        long val = db.insert(DB_TABLE_SCHEMES, null, initvals);

        return val;
    }

    // deletes a scheme by id
    public boolean deleteScheme(long id) {
        boolean val = db.delete(DB_TABLE_SCHEMES, KEY_ROWID + "=" + id, null) > 0;

        return val;
    }

    // gets all the schemes in the db
    public ArrayList<ColorScheme> getAllSchemes() {
        ArrayList<ColorScheme> allcs = new ArrayList();

        // query the db and set to cursor
        Cursor c = db.query(DB_TABLE_SCHEMES,
                new String[] {KEY_ROWID, KEY_COLOR + 1, KEY_COLOR + 2,
                        KEY_COLOR + 3, KEY_COLOR + 4, KEY_UNAME}, null, null, null, null, null);

        // makes sure the cursor has rows
        if(c != null) {
            // loop through the cursor rows
            while(c.moveToNext()) {
                // get our scheme values from the row
                String color_1 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_2 = c.getString(c.getColumnIndex(KEY_COLOR + 2));
                String color_3 = c.getString(c.getColumnIndex(KEY_COLOR + 3));
                String color_4 = c.getString(c.getColumnIndex(KEY_COLOR + 4));
                String name = c.getString(c.getColumnIndex(KEY_UNAME));
                int id = Integer.parseInt(c.getString(c.getColumnIndex(KEY_ROWID)));

                String[] colors = {color_1, color_2, color_3, color_4};

                // create a new scheme from the values
                ColorScheme news = new ColorScheme(id, colors, name, false);

                // add the new scheme to the list
                allcs.add(news);
            }
        }

        // return the list
        return allcs;
    }

    // get schemes liked by the user
    // liked schemes are in the user_schemes table
    public ArrayList<ColorScheme> getLikedSchemes() {
        ArrayList<ColorScheme> allcs = new ArrayList();

        // run a raw query to get the liked schemes
        Cursor c = db.rawQuery("select * " +
            "from " + DB_TABLE_SCHEMES + " sc "
            + DB_TABLE_USER + " u "
            + "where u.color_scheme_id = sc_id", null);

        // make sure we have rows in our cursor
        if(c != null) {
            // loop through the cursor rows
            while(c.moveToNext()) {
                // get our values from the row
                String color_1 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_2 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_3 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_4 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String name = c.getString(c.getColumnIndex(KEY_UNAME));
                int id = Integer.parseInt(c.getString(c.getColumnIndex(KEY_ROWID)));

                String[] colors = {color_1, color_2, color_3, color_4};

                // create a new scheme from the row
                ColorScheme news = new ColorScheme(id, colors, name, false);

                // add the new scheme to the list
                allcs.add(news);
            }
        }

        // return the list
        return allcs;
    }

    // db open helper
    public DBUtil open() throws SQLException {
        db = _dbhelper.getWritableDatabase();

        return this;
    }

    // db close helper
    public void close() {
        _dbhelper.close();
    }
}