package com.example.mitv;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=59ARDCi9eYs";
    private TextView textViewTime;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOpenYouTube = findViewById(R.id.button_open_youtube);
        buttonOpenYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeVideo();
            }
        });

        textViewTime = findViewById(R.id.textViewTime1); // Agrega esta línea

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000); // Actualizar cada segundo
            }
        };

        TextView textViewDate = findViewById(R.id.editTextDate1);

        Calendar calendar = Calendar.getInstance(new Locale("es", "ES"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        textViewDate.setText(currentDate);
    }

    private void openYouTubeVideo() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VIDEO_URL));
        intent.setPackage("com.google.android.youtube");
        if (isIntentAvailable(intent)) {
            startActivity(intent);
        } else {
            // YouTube app is not installed, handle this case or prompt the user to install it
            openYouTubeWebsite();
        }
    }

    private boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

    private void openYouTubeWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VIDEO_URL));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 0); // Iniciar la actualización de tiempo
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Detener la actualización de tiempo
    }

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(calendar.getTime());
        textViewTime.setText(currentTime);
    }
}
