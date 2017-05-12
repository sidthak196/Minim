package com.minin.sidthakur.minim.controller;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.minin.sidthakur.minim.R;

import layout.ListFragment;
import layout.PlayerFragment;

/**
 * Created by siddharth_thakur on 4/1/17.
 */

public class Main_Activity extends FragmentActivity implements PlayerFragment.OnFragmentInteractionListener ,ListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container)!=null)
        {
            if(savedInstanceState!=null)
                return;
            //TODO : should start with earlier fragment

            ListFragment listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,listFragment).commit();
        }
    }

    @Override
    public void onPlayerFragmentInteraction(Bundle bundle) {

    }

    @Override
    public void onListFragmentInteraction(Bundle bundle) {
        PlayerFragment fragment=PlayerFragment.newInstance();
            fragment.setArguments(bundle);
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }
}
