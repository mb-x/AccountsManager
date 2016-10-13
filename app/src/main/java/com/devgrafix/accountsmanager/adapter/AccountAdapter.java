package com.devgrafix.accountsmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.devgrafix.accountsmanager.R;
import com.devgrafix.accountsmanager.model.Account;

import java.util.List;

/**
 * Created by PC-MA13 on 13/10/2016.
 */
public class AccountAdapter extends ArrayAdapter<Account> {
    public AccountAdapter(Context context, List<Account> persons){
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
        }
        AccountViewHolder personViewHolder = (AccountViewHolder) convertView.getTag();
        if(personViewHolder == null){
            personViewHolder = new AccountViewHolder();
            personViewHolder.accountName = (TextView) convertView.findViewById(R.id.account_name);
            convertView.setTag(personViewHolder);
        }
        Account account = getItem(position);
        personViewHolder.accountName.setText(account.getName());
        return convertView;
    }

}
