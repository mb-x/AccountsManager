package com.devgrafix.accountsmanager.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devgrafix.accountsmanager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFolderActivityFragment extends Fragment {

    public ListFolderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_folder, container, false);
    }
}
