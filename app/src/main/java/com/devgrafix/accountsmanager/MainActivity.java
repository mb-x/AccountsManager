package com.devgrafix.accountsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //********************** Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ***************** Initializing FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivity(intent);
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

        try {
            setUpNavigationView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void setUpNavigationView() throws SQLException {
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        FolderManager folderManager = new FolderManager(this);
        folderManager.open();
        List<Folder> categories = folderManager.findAll();
        for(Folder folder : categories){
            menu.add(0, (int) folder.getId(), folder.getOrder(), folder.getName());
        }
        folderManager.close();
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
        setFragment((long)id);
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

    protected void setFragment(long folder_id){
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putLong(ListFragment.FOLDER_KEY, folder_id);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }


}
