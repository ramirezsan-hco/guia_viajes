package com.example.guiaviajes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
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

public class PerfilActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Wrapper with drawer + includes activity_perfil.xml
        setContentView(R.layout.activity_drawer);

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
            Intent intent = new Intent(PerfilActivity.this, PerfilActivity.class);
            startActivity(intent);
        });

        // Hamburguesa
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        // --- Enable inner scrolling on long text fields ---
        EditText etInfo = findViewById(R.id.etInfoPersonal);
        EditText etEst  = findViewById(R.id.etEstudios);
        EditText etExp  = findViewById(R.id.etExperiencia);
        enableInnerScroll(etInfo);
        enableInnerScroll(etEst);
        enableInnerScroll(etExp);

        // Drawer navigation
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

        // Back: close drawer first
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    PerfilActivity.super.onBackPressed();
                }
            }
        });
    }

    /** Allow EditText to scroll vertically inside the outer ScrollView */
    private void enableInnerScroll(EditText et) {
        if (et == null) return;
        et.setVerticalScrollBarEnabled(true);
        et.setMovementMethod(new ScrollingMovementMethod());
        et.setOnTouchListener((v, event) -> {
            // If the EditText can scroll, don’t let the parent (ScrollView) intercept
            boolean canScroll = v.canScrollVertically(1) || v.canScrollVertically(-1);
            if (canScroll) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false; // still let the EditText handle the event
        });
    }
}
