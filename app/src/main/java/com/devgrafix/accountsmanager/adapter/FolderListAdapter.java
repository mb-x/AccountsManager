package com.devgrafix.accountsmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.model.Folder;

import java.util.List;

/**
 * Created by PC-MA13 on 13/01/2017.
 */
public class FolderListAdapter extends RecyclerView.Adapter<FolderViewHolder> {

    private List<Folder> _folderList;

    public FolderListAdapter() {
        _folderList = Folder.getAll();
//        notifyDataSetChanged();
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_list_item, parent, false);

        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.layoutForFolder(_folderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(_folderList != null){
            return _folderList.size();
        }
        return 0;
    }
}
