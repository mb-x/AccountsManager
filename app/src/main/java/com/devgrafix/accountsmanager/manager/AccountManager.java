package com.devgrafix.accountsmanager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devgrafix.accountsmanager.model.Account;
import com.devgrafix.accountsmanager.model.Folder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sbxramses on 27/04/16.
 */
public class AccountManager extends EntityManager {

    /* Begin Constants */

    public static final String TABLE_NAME= "account";

    public static final String ID= "id";

    public static final String FOLDER= "folder_id";

    public static final String NAME= "name";

    public static final String URL= "url";

    public static final String LOGIN= "login";

    public static final String EMAIL= "email";

    public static final String PASSWORD= "password";

    public static final String COMMENT= "comment";

    public static final String IS_IN_HOME= "is_in_home";

    public static final String CREATED_AT= "created_at";

    public static final String UPDATED_AT= "updated_at";

    private String[] allColumns = { ID, NAME, FOLDER, URL, LOGIN, PASSWORD, EMAIL, COMMENT, IS_IN_HOME, CREATED_AT, UPDATED_AT };

    public AccountManager(Context context) {
        super(context);
    }
     /* end Constants */

    public List<Account> findAll() {

        List<Account> accounts = new ArrayList<Account>();
        try{
            this.open();
            Cursor cursor = database.query(TABLE_NAME,
                    allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Account account = cursorToEntity(cursor);
                accounts.add(account);
                cursor.moveToNext();
            }
            // assurez-vous de la fermeture du curseur
            cursor.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.close();
        }

        return accounts;
    }

    public List<Account> findByFolder(long folder_id) {

        List<Account> accounts = new ArrayList<Account>();
        try{
            this.open();
            Cursor cursor = database.query(TABLE_NAME,
                    allColumns, FOLDER+" = " + folder_id, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Account account = cursorToEntity(cursor);
                accounts.add(account);
                cursor.moveToNext();
            }
            // assurez-vous de la fermeture du curseur
            cursor.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.close();
        }

        return accounts;
    }

    /**
     * @param entity le métier à ajouter à la base
     */
    public long add(Account entity){
        //Log.e("Folder Add", entity.getName()+" -- "+entity.getOrder());
        long insertId = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, entity.getName());
            contentValues.put(LOGIN, entity.getLogin());
            contentValues.put(EMAIL, entity.getEmail());
            contentValues.put(URL, entity.getUrl());
            contentValues.put(PASSWORD, entity.getPassword());
            contentValues.put(COMMENT, entity.getDescription());
            contentValues.put(IS_IN_HOME, entity.getIsFavorite());
            contentValues.put(CREATED_AT, dateFormat.format(new Date()));
            contentValues.put(FOLDER, entity.getFolder().getId());
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
    public Account findById(long id) {
        Account account=null;
        try {
            open();
            Cursor cursor =  database.query(TABLE_NAME, allColumns, ID + " = " + id, null,
                    null, null, null);
            cursor.moveToFirst();
            account = cursorToEntity(cursor);
            cursor.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }

        return account;
    }

    private Account cursorToEntity(Cursor c) {
        Account account = new Account();

        account.setName(c.getString(c.getColumnIndex(NAME)));
        account.setLogin(c.getString(c.getColumnIndex(LOGIN)));
        account.setEmail(c.getString(c.getColumnIndex(EMAIL)));
        account.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
        account.setUrl(c.getString(c.getColumnIndex(URL)));
        account.setDescription(c.getString(c.getColumnIndex(COMMENT)));
        account.setIsFavorite((c.getInt(c.getColumnIndex(IS_IN_HOME))==0? false : true));
        return account;
    }

}
