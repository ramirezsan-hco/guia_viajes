package com.example.guiaviajes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.Button;
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
        setContentView(R.layout.activity_drawer_galeria); // wrapper with include of the XML above
        View root = getWindow().getDecorView().findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        if (nav.getHeaderCount() == 0) {
            nav.inflateHeaderView(R.layout.nav_header);
        }
        View headerView = nav.getHeaderView(0);
        Button btnEditarPerfil = (Button) headerView.findViewById(R.id.btnEditarPerfilHeader);

        btnEditarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(GaleriaActivity.this, PerfilActivity.class);
            startActivity(intent);
        });
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        // Find all cards
        MaterialCardView cParis      = findViewById(R.id.card_paris);
        MaterialCardView cKioto      = findViewById(R.id.card_kioto);
        MaterialCardView cMachu      = findViewById(R.id.card_machu);
        MaterialCardView cPetra      = findViewById(R.id.card_petra);
        MaterialCardView cRoma       = findViewById(R.id.card_roma);
        MaterialCardView cLondres    = findViewById(R.id.card_londres);
        MaterialCardView cNY         = findViewById(R.id.card_ny);
        MaterialCardView cCairo      = findViewById(R.id.card_cairo);
        MaterialCardView cSydney     = findViewById(R.id.card_sydney);
        MaterialCardView cBangkok    = findViewById(R.id.card_bangkok);
        MaterialCardView cBarcelona  = findViewById(R.id.card_barcelona);
        MaterialCardView cIstanbul   = findViewById(R.id.card_istanbul);

        // Clicks -> modal with description
        cParis.setOnClickListener(v ->
                mostrarModalTarjetas("París, Francia",
                        "Ciudad de la luz: Torre Eiffel, Louvre, Sena y alta gastronomía."));

        cKioto.setOnClickListener(v ->
                mostrarModalTarjetas("Kioto, Japón",
                        "Antigua capital imperial, templos budistas, jardines zen y geishas."));

        cMachu.setOnClickListener(v ->
                mostrarModalTarjetas("Machu Picchu, Perú",
                        "Ciudadela inca en los Andes, maravilla arqueológica."));

        cPetra.setOnClickListener(v ->
                mostrarModalTarjetas("Petra, Jordania",
                        "Ciudad nabatea tallada en roca, famoso ‘Tesoro’ y el Siq."));

        cRoma.setOnClickListener(v ->
                mostrarModalTarjetas("Roma, Italia",
                        "Coliseo, Foro Romano, Vaticano y fuentes barrocas."));

        cLondres.setOnClickListener(v ->
                mostrarModalTarjetas("Londres, Reino Unido",
                        "Big Ben, Tower Bridge, museos de clase mundial."));

        cNY.setOnClickListener(v ->
                mostrarModalTarjetas("Nueva York, EE.UU.",
                        "Times Square, Central Park, Broadway y rascacielos icónicos."));

        cCairo.setOnClickListener(v ->
                mostrarModalTarjetas("El Cairo, Egipto",
                        "Pirámides de Giza, Esfinge y Museo Egipcio."));

        cSydney.setOnClickListener(v ->
                mostrarModalTarjetas("Sídney, Australia",
                        "Ópera de Sídney, Harbour Bridge y playas."));

        cBangkok.setOnClickListener(v ->
                mostrarModalTarjetas("Bangkok, Tailandia",
                        "Templos brillantes, mercados flotantes y vida nocturna."));

        cBarcelona.setOnClickListener(v ->
                mostrarModalTarjetas("Barcelona, España",
                        "Sagrada Familia, modernismo de Gaudí y Mediterráneo."));

        cIstanbul.setOnClickListener(v ->
                mostrarModalTarjetas("Estambul, Turquía",
                        "Entre Europa y Asia: Santa Sofía, Mezquita Azul, Bazar."));

        // Drawer navigation (unchanged)
        nav.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_perfil) {
                startActivity(new Intent(GaleriaActivity.this, PerfilActivity.class));
            } else if (id == R.id.nav_galeria) {
                Toast.makeText(GaleriaActivity.this, "Galería de Destinos", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_media) {
                startActivity(new Intent(GaleriaActivity.this, MediaActivity.class));
            } else if (id == R.id.nav_web) {
                startActivity(new Intent(GaleriaActivity.this, WebActivity.class));
            } else if (id == R.id.nav_favoritos) {
                startActivity(new Intent(GaleriaActivity.this, FavoritosActivity.class));
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        // Back: close drawer first
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    GaleriaActivity.super.onBackPressed();
                }
            }
        });
    }
}