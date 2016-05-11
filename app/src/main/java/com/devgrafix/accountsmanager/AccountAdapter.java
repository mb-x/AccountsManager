package com.devgrafix.accountsmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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

    //Interface to handle click in MainActivity
    public interface AccountClicked{
        void onClicked(int position, int oldPosition);
    }

    //To get the last position of account clicked
    public int getPositionClicked() {
        return positionClicked;
    }

    //Constructor with AccountClicked Interface
    public AccountAdapter(Context context,long folderId,AccountClicked accountClicked){
        accountManager = new AccountManager(context);
        listAccounts = accountManager.findByFolder(folderId);
        this.accountClicked = accountClicked;
    }

    /******************Override Methods********************/
    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.account_single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final AccountViewHolder holder, int position) {
        final Account currentAccount = listAccounts.get(position);

        holder.getTextAccount().setText(currentAccount.getName());
        holder.getTextEmail().setText(currentAccount.getEmail());
        holder.getTextEmail().setText(currentAccount.getPassword());


        //Make account VISIBLE or GONE at onClick
        if(position == positionClicked){
            holder.getAccountRelativeLayout().setVisibility(View.VISIBLE);
        }else{
            holder.getAccountRelativeLayout().setVisibility(View.GONE);
        }
        holder.getCurrentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPositionClicked = positionClicked;
                positionClicked = holder.getAdapterPosition();
                accountClicked.onClicked(holder.getAdapterPosition(), oldPositionClicked);//To notifyItemChanged from MainActivity
            }
        });

        //To remember which position of account LongClicked and also change that account to VISIBLE and make old one as GONE
        holder.getCurrentView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.setTitleMenu(currentAccount.getName());//To Show the name of account in the title of Menu
                oldPositionClicked = positionClicked;
                positionClicked = holder.getAdapterPosition();
                accountClicked.onClicked(holder.getAdapterPosition(), oldPositionClicked);//To notifyItemChanged from MainActivity
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }
    /***********************************************************/

    //ViewHolder Class that RecyclerView needs
    public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private ImageView imageAccount;
        private TextView textAccount;
        private RelativeLayout accountRelativeLayout;
        private View currentView;
        private TextView textEmail;
        private TextView textPassword;
        private String titleMenu = "Select The Action";
        public AccountViewHolder(View itemView) {
            super(itemView);

            imageAccount = (ImageView)itemView.findViewById(R.id.imageAccount);
            textAccount = (TextView)itemView.findViewById(R.id.textAccountName);
            accountRelativeLayout = (RelativeLayout)itemView.findViewById(R.id.accountRelativeLayout);
            textEmail = (TextView)itemView.findViewById(R.id.textEmail);
            textPassword = (TextView)itemView.findViewById(R.id.textPassword);
            currentView = itemView;

            currentView.setOnCreateContextMenuListener(this);//To Add Context Menu to the current view
        }

        //Getters and Setters
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

        public View getCurrentView() {
            return currentView;
        }

        public void setTitleMenu(String titleMenu) {
            this.titleMenu = titleMenu;
        }

        //to implement The Menu Context
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(titleMenu);
            menu.add(0, 1001, 0, "View Account");//groupId, itemId, order, title
            menu.add(0, 1002, 0, "Delete Account");
        }
    }
}