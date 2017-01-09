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

    @Column(name = "folder_description")
    protected String description;

    @Column(name = "folder_icon")
    protected String icon;

    @Column(name = "foreground_colour")
    protected String foregroundColour;

    @Column(name = "background_colour")
    protected String backgroundColour;

    @Column(name = "folder_order")
    protected int order = 0;

    @Column(name = "created_at")
    protected Date created_at;

    @Column(name = "updated_at")
    protected Date updated_at;

    public Folder(){

    }

    public Folder(String name, int order) {
        this.name = name;
        this.order = order;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getForegroundColour() {
        return foregroundColour;
    }

    public void setForegroundColour(String foregroundColour) {
        this.foregroundColour = foregroundColour;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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
