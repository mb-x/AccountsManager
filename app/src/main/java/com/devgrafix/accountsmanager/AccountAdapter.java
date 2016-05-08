package com.devgrafix.accountsmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/8/16.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{
    private List<Account> listAccounts;
    private AccountManager accountManager;
    private AccountClicked accountClicked;
    private int positionClicked = 0;
    private int oldPositionClicked;

    public interface AccountClicked{
        public void onClicked(int oldPosition);
    }

    public AccountAdapter(Context context,long folderId,AccountClicked accountClicked){
        accountManager = new AccountManager(context);
        listAccounts = accountManager.findByFolder(folderId);
        this.accountClicked = accountClicked;
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.account_single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final AccountViewHolder holder, final int position) {
        Account currentAccount = listAccounts.get(position);

        holder.getTextAccount().setText(currentAccount.getName());
        holder.getTextEmail().setText(currentAccount.getEmail());
        holder.getTextEmail().setText(currentAccount.getPassword());


        //Make account visible or gone in onClick
        if(position == positionClicked){
            holder.getAccountRelativeLayout().setVisibility(View.VISIBLE);
        }else{
            holder.getAccountRelativeLayout().setVisibility(View.GONE);
        }
        holder.getCurrentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPositionClicked = positionClicked;
                positionClicked = position;
                accountClicked.onClicked(oldPositionClicked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageAccount;
        private TextView textAccount;
        private RelativeLayout accountRelativeLayout;
        private View currentView;
        private TextView textEmail;
        private TextView textPassword;

        public AccountViewHolder(View itemView) {
            super(itemView);

            imageAccount = (ImageView)itemView.findViewById(R.id.imageAccount);
            textAccount = (TextView)itemView.findViewById(R.id.textAccountName);
            accountRelativeLayout = (RelativeLayout)itemView.findViewById(R.id.accountRelativeLayout);
            textEmail = (TextView)itemView.findViewById(R.id.textEmail);
            textPassword = (TextView)itemView.findViewById(R.id.textPassword);
            currentView = itemView;
        }

        public ImageView getImageAccount() {
            return imageAccount;
        }

        public RelativeLayout getAccountRelativeLayout() {
            return accountRelativeLayout;
        }

        public TextView getTextAccount() {
            return textAccount;
        }

        public TextView getTextEmail() {
            return textEmail;
        }

        public TextView getTextPassword() {
            return textPassword;
        }

        public void setImageAccount(ImageView imageAccount) {
            this.imageAccount = imageAccount;
        }

        public View getCurrentView() {
            return currentView;
        }
    }
}