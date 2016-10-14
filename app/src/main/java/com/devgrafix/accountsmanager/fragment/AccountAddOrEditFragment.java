package com.devgrafix.accountsmanager.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.model.Account;
import com.devgrafix.accountsmanager.model.Folder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AccountAddOrEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountAddOrEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCOUNT_ID = "ARG_ACCOUNT_ID ";

    // TODO: Rename and change types of parameters
    private Long account_id = null;

    protected View rootView;
    protected Folder selectedFolder = null;
    protected Spinner spinnerFolder;
    protected CheckBox chkInHome;

    protected EditText fldAccountName,
            fldLogin, fldEmail, fldPassword, fldRepeatPassword, fldUrl, fldComment
            ;
    protected Account account = null;
    protected Button btnSubmit, btnAddFolder ;
    protected ArrayAdapter<Folder> folderSpinnerAdapter;

    public AccountAddOrEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountAddOrEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountAddOrEditFragment newInstance(long param_account_id) {
        AccountAddOrEditFragment fragment = new AccountAddOrEditFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ACCOUNT_ID, param_account_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account_id = getArguments().getLong(ARG_ACCOUNT_ID);
            if(account_id > 0) {
                account = Account.getOneById(account_id);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account_add_or_edit, container, false);
        initViews();
        loadSpinnerFolderData();
        if(account != null) {
            fillComponents(account);
        }
        return rootView;
    }
    protected void initViews(){
        /* Spinners */
        spinnerFolder = (Spinner)rootView.findViewById(R.id.spinner_folder);
        spinnerFolder.setOnItemSelectedListener(this);
        /* EditText */
        fldAccountName = (EditText)rootView.findViewById(R.id.fld_account_name);

        fldLogin = (EditText)rootView.findViewById(R.id.fld_login);
        fldEmail = (EditText)rootView.findViewById(R.id.fld_email);
        fldPassword = (EditText)rootView.findViewById(R.id.fld_password);
        fldRepeatPassword = (EditText)rootView.findViewById(R.id.fld_repeat_password);
        fldUrl = (EditText)rootView.findViewById(R.id.fld_url);
        fldComment = (EditText)rootView.findViewById(R.id.fld_comment);

        /* Checkboxs */
        chkInHome = (CheckBox)rootView.findViewById(R.id.chk_in_home);

        /* Buttons */
        btnSubmit = (Button)rootView.findViewById(R.id.btn_submit_add_entry);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_id == null || account_id == 0 || account == null){
                    account = new Account();
                }
                hydrateAccount(account);
                if(validateAccountObject(account)) {
                    account.save();
                    Toast.makeText(getContext(), "Account saved successfully ",
                            Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });

        btnAddFolder = (Button)rootView.findViewById(R.id.btn_add_folder);
        btnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptCreateFolder();
            }
        });
    }
    /**
     * load folders from sqlite database and fill the spinner view
     */
    protected void loadSpinnerFolderData(){

        List<Folder> folderList = Folder.getAll();
        folderSpinnerAdapter =  new ArrayAdapter<Folder>(getContext(),
                android.R.layout.simple_spinner_item, folderList);
        spinnerFolder.setAdapter(folderSpinnerAdapter);
    }
    protected void promptCreateFolder(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.promptsfolder, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

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
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    protected void hydrateAccount(Account account){
        account.setName(fldAccountName.getText().toString());
        account.setLogin(fldLogin.getText().toString());
        account.setEmail(fldEmail.getText().toString());
        if(account_id == 0 || (fldPassword.getText().toString().isEmpty() && account_id != 0)){
            account.setPassword(fldPassword.getText().toString());
        }
        account.setUrl(fldUrl.getText().toString());
        account.setComment(fldComment.getText().toString());
        account.setIs_in_home(chkInHome.isChecked());
        account.setFolder(selectedFolder);
    }
    protected boolean validateAccountObject(Account account){
        boolean valide = true;
        if(account.getName().length()<1){
            fldAccountName.setError(getContext().getString(R.string.error_account_name));
            valide = false;
        }
        if(account.getLogin().length()<1){
            fldLogin.setError(getContext().getString(R.string.error_account_login));
            valide = false;
        }
        if(account.getEmail().length()<1){
            fldEmail.setError(getContext().getString(R.string.error_account_email));
            valide = false;
        }
        if(account.getPassword().length()<1 && account_id == 0){
            fldPassword.setError(getContext().getString(R.string.error_account_password_null));
            valide = false;
        }

        if(!fldRepeatPassword.getText().toString().equals(account.getPassword())){
            fldPassword.setError(getContext().getString(R.string.error_account_password_matche));
            valide = false;
        }
        return valide;
    }
    protected void fillComponents(Account account){
        fldAccountName.setText(account.getName());
        fldLogin.setText(account.getLogin());
        fldEmail.setText(account.getEmail());
        fldUrl.setText(account.getUrl());
        fldComment.setText(account.getComment());
        chkInHome.setChecked(account.getIs_in_home());
        int spinnerPos = folderSpinnerAdapter.getPosition(account.getFolder());
        spinnerFolder.setSelection(spinnerPos);
    }

    protected void createFolderAndReloadSpinner(String folderName){
        Folder folder = new Folder();
        folder.setName(folderName);
        folder.setOrder(1);
        folder.save();
        loadSpinnerFolderData();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item, set selected folder object
        selectedFolder = (Folder)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
