package weather.android.com.view

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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellLocation.requestLocationUpdate
import android.widget.Toast
import androidx.core.content.ContextCompat
import weather.android.com.R
import weather.android.com.view.LoadingFragment.*
import weather.android.com.util.ManageFragment
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), LoadingFragmentListener, LocationListener {

    private lateinit var fragmentLoading: LoadingFragment
    private lateinit var locationManager: LocationManager

    var isGPSEnabled = false
    var isNetworkEnabled = false

    var currentLocation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getLocation()

        addLoadingFragment()

    }

    private fun addLoadingFragment() {
        fragmentLoading = LoadingFragment()
        ManageFragment.addFrag(this, fragmentLoading, "")
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
           getLocationUpdate()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationUpdate() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled && !isNetworkEnabled) {

            Toast.makeText(this, "Please enable your device location", Toast.LENGTH_SHORT).show()

        } else {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                10f,
                this
            )

        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocationUpdate()
                }
            }
        }
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

    companion object {
        fun checkPermissions(ctx: Context, permission: String): Boolean {

            val permission = ContextCompat.checkSelfPermission(ctx, permission)

            return permission == PackageManager.PERMISSION_GRANTED


        }
    }

    fun getAddressByLatLog(lat: Double, lng: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            currentLocation = obj.subAdminArea

            fragmentLoading.getLocationForecast(currentLocation)

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }

}
