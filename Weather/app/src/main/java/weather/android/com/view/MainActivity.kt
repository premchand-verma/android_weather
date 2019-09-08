package weather.android.com.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import weather.android.com.LocationProvider
import weather.android.com.R
import weather.android.com.view.ForecastFragment.*
import weather.android.com.util.ManageFragment

class MainActivity : AppCompatActivity(), ForecastFragmentListener{

    private lateinit var locationProvider: LocationProvider
    lateinit var fragmentForecast: ForecastFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationProvider = LocationProvider(this)

        addLoadingFragment()
    }

    private fun addLoadingFragment() {
        fragmentForecast = ForecastFragment()
        ManageFragment.addFrag(this, fragmentForecast, "")
    }

    override fun getLocation() {
        locationProvider.getLocation()
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
                    locationProvider.getLocationUpdate()
                }
                else{
                    Toast.makeText(this, "Location access permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
