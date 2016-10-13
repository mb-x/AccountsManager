package com.devgrafix.accountsmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by PC-MA13 on 13/10/2016.
 */
public class Helper {

    public static void switchToFragment(FragmentManager fragmentManager, Fragment newFragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
