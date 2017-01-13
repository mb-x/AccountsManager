package com.devgrafix.accountsmanager.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.adapter.FolderListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFolderActivityFragment extends Fragment {

    private View view;
    private RecyclerView ui_folderRecyclerView;

    public ListFolderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_folder, container, false);
        initViews();
        initEvents();
        return view;
    }

    public void initViews(){
        ui_folderRecyclerView = (RecyclerView)view.findViewById(R.id.folder_recycler_view);
        ui_folderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ui_folderRecyclerView.setAdapter(new FolderListAdapter());
    }
    public void initEvents(){

    }



}
