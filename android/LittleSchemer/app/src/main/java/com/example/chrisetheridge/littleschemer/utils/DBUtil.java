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

// our db 'adapter'
public class DBUtil {
    private String KEY_ROWID = "_id";
    private String KEY_UNAME = "username";
    private String KEY_COLOR = "color_";

    private String DB_NAME = "little_schemer_db";
    private String DB_TABLE_SCHEMES = "color_schemes";
    private String DB_TABLE_USER = "user_schemes";
    private String CHECK_TABLE_EXISTS = "SELECT name FROM sqlite_master WHERE type='table' AND name='color_schemes'";

    private int DB_VERSION = 1;

    final String CREATE_TABLE_SCHEMES = "create table " + DB_TABLE_SCHEMES +
            " (_id integer primary key autoincrement, username text not null," +
            " color_1 text not null, color_2 text not null, color_3 text not null," +
            " color_4 text not null)";

    final String CREATE_TABLE_USER = "create table " + DB_TABLE_USER +
            " (_id integer primary key autoincrement, color_scheme_id integer not null)";

    final Context context;
    private DatabaseHelper _dbhelper;
    SQLiteDatabase db;

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
                db.execSQL(CREATE_TABLE_SCHEMES);
                db.execSQL(CREATE_TABLE_USER);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DB_TABLE_SCHEMES);
            db.execSQL("drop table if exists " + DB_TABLE_USER);

            onCreate(db);
        }
    }

    // essentially 'installs' the db
    public void runSchemerInstall(String path, Context ctx) throws IOException {
        if(!schemerDbExists()) {
            try {
                db.execSQL(CREATE_TABLE_SCHEMES);
                db.execSQL(CREATE_TABLE_USER);

                insertSeedData(path, ",", ctx);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertSeedData(String path, String deli, Context ctx) throws IOException {
        // load seed data
        ArrayList<ColorScheme> cs = ColorsUtil.FileUtil.loadData(path, deli, ctx);

        // build our inserts
        for(ColorScheme c : cs) {
            insertScheme(c);
        }
    }

    private boolean schemerDbExists() {
        Cursor c = db.rawQuery(CHECK_TABLE_EXISTS, null);

        return c != null;
    }

    public long insertScheme(ColorScheme cs) {
        ContentValues initvals = new ContentValues();

        initvals.put(KEY_COLOR + 1, cs.Colors[0]);
        initvals.put(KEY_COLOR + 2, cs.Colors[1]);
        initvals.put(KEY_COLOR + 3, cs.Colors[2]);
        initvals.put(KEY_COLOR + 4, cs.Colors[3]);
        initvals.put(KEY_UNAME, cs.UserName);

        long val = db.insert(DB_TABLE_SCHEMES, null, initvals);

        return val;
    }

    public boolean deleteScheme(long id) {
        boolean val = db.delete(DB_TABLE_SCHEMES, KEY_ROWID + "=" + id, null) > 0;

        return val;
    }

    public ArrayList<ColorScheme> getAllSchemes() {
        ArrayList<ColorScheme> allcs = new ArrayList();

        Cursor c = db.query(DB_TABLE_SCHEMES,
                new String[] {KEY_ROWID, KEY_COLOR + 1, KEY_COLOR + 2,
                        KEY_COLOR + 3, KEY_COLOR + 4, KEY_UNAME}, null, null, null, null, null);

        if(c != null) {
            while(c.moveToNext()) {
                String color_1 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_2 = c.getString(c.getColumnIndex(KEY_COLOR + 2));
                String color_3 = c.getString(c.getColumnIndex(KEY_COLOR + 3));
                String color_4 = c.getString(c.getColumnIndex(KEY_COLOR + 4));
                String name = c.getString(c.getColumnIndex(KEY_UNAME));
                int id = Integer.parseInt(c.getString(c.getColumnIndex(KEY_ROWID)));

                String[] colors = {color_1, color_2, color_3, color_4};

                ColorScheme news = new ColorScheme(id, colors, name, false);

                allcs.add(news);
            }
        }

        return allcs;
    }

    public ArrayList<ColorScheme> getLikedSchemes() {
        ArrayList<ColorScheme> allcs = new ArrayList();

        Cursor c = db.rawQuery("select * " +
            "from " + DB_TABLE_SCHEMES + " sc "
            + DB_TABLE_USER + " u "
            + "where u.color_scheme_id = sc_id", null);

        if(c != null) {
            while(c.moveToNext()) {
                String color_1 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_2 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_3 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String color_4 = c.getString(c.getColumnIndex(KEY_COLOR + 1));
                String name = c.getString(c.getColumnIndex(KEY_UNAME));
                int id = Integer.parseInt(c.getString(c.getColumnIndex(KEY_ROWID)));

                String[] colors = {color_1, color_2, color_3, color_4};

                ColorScheme news = new ColorScheme(id, colors, name, false);

                allcs.add(news);
            }
        }

        return allcs;
    }

    public DBUtil open() throws SQLException {
        db = _dbhelper.getWritableDatabase();

        return this;
    }

    public void close() {
        _dbhelper.close();
    }
}