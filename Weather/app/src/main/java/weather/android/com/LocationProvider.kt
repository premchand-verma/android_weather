package weather.android.com

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import weather.android.com.view.MainActivity
import java.io.IOException
import java.util.*

class LocationProvider(var ctx: MainActivity) : LocationListener {

    private var locationManager: LocationManager

    var isGPSEnabled = false
    var isNetworkEnabled = false

    var currentLocation = ""

    init {
        locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getLocation()
    }

    @SuppressLint("MissingPermission")
    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            location.accuracy = 100f
            locationManager.removeUpdates(this)

            getAddressByLatLog(location.latitude, location.longitude)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    fun getLocation() {
        if (checkPermissions(
                ctx,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            getLocationUpdate()
        } else {
            ctx.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocationUpdate() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled && !isNetworkEnabled) {

            Toast.makeText(ctx, "Please enable your device location", Toast.LENGTH_SHORT).show()

        } else {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                10f,
                this
            )
        }
    }

    fun getAddressByLatLog(lat: Double, lng: Double) {
        val geocoder = Geocoder(ctx, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            currentLocation = obj.subAdminArea

            ctx.fragmentForecast.getLocationForecast(currentLocation)

        } catch (e: IOException) {
            e.printStackTrace()
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            ctx.fragmentForecast.showErrorView()
        }
    }

    companion object {
        fun checkPermissions(ctx: Context, permission: String): Boolean {
            val permission = ContextCompat.checkSelfPermission(ctx, permission)
            return permission == PackageManager.PERMISSION_GRANTED
        }
    }

}