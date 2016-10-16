package com.devgrafix.accountsmanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devgrafix.accountsmanager.Helper;
import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.model.Account;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link accountDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountDetailsFragment extends Fragment {
    private static final String ARG_ACCOUNT_ID = "ARG_ACCOUNT_ID";

    private Long account_id;
    private Account account;

    private View rootView;
    protected TextView accountName, accountLogin, accountEmail, accountUrl, accountComment,
                createdAt, updatedAt
                ;

    public accountDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pAccountId Parameter 1.
     * @return A new instance of fragment accountDetailsFragment.
     */
    public static accountDetailsFragment newInstance(Long pAccountId) {
        accountDetailsFragment fragment = new accountDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ACCOUNT_ID, pAccountId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account_id = getArguments().getLong(ARG_ACCOUNT_ID);
            account = Account.getOneById(account_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account_details, container, false);
        initViews();
        initEvents();
        fillComponents(account);
        return rootView;
    }
    protected void initViews(){
        accountName = (TextView)rootView.findViewById(R.id.account_name);
        accountLogin = (TextView)rootView.findViewById(R.id.account_login);
        accountEmail = (TextView)rootView.findViewById(R.id.account_email);
        accountUrl = (TextView)rootView.findViewById(R.id.account_url);
        accountComment = (TextView)rootView.findViewById(R.id.account_comment);
        createdAt = (TextView)rootView.findViewById(R.id.account_created_at);
        updatedAt = (TextView)rootView.findViewById(R.id.account_updated_at);
    }
    protected void initEvents(){

    }
    protected void fillComponents(Account account){
        accountName.setText(account.getName());
        accountLogin.setText(accountLogin.getText()+account.getLogin());
        accountEmail.setText(accountEmail.getText()+account.getEmail());
        accountUrl.setText(accountUrl.getText()+account.getUrl());
        accountComment.setText(accountComment.getText()+account.getComment());
        if(account.getCreated_at()!= null){
            createdAt.setText(createdAt.getText()+ Helper.getFormatedDateTime(account.getCreated_at()));
        }
        if(account.getUpdated_at()!= null){
            updatedAt.setText(updatedAt.getText()+ Helper.getFormatedDateTime(account.getUpdated_at()));
        }
    }
}
