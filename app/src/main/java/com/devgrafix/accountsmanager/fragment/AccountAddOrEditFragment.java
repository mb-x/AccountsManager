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
 * {@link AccountAddOrEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountAddOrEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountAddOrEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCOUNT_ID = "ARG_ACCOUNT_ID ";

    // TODO: Rename and change types of parameters
    private Long account_id;

    protected View rootView;
    protected Folder selectedFolder;
    protected Spinner spinnerFolder;
    protected CheckBox chkInHome;

    protected EditText fldAccountName,
            fldLogin, fldEmail, fldPassword, fldRepeatPassword, fldUrl, fldComment
            ;
    protected Button btnSubmit, btnAddFolder ;

    private OnFragmentInteractionListener mListener;

    public AccountAddOrEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AccountAddOrEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountAddOrEditFragment newInstance(long param1) {
        AccountAddOrEditFragment fragment = new AccountAddOrEditFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ACCOUNT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account_id = getArguments().getLong(ARG_ACCOUNT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account_add_or_edit, container, false);
        initViews();
        loadSpinnerFolderData();
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
                submitForm();
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
        ArrayAdapter<Folder> folderArrayAdapter =  new ArrayAdapter<Folder>(getContext(),
                android.R.layout.simple_spinner_item, folderList);
        spinnerFolder.setAdapter(folderArrayAdapter);
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
    protected void submitForm(){

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
        Toast.makeText(getContext(), "Account saved succefully ",
                Toast.LENGTH_LONG).show();
    }

    protected void createFolderAndReloadSpinner(String folderName){
        Folder folder = new Folder();
        folder.setName(folderName);
        folder.setOrder(1);
        folder.save();
        loadSpinnerFolderData();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item, set selected folder object
        selectedFolder = (Folder)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
