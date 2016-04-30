package com.devgrafix.accountsmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sbxramses on 24/04/16.
 */
public class DatabaseManager extends SQLiteOpenHelper{



    protected final static int VERSION = 3;
    protected final static String DATABASE_NAME = "database.db";


    public static final String TABLE_FOLDER_CREATE= "CREATE TABLE IF NOT EXISTS "+ FolderManager.TABLE_NAME+" (" +
            "  "+ FolderManager.ID+" integer primary key autoincrement," +
            "  "+ FolderManager.NAME+" text not null," +
            "  "+ FolderManager.RANK+" integer NULL DEFAULT 0, " +
            "  "+ FolderManager.ICON+" text NULL, " +
            "  "+ FolderManager.CREATED_AT+" text NULL, " +
            "  "+ FolderManager.UPDATED_AT+" text NULL " +
            ");";

    private static final String TABLE_ACCOUNT_CREATE = "CREATE TABLE IF NOT EXISTS "
            + AccountManager.TABLE_NAME + " ("
            + AccountManager.ID + " integer primary key autoincrement, "
            + AccountManager.NAME + " text not null, "
            + AccountManager.URL + " text not null, "
            + AccountManager.LOGIN + " text not null,"
            + AccountManager.PASSWORD + " text ,"
            + AccountManager.COMMENT + " text ,"
            + AccountManager.IS_IN_HOME + " integer ,"
            + AccountManager.EMAIL + " text ,"
            + AccountManager.CREATED_AT + " text ,"
            + AccountManager.UPDATED_AT + " text ,"
            + AccountManager.FOLDER + " integer ,"
            + " FOREIGN KEY ("+AccountManager.FOLDER+") REFERENCES "+ FolderManager.TABLE_NAME+"("+ FolderManager.ID+"));";

    public static final String TABLE_FOLDER_DROP =  "DROP TABLE IF EXISTS " + FolderManager.TABLE_NAME + ";";
    public static final String TABLE_ACCOUNT_DROP =  "DROP TABLE IF EXISTS " +AccountManager.TABLE_NAME + ";";

        public DatabaseManager(Context context) {
            super(context, DATABASE_NAME,null, VERSION);
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_FOLDER_CREATE);
            db.execSQL(TABLE_ACCOUNT_CREATE);
        }

        /**
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_FOLDER_DROP);
            db.execSQL(TABLE_ACCOUNT_DROP);
            onCreate(db);
        }
    }


