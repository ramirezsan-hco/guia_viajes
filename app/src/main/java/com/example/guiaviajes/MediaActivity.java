package com.example.guiaviajes;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MediaActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private VideoView videoView;
    private ImageButton btnPlayPause, btnMute;
    private SeekBar volumeSeek;
    private AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_media);

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
                    startActivity(new Intent(MediaActivity.this, PerfilActivity.class));
                } else if (id == R.id.nav_galeria) {
                    startActivity(new Intent(MediaActivity.this, GaleriaActivity.class));
                } else if (id == R.id.nav_media) {
                    Toast.makeText(MediaActivity.this, "Reproductor Multimedia", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_web) {
                    startActivity(new Intent(MediaActivity.this, WebActivity.class));
                } else if (id == R.id.nav_favoritos) {
                    startActivity(new Intent(MediaActivity.this, FavoritosActivity.class));
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
                    MediaActivity.super.onBackPressed();
                }
            }
        });

        //Controles
        videoView   = findViewById(R.id.videoView);
        btnPlayPause= findViewById(R.id.btnPlayPause);
        btnMute     = findViewById(R.id.btnMute);
        volumeSeek  = findViewById(R.id.volumeSeek);


        btnPlayPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            } else {
                videoView.start();
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            }
        });

        audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        int max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cur = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSeek.setMax(max);
        volumeSeek.setProgress(cur);
        volumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnMute.setOnClickListener(v -> {
            int vol = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (vol > 0) {
                volumeSeek.setTag(vol); // remember
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                volumeSeek.setProgress(0);
            } else {
                int prev = (volumeSeek.getTag() instanceof Integer) ? (Integer) volumeSeek.getTag() : max/2;
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, prev, 0);
                volumeSeek.setProgress(prev);
            }
        });
    }
}
