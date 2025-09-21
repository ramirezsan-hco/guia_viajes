package com.example.guiaviajes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.card.MaterialCardView;

public class GaleriaActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private void mostrarModalTarjetas(String title, String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Cerrar", (d, which) -> d.dismiss())
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer_galeria);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        if (nav.getHeaderCount() == 0) {
            nav.inflateHeaderView(R.layout.nav_header);
        }
        // Menu hamburguesa que abre la navegación
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        //Para el modal que se abre al dar tab en las tarjetas
        MaterialCardView cParis  = findViewById(R.id.card_paris);
        MaterialCardView cKioto  = findViewById(R.id.card_kioto);
        MaterialCardView cMachu  = findViewById(R.id.card_machu);
        MaterialCardView cPetra  = findViewById(R.id.card_petra);

        cParis.setOnClickListener(v ->
                mostrarModalTarjetas("París, Francia",
                        "Ciudad de la luz, famosa por la Torre Eiffel, el Louvre y su gastronomía."));

        cKioto.setOnClickListener(v ->
                mostrarModalTarjetas("Kioto, Japón",
                        "Antigua capital imperial, conocida por sus templos budistas, jardines y geishas."));

        cMachu.setOnClickListener(v ->
                mostrarModalTarjetas("Machu Picchu, Perú",
                        "Ciudadela inca en los Andes, maravilla arqueológica rodeada de montañas."));

        cPetra.setOnClickListener(v ->
                mostrarModalTarjetas("Petra, Jordania",
                        "Ciudad nabatea tallada en roca, destacada por el Tesoro y el Siq."));



        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_perfil) {
                    startActivity(new Intent(GaleriaActivity.this, PerfilActivity.class));
                } else if (id == R.id.nav_galeria) {
                    Toast.makeText(GaleriaActivity.this, "Galería de Destinos", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_media) {
                    startActivity(new Intent(GaleriaActivity.this, MediaActivity.class));
                }
                else if (id == R.id.nav_web) {
                    Toast.makeText(GaleriaActivity.this, "Navegador Web", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_favoritos) {
                    startActivity(new Intent(GaleriaActivity.this, FavoritosActivity.class));
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Back: close drawer if open, else default
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    GaleriaActivity.super.onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
