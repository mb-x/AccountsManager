package com.devgrafix.accountsmanager;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sbxramses on 17/04/16.
 */
public class Folder {

    protected long id;

    protected String name;

    protected int order;




    public Folder(){

    }

    public Folder(int id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String toString() {
        return name;
    }
}
