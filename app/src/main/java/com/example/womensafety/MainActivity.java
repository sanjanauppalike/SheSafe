package com.example.womensafety;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("PhoneNo", MODE_PRIVATE);
        String ENUM = sharedPreferences.getString("ENUM", "NONE");
        if (ENUM.equalsIgnoreCase("NONE")) {
            startActivity(new Intent(this, RegisterNo.class));
        } else {
            TextView textView = findViewById(R.id.sosTextView);
            textView.setText("SOS will be sent to\n" + ENUM);
        }
    }

    private ActivityResultLauncher<String[]> multiplePermission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            for (Map.Entry<String, Boolean> entry : result.entrySet())
                if (!entry.getValue()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permission must be granted", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Grant Permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            multiplePermission.launch(new String[]{entry.getKey()});
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
        }
    });

    public void startService(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED ) {
            Intent notificationIntent = new Intent(this,ServiceMine.class);
            notificationIntent.setAction("Start");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(notificationIntent);
                Snackbar.make(findViewById(android.R.id.content),"Service Started!", Snackbar.LENGTH_LONG).show();
            }
        }else{
            multiplePermission.launch(new String[]{Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION});
        }

    }    public void stopService(View view) {

        Intent notificationIntent = new Intent(this, ServiceMine.class);
        notificationIntent.setAction("stop");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(notificationIntent);
            Snackbar.make(findViewById(android.R.id.content), "Service Stopped!", Snackbar.LENGTH_LONG).show();
        }
    }

    public void PopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.changenum, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.changeNum) {
                    startActivity(new Intent(MainActivity.this, RegisterNo.class));
                }
                return true;
            }
        });
        popupMenu.show();
    }
}