package com.devgrafix.accountsmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by sbxramses on 17/04/16.
 */
public class ListFragment extends android.support.v4.app.ListFragment
                implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String FOLDER_KEY = "folder_id";
    public static final String IS_HOME_KEY = "home";
    private TextView txtShow;
    private View view;
    private long folder_id;
    private boolean is_home;

    protected FolderManager folderManager;
    protected AccountManager accountManager;
    protected Folder folder;
    protected List<Account> accounts;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);
        initViews();
        return view;
    }

    protected void initViews(){
        txtShow =(TextView) view.findViewById(R.id.txt_show);
        txtShow.setText(folder.getName());
        Button btnAdd = (Button)view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    protected void doHome(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        is_home =  (getArguments() != null)? getArguments().getBoolean(IS_HOME_KEY) : false;
        folder_id =  (getArguments() != null)? getArguments().getLong(FOLDER_KEY): 0;
        Toast.makeText(getActivity(), "Param =" + folder_id, Toast.LENGTH_SHORT).show();
        folderManager = new FolderManager(getContext());
        accountManager = new AccountManager(getContext());
        folder = folderManager.findById(folder_id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        accounts = accountManager.findAll();
        ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(getContext(), android.R.layout.simple_list_item_1, accounts);
        setListAdapter(adapter);

        /*ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets1, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);*/
        getListView().setOnItemClickListener(this);

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Folder> adapter = (ArrayAdapter<Folder>) getListAdapter();
        Folder folder = null;
        switch (view.getId()) {
            case R.id.btn_add:

                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                Folder folderToAdd = new Folder();
                folderToAdd.setName(comments[nextInt]);
                folderToAdd.setOrder(1);
                // enregistrer le nouveau commentaire dans la base de données
                    long id = folderManager.add(folderToAdd);
                    Toast.makeText(getActivity(), "Inserted key=" + id, Toast.LENGTH_SHORT).show();
                    folder = folderManager.findById(id);
                    adapter.add(folder);

                break;
            /*case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;*/
        }
        adapter.notifyDataSetChanged();
    }
}
