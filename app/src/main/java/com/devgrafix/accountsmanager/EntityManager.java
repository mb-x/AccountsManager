package com.devgrafix.accountsmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by sbxramses on 24/04/16.
 */
abstract class EntityManager {

    protected SQLiteDatabase database;
    protected DatabaseManager dbManager;

    public static final String CREATED_AT= "created_at";

    public static final String UPDATED_AT= "updated_at";

    public EntityManager(Context context){
        dbManager = new DatabaseManager(context);
    }

    public void open() throws SQLException {
        database = dbManager.getWritableDatabase();
    }

    public void close() {
        dbManager.close();
    }
}
