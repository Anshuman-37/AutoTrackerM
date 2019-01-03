package com.armyof2.autotracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

public class LocationServices extends Service {
    private int NOTIFICATION = 1;
    public static boolean isRunning = false;
    public static LocationServices instance = null;
    private NotificationManager notificationManager = null;
    String Channel_ID = "AutoTracker";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        instance = this;
        isRunning = true;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                MainActivity.class), 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Channel_ID, "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Service running...")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("AutoTracker")
                .setContentText("Some Shit is trying to track you")
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .build();
        // Start service in foreground mode

        startForeground(NOTIFICATION, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        instance = null;
        notificationManager.cancel(NOTIFICATION); // Remove notification
        super.onDestroy();
    }

    public Location TrackLocation() {
        Location location = null;
        LocationManager locationManager = null;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return location;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        getLastKnownLocation();
        /**
         * Create a getter setter for the function and then the things will be done
         * i am leaving this to you i have commented the function above u can see and when u will create the getter setter of
         * function then call this service in the broadcast receiver */
        return location;
    }

}