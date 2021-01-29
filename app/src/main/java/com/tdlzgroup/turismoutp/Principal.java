package com.tdlzgroup.turismoutp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class Principal extends AppCompatActivity implements PaqueteFragment.OnListFragmentInteractionListener, CompraFragment.OnListFragmentInteractionListener {

    public static String userID;
    public static String userNombre;
    public static String userURL;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent intent = getIntent();
        userID = intent.getStringExtra("id");
        userNombre = intent.getStringExtra("nombre");
        userURL = intent.getStringExtra("url");
        drawerLayout = findViewById(R.id.drawer_layout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    @Override
    public void onListFragmentInteraction(ModelPaquete item) {}

    @Override
    public void onListFragmentInteraction(ModelCompra item) {}
}
