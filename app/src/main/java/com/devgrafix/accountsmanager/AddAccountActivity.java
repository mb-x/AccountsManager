package com.devgrafix.accountsmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class AddAccountActivity extends AppCompatActivity {

    Spinner spinnerFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerFolder = (Spinner)findViewById(R.id.spinner_folder);
        loadSpinnerFolderData();

    }

    protected void loadSpinnerFolderData(){
        FolderManager folderManager = new FolderManager(getApplicationContext());
        List<Folder> folderList = folderManager.findAll();
        ArrayAdapter<Folder> folderArrayAdapter =  new ArrayAdapter<Folder>(this,
                android.R.layout.simple_spinner_item, folderList);
        spinnerFolder.setAdapter(folderArrayAdapter);
    }

}
