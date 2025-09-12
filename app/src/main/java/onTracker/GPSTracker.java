package onTracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.rp.uihelpher.helpher.GlobalData;
import com.rp.uihelpher.localStorage.SharedPre;
import com.rp.uihelpher.log.IsLog;

import onInteface.OnInterface;

public class GPSTracker extends Service implements LocationListener, GlobalData {

    private String TAG = GPSTracker.class.getSimpleName();
    private final Activity context;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public boolean canGetLocation = false;

    Location location;

    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;
    public static final int REQUEST_CHECK_SETTINGS = 1;
    OnInterface.OnLoc onLoc;

    public GPSTracker(Activity context, OnInterface.OnLoc onLoc) {

        this.context = context;
        this.onLoc = onLoc;

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
        }
        LocationManager mLocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!enabled) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                displayLocationSettingsRequest(context);
            }else {
                showDialogGPS();
            }
        }
        new IsLog(TAG,"IF==context=");
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                new IsLog(TAG,"IF===");
            } else {
                this.canGetLocation = true;

                new IsLog(TAG,"IF==qr4342=");

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            new IsLog(TAG,"latitude==546=="+latitude);
                            new IsLog(TAG,"longitude==656=="+longitude);

                            onLoc.onViewLatLng("LOCATION",latitude,longitude);

                            SharedPre.setDef(context,TAG_CURR_LAT,latitude+"");
                            SharedPre.setDef(context,TAG_CURR_LNG,longitude+"");
                        }
                    }
                }

                if(isGPSEnabled) {
                    if(location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                onLoc.onViewLatLng("LOCATION",latitude,longitude);

                                SharedPre.setDef(context,TAG_CURR_LAT,latitude+"");
                                SharedPre.setDef(context,TAG_CURR_LNG,longitude+"");

                                new IsLog(TAG,"latitude===="+latitude);
                                new IsLog(TAG,"longitude===="+longitude);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new IsLog(TAG,"Exception==="+e.getMessage());
        }

        return location;
    }


    public void stopUsingGPS() {
        if(locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    private void displayLocationSettingsRequest(Activity context) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    new IsLog(TAG, "status " + "All location settings are satisfied.");
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    new IsLog(TAG, "Hello " + "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                    try {
                        status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        new IsLog(TAG, "pending " + "PendingIntent unable to execute request.");
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    new IsLog(TAG, "welcome " + "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                    break;
            }
        });
    }
    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setPositiveButton("Enable", (dialog, which) -> startActivity(
                new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}