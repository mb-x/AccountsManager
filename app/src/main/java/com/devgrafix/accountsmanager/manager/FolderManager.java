package com.devgrafix.accountsmanager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devgrafix.accountsmanager.model.Folder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sbxramses on 17/04/16.
 */
public class FolderManager extends EntityManager {

    public static final String TABLE_NAME= "folder";

    public static final String ID= "id";

    public static final String NAME= "name";

    public static final String RANK= "rank";

    public static final String ICON= "icon";



    private String[] allColumns = { ID, NAME, RANK };

    public FolderManager(Context context) {
        super(context);
    }


    public List<Folder> findAll() {

        List<Folder> folders = new ArrayList<Folder>();
        try{
            this.open();
            Cursor cursor = database.query(TABLE_NAME,
                    allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Folder folder = cursorToEntity(cursor);
                folders.add(folder);
                cursor.moveToNext();
            }
            // assurez-vous de la fermeture du curseur
            cursor.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.close();
        }

        return folders;
    }

    /**
     * @param entity le métier à ajouter à la base
     */
    public long add(Folder entity){
        //Log.e("Folder Add", entity.getName()+" -- "+entity.getOrder());
        long insertId = 0;
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, entity.getName());
            contentValues.put(RANK, entity.getOrder());
            insertId = database.insert(TABLE_NAME, null, contentValues);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }

        return insertId;
    }

    public void delete(Folder folder) {
        long id = folder.getId();
        try {
            open();
            database.delete(TABLE_NAME, ID + " = " + id, null);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }

    }
    /**
     * @param entity le métier modifié
     */
    public void edit(Folder entity) {
    }
    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Folder findById(long id) {
        Folder folder=null;
        try {
            open();
            Cursor cursor =  database.query(TABLE_NAME, allColumns, ID + " = " + id, null,
                    null, null, null);
            cursor.moveToFirst();
            folder = cursorToEntity(cursor);
            cursor.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }

        return folder;
    }

    private Folder cursorToEntity(Cursor cursor) {
        Folder folder = new Folder();
        folder.setId(cursor.getLong(0));
        folder.setName(cursor.getString(1));
        folder.setOrder(cursor.getInt(2));
        return folder;
    }


}

