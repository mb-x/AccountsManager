package com.devgrafix.accountsmanager.model;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by sbxramses on 17/04/16.
 */
@Table(name = "tbl_folder")
public class Folder extends Model {

    @Column(name = "folder_name")
    protected String name;
    @Column(name = "folder_order")
    protected int order = 0;
    @Column(name = "created_at")
    protected Date created_at;

    public Folder(){

    }

    public Folder(String name, int order) {
        this.name = name;
        this.order = order;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String toString() {
        return name;
    }

    /** *****************************
     * SQLITE METHODES
     ********************************** */
    public static List<Folder> getAll(){
        return new Select()
                .from(Folder.class)
                .orderBy("folder_order ASC")
                .execute();
    }
    public static Folder getOneById(long id){
        return new Select()
                .from(Folder.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}
