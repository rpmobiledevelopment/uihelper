package onTracker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre
import com.rp.uihelpher.log.IsLog
import onInteface.OnInterface.OnLoc

open class GPSTracker(private val context: Activity, var onLoc: OnLoc) : Service(), LocationListener,
    GlobalData {

    private val TAG: String = GPSTracker::class.java.simpleName

    var isGPSEnabled: Boolean = false
    var isNetworkEnabled: Boolean = false
    var canGetLocation: Boolean = false

    var location: Location? = null

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    protected var locationManager: LocationManager? = null

    init {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context, arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 10
            )
        }
        val mLocManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        val enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!enabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                displayLocationSettingsRequest(context)
            } else {
                showDialogGPS()
            }
        }
        IsLog(TAG, "IF==context=")
        getLocation()
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLocation(): Location? {
        try {
            locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?

            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
                IsLog(TAG, "IF===")
            } else {
                this.canGetLocation = true

                IsLog(TAG, "IF==qr4342=")

                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )

                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                            IsLog(TAG, "latitude==546==$latitude")
                            IsLog(TAG, "longitude==656==$longitude")

                            onLoc.onViewLatLng("LOCATION", latitude, longitude)

                            SharedPre.setDef(
                                context,
                                GlobalData.TAG_CURR_LAT,
                                latitude.toString() + ""
                            )
                            SharedPre.setDef(
                                context,
                                GlobalData.TAG_CURR_LNG,
                                longitude.toString() + ""
                            )
                        }
                    }
                }

                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        if (locationManager != null) {
                            location =
                                locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                            if (location != null) {
                                latitude = location!!.getLatitude()
                                longitude = location!!.getLongitude()

                                onLoc.onViewLatLng("LOCATION", latitude, longitude)

                                SharedPre.setDef(
                                    context,
                                    GlobalData.TAG_CURR_LAT,
                                    latitude.toString() + ""
                                )
                                SharedPre.setDef(
                                    context,
                                    GlobalData.TAG_CURR_LNG,
                                    longitude.toString() + ""
                                )

                                IsLog(TAG, "latitude====$latitude")
                                IsLog(TAG, "longitude====$longitude")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            IsLog(TAG, "Exception===" + e.message)
        }

        return location
    }


    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    override fun onProviderDisabled(arg0: String) {
    }

    override fun onProviderEnabled(arg0: String) {
    }

    override fun onLocationChanged(p0: Location) {
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    private fun displayLocationSettingsRequest(context: Activity) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(10000)
        locationRequest.setFastestInterval((10000 / 2).toLong())

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(ResultCallback { result1: LocationSettingsResult? ->
            val status = result1!!.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> IsLog(TAG, "status " + "All location settings are satisfied.")

                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    IsLog(TAG, "Hello " + "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")
                    try {
                        status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS)
                    } catch (e: SendIntentException) {
                        IsLog(TAG, "pending " + "PendingIntent unable to execute request.")
                    }
                }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> IsLog(
                    TAG,
                    "welcome " + "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                )
            }
        })
    }

    private fun showDialogGPS() {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle("Enable GPS")
        builder.setMessage("Please enable GPS")
        builder.setPositiveButton(
            "Enable"
        ) { dialog: DialogInterface?, which: Int ->
            startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            )
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface?, which: Int -> dialog!!.cancel() }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
        const val REQUEST_CHECK_SETTINGS: Int = 1
    }
}