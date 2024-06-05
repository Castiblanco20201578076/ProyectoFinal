package com.example.proyectofinal;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;


public class NavClienteFragment extends Fragment {

    NavController navController;

    NavHostFragment navHostFragment;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;

    AppCompatActivity activity;

    ActionBarDrawerToggle toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_cliente, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        activity = (AppCompatActivity) getActivity();
        if(activity != null){
            activity.setSupportActionBar(toolbar);
        }

        drawerLayout = view.findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = view.findViewById(R.id.navigationView);


        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentHostCliente);
        if(navHostFragment != null){
            navController =  navHostFragment.getNavController();
            NavigationUI.setupWithNavController(navigationView, navController);
            NavigationUI.setupActionBarWithNavController(activity, navController, drawerLayout);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (navController != null && NavigationUI.onNavDestinationSelected(item, navController)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        drawerLayout.removeDrawerListener((DrawerLayout.DrawerListener) getActivity());
    }
}