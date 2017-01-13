package com.devgrafix.accountsmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.devgrafix.accountsmanager.Helper;
import com.devgrafix.accountsmanager.fragment.AccountAddOrEditFragment;
import com.devgrafix.accountsmanager.model.Folder;
import com.devgrafix.accountsmanager.fragment.AccountsListFragment;
import com.devgrafix.accountsmanager.R;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    protected FloatingActionButton fabCreateAccount;
    protected FloatingActionButton fabCreateFolder;
    protected FloatingActionMenu fabMenu;
    public List<Folder> folders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //********************** Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        // ***************** Initializing FloatingActionButton
        fabCreateAccount = (FloatingActionButton) findViewById(R.id.fab_add_account);
        fabCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.switchToFragment(getSupportFragmentManager(), AccountAddOrEditFragment.newInstance(0));
                fabMenu.close(true);
            }
        });
        fabCreateFolder = (FloatingActionButton) findViewById(R.id.fab_add_folder);
        fabCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent folderListIntent = new Intent(MainActivity.this, ListFolderActivity.class);
                startActivity(folderListIntent);
                fabMenu.close(true);
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(toggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        toggle.syncState();


        setUpNavigationView();
        Helper.switchToFragment(getSupportFragmentManager(), AccountsListFragment.newInstance(folders.get(0).getId()));
    }
    protected void setUpNavigationView(){
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        IconicsDrawable iconicsDrawable = new IconicsDrawable(getApplicationContext()).icon(FontAwesome.Icon.faw_folder);
        folders = Folder.getAll();
        if(folders.size()== 0){
            Folder folder = new Folder();
            folder.setName("Default");
            folder.setCreated_at(new Date());
            folder.save();
            folders.add(folder);
        }
        for(Folder folder : folders){
            menu.add(0,  folder.getId().intValue(), folder.getOrder(), folder.getName())
                    .setIcon(iconicsDrawable)
                    ;
        }

        //menu.add(0,99, 1,"Test").setIcon(R.drawable.ic_menu_slideshow);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method will trigger on item Click of navigation menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Checking if the item is in checked state or not, if not make it in checked state
       /* if(item.isChecked()) item.setChecked(false);
        else item.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();
*/
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Helper.switchToFragment(getSupportFragmentManager() ,AccountsListFragment.newInstance((long)id));

        //Check to see which item was being clicked and perform appropriate action
        /*if (id == R.id.nav_camera) {
            setFragment("Camera");
            //return true;
        } else if (id == R.id.nav_gallery) {
            setFragment("nav_gallery");
        } else if (id == R.id.nav_slideshow) {
            setFragment("nav_slideshow");

        } else if (id == R.id.nav_manage) {
            setFragment("nav_manage");

        } else if (id == R.id.nav_share) {
            setFragment("nav_share");

        } else if (id == R.id.nav_send) {
            setFragment("nav_send");

        }else if (id == 99) {
            setFragment("my personnal test");

        }*/

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
