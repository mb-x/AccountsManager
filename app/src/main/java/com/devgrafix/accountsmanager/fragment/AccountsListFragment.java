package com.devgrafix.accountsmanager.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devgrafix.accountsmanager.Helper;
import com.devgrafix.accountsmanager.adapter.AccountAdapter;
import com.devgrafix.accountsmanager.model.Account;
import com.devgrafix.accountsmanager.model.Folder;
import com.devgrafix.accountsmanager.R;

import java.util.List;

/**
 * Created by sbxramses on 17/04/16.
 */
public class AccountsListFragment extends Fragment {

    public static final String FOLDER_KEY = "folder_id";
    public static final String IS_HOME_KEY = "home";
    private TextView txtFolderName;
    private View view;
    private long folder_id;
    private boolean is_home= false;

    protected Account selectedAccount;
    protected int selectedAccountPosition;
    protected AccountAdapter accountAdapter;
    protected ListView accounListView;
    protected List<Account> accounts;

    protected Folder folder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            is_home =   getArguments().getBoolean(IS_HOME_KEY);
            folder_id =   getArguments().getLong(FOLDER_KEY);
            //Toast.makeText(getActivity(), "Param =" + folder_id, Toast.LENGTH_SHORT).show();
            folder = Folder.getOneById(folder_id);
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.accounts_list_fragment, container, false);
        initViews();
        return view;
    }

    protected void initViews(){
        txtFolderName =(TextView) view.findViewById(R.id.txt_folder_name);
        txtFolderName.setText(folder.getName());
        accounListView = (ListView) view.findViewById(R.id.accountListView);
    }

    protected void doHome(){

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupAccountListView();
    }
    protected void setupAccountListView(){
        accounts = Account.findByFolder(folder_id);
        accountAdapter = new AccountAdapter(getContext(), accounts);
        accounListView.setAdapter(accountAdapter);
        registerForContextMenu(accounListView);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.accountListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            selectedAccountPosition = info.position;
            selectedAccount = accounts.get(selectedAccountPosition);
            menu.setHeaderTitle(selectedAccount.getName());

            String[] menuItems = getResources().getStringArray(R.array.menu_edit_delete_items);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemId = item.getItemId();
        //String[] menuItems = getResources().getStringArray(R.array.menu_edit_delete_items);
        //String menuItemName = menuItems[menuItemId];
        selectedAccountPosition= info.position;
        selectedAccount = accounts.get(info.position);
       // Toast.makeText(getContext(), selectedAccount.toString(), Toast.LENGTH_LONG).show();
        switch (menuItemId){
            case 0: //edit
                Toast.makeText(getContext(),"Edit" +  selectedAccount.toString(), Toast.LENGTH_LONG).show();
                Helper.switchToFragment(getFragmentManager(), AccountAddOrEditFragment.newInstance(selectedAccount.getId()));
                break;
            case 1: //delete
                Toast.makeText(getContext(),"Delete" +  selectedAccount.toString(), Toast.LENGTH_LONG).show();
                askConfirmationBeforeDelete(info.position).show();
                break;
            default:
                break;
        }
        return true;
    }

    public static AccountsListFragment newInstance(long pFolderId) {
        AccountsListFragment fragment = new AccountsListFragment();
        Bundle args = new Bundle();
        args.putLong(AccountsListFragment.FOLDER_KEY, pFolderId);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog askConfirmationBeforeDelete(final int position)
    {
        AlertDialog dialogBox =new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this item")
                        //.setIcon(R.drawable.delete)
                        //.setCancelable(true)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        selectedAccount.delete();
                        accounts.remove(position);
                        accountAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),selectedAccount.getName()+" is deleted successfully", Toast.LENGTH_LONG ).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialogBox;

    }

}
