package com.devgrafix.accountsmanager;

import java.util.Date;

/**
 * Created by sbxramses on 30/04/16.
 */
public class Account {

    /* Begin Variables */
    protected long id;
    protected String name , url, login, email , password, comment;
    protected Boolean is_in_home;
    protected Date created_at, updated_at;
    protected Folder folder;
    /* End Variables */

    public Account(){}
    public Account(String name, String url, String login, String email, String password, String comment, Boolean is_in_home) {
        this.name = name;
        this.url = url;
        this.login = login;
        this.email = email;
        this.password = password;
        this.comment = comment;
        this.is_in_home = is_in_home;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIs_in_home() {
        return is_in_home;
    }

    public void setIs_in_home(Boolean is_in_home) {
        this.is_in_home = is_in_home;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
