package com.example.guiaviajes;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PerfilActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        if (nav.getHeaderCount() == 0) {
            nav.inflateHeaderView(R.layout.nav_header);
        }
        // Menu hamburguewsa
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_perfil) {
                    Toast.makeText(PerfilActivity.this, "Gestión de Perfil", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_galeria) {
                    startActivity(new Intent(PerfilActivity.this, GaleriaActivity.class));
                } else if (id == R.id.nav_media) {
                    startActivity(new Intent(PerfilActivity.this, MediaActivity.class));
                } else if (id == R.id.nav_web) {
                    startActivity(new Intent(PerfilActivity.this, WebActivity.class));
                } else if (id == R.id.nav_favoritos) {
                    startActivity(new Intent(PerfilActivity.this, FavoritosActivity.class));
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    PerfilActivity.super.onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
