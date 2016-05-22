package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chrisetheridge on 5/22/16.
 */
// our db 'adapter'
public class DBUtil {
    private String KEY_ROWID = "_id";
    private String KEY_UNAME = "username";
    private String KEY_COLOR = "color_";

    private String DB_NAME = "little_schemer_db";
    private String DB_TABLE_SCHEMES = "color_schemes";
    private String DB_TABLE_USER = "user_schemes";
    private int DB_VERSION = 1;

    final String CREATE_TABLE_SCHEMES = "create table " + DB_TABLE_SCHEMES +
            "(_id integer primary key autoincrement, username text not null," +
            " color_1 text not null, color_2 text not null, color_3 text not null," +
            " color_4 text not null";

    final String CREATE_TABLE_USER = "create table " + DB_TABLE_USER +
            "(_id integer primary key autoincrement, color_scheme_id integer not null)";

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
                db.execSQL(DB_TABLE_SCHEMES);
                db.execSQL(DB_TABLE_USER);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("CHRIS", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("drop table if exists " + DB_TABLE_SCHEMES);
            db.execSQL("drop table if exists " + DB_TABLE_USER);

            onCreate(db);
        }
    }

    public long insertScheme(ColorScheme cs) {}

    public boolean deleteScheme(long id) {}

    public ArrayList<ColorScheme> getAllSchemes() {}

    public ArrayList<ColorScheme> getLikedSchemes() {}

    public DBUtil open() throws SQLException {
        db = _dbhelper.getWritableDatabase();

        return this;
    }

    public void close() {
        _dbhelper.close();
    }
}