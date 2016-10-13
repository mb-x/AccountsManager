package com.devgrafix.accountsmanager.activity;

import com.devgrafix.accountsmanager.model.Account;
import com.devgrafix.accountsmanager.manager.AccountManager;
import com.devgrafix.accountsmanager.model.Folder;
import com.devgrafix.accountsmanager.manager.FolderManager;
import com.devgrafix.accountsmanager.R;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddAccountActivity extends AppCompatActivity
            implements AdapterView.OnItemSelectedListener {

    final Context contextAddAccount = this;


    protected Folder selectedFolder;
    protected Spinner spinnerFolder;
    protected CheckBox chkInHome;

    protected  EditText fldAccountName,
             fldLogin, fldEmail, fldPassword, fldRepeatPassword, fldUrl, fldComment
                        ;
    protected Button btnSubmit, btnAddFolder ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        loadSpinnerFolderData();

    }

    protected void initViews(){
        /* Spinners */
        spinnerFolder = (Spinner)findViewById(R.id.spinner_folder);
        spinnerFolder.setOnItemSelectedListener(this);
        /* EditText */
        fldAccountName = (EditText)findViewById(R.id.fld_account_name);
        fldLogin = (EditText)findViewById(R.id.fld_login);
        fldEmail = (EditText)findViewById(R.id.fld_email);
        fldPassword = (EditText)findViewById(R.id.fld_password);
        fldRepeatPassword = (EditText)findViewById(R.id.fld_repeat_password);
        fldUrl = (EditText)findViewById(R.id.fld_url);
        fldComment = (EditText)findViewById(R.id.fld_comment);

        /* Checkboxs */
        chkInHome = (CheckBox)findViewById(R.id.chk_in_home);

        /* Buttons */
        btnSubmit = (Button)findViewById(R.id.btn_submit_add_entry);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        btnAddFolder = (Button)findViewById(R.id.btn_add_folder);
        btnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptCreateFolder();
            }
        });
    }

    protected void promptCreateFolder(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(contextAddAccount);
        View promptsView = li.inflate(R.layout.promptsfolder, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                contextAddAccount);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.input_folder_name);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input, create folder entry and reload spinner
                                // edit text
                                createFolderAndReloadSpinner(userInput.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    protected void submitForm(){
        Toast.makeText(contextAddAccount, "Submit " ,
                Toast.LENGTH_LONG).show();
        Account account = new Account();
        account.setName(fldAccountName.getText().toString());
        account.setLogin(fldLogin.getText().toString());
        account.setEmail(fldEmail.getText().toString());
        account.setPassword(fldPassword.getText().toString());
        account.setUrl(fldUrl.getText().toString());
        account.setComment(fldComment.getText().toString());
        account.setIs_in_home(chkInHome.isChecked());
        account.setFolder(selectedFolder);
        account.save();

    }

    protected void createFolderAndReloadSpinner(String folderName){
        Folder folder = new Folder();
        folder.setName(folderName);
        folder.setOrder(1);
        folder.save();
        loadSpinnerFolderData();
    }

    /**
     * load folders from sqlite database and fill the spinner view
     */
    protected void loadSpinnerFolderData(){

        List<Folder> folderList = Folder.getAll();
        ArrayAdapter<Folder> folderArrayAdapter =  new ArrayAdapter<Folder>(this,
                android.R.layout.simple_spinner_item, folderList);
        spinnerFolder.setAdapter(folderArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_account_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_save_account:
                // save account
                submitForm();
                return true;
            case R.id.action_cancel_account:
                // TODO
                // close activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        // On selecting a spinner item, set selected folder object
        selectedFolder = (Folder)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
