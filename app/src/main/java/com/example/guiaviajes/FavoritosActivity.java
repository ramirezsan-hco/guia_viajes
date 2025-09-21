package com.example.guiaviajes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class FavoritosActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_favoritos);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        if (nav.getHeaderCount() == 0) nav.inflateHeaderView(R.layout.nav_header);

        // Menu de hamburguesa
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        // elementos del menu navegación
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_perfil) {
                    startActivity(new Intent(FavoritosActivity.this, PerfilActivity.class));
                } else if (id == R.id.nav_galeria) {
                    startActivity(new Intent(FavoritosActivity.this, GaleriaActivity.class));
                } else if (id == R.id.nav_media) {
                    startActivity(new Intent(FavoritosActivity.this, MediaActivity.class));
                } else if (id == R.id.nav_web) {
                    startActivity(new Intent(FavoritosActivity.this, WebActivity.class));
                } else if (id == R.id.nav_favoritos) {
                    Toast.makeText(FavoritosActivity.this, "Favoritos y Comentarios", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    FavoritosActivity.super.onBackPressed();
                }
            }
        });

        // Acciones de la interfaz
        MaterialButton btnGuardar = findViewById(R.id.btnGuardar);
        MaterialButton btnCompartir = findViewById(R.id.btnCompartir);
        MaterialButton btnPublicar = findViewById(R.id.btnPublicar);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText etComentario = findViewById(R.id.etComentario);

        btnGuardar.setOnClickListener(v ->
                Toast.makeText(this, "Guardado en favoritos", Toast.LENGTH_SHORT).show());

        btnCompartir.setOnClickListener(v -> {
            String comment = etComentario.getText().toString().trim();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT,
                    TextUtils.isEmpty(comment) ? "Mi experiencia buena!!" : comment);
            startActivity(Intent.createChooser(share, "Compartir experiencia"));
        });

        btnPublicar.setOnClickListener(v -> {
            String comment = etComentario.getText().toString().trim();
            if (comment.isEmpty()) {
                Toast.makeText(this, "Escribe un comentario primero", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Comentario publicado", Toast.LENGTH_SHORT).show();
                etComentario.setText("");
            }
        });

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                Toast.makeText(this, "Calificación: " + (int) rating + " estrellas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
