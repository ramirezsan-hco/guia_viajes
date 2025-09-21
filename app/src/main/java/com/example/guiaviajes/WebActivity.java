package com.example.guiaviajes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class WebActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private WebView webView;
    private EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_web);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        if (nav.getHeaderCount() == 0) nav.inflateHeaderView(R.layout.nav_header);

        // Menu hamburguesa
        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_perfil) {
                    startActivity(new Intent(WebActivity.this, PerfilActivity.class));
                } else if (id == R.id.nav_galeria) {
                    startActivity(new Intent(WebActivity.this, GaleriaActivity.class));
                } else if (id == R.id.nav_media) {
                    startActivity(new Intent(WebActivity.this, MediaActivity.class));
                } else if (id == R.id.nav_web) {
                    Toast.makeText(WebActivity.this, "Navegador Web", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_favoritos) {
                    startActivity(new Intent(WebActivity.this, FavoritosActivity.class));
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // WebView
        webView = findViewById(R.id.webView);
        etUrl = findViewById(R.id.etUrl);
        Button btnIr = findViewById(R.id.btnIr);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient()); // stay inside app

        btnIr.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (url.isEmpty()) return;
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            webView.loadUrl(url);
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    setEnabled(false);
                    WebActivity.super.onBackPressed();
                }
            }
        });
    }
}
