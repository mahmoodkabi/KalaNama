package com.silver.app.kalanama.CustomControl;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.silver.app.kalanama.R;

public class TabListener implements ActionBar.TabListener {

    private Fragment fragment;

    // The contructor.
    public TabListener(Fragment fragment) {
        this.fragment = fragment;
    }



    // When a tab is tapped, the FragmentTransaction replaces
    // the content of our main layout with the specified fragment;
    // that's why we declared an id for the main layout.
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
       ft.replace(R.id.tabcontent, fragment);
    }

    // When a tab is unselected, we have to hide it from the user's view.
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    // Nothing special here. Fragments already did the job.
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}

