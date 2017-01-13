package com.devgrafix.accountsmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.model.Folder;

/**
 * Created by PC-MA13 on 13/01/2017.
 */
public class FolderViewHolder extends RecyclerView.ViewHolder {

    private final TextView ui_folderTitle;

    public FolderViewHolder(View itemView) {
        super(itemView);
        ui_folderTitle = (TextView) itemView.findViewById(R.id.folder_item_name);
    }

    public void layoutForFolder(Folder folder){
        ui_folderTitle.setText(folder.getName());
    }
}
