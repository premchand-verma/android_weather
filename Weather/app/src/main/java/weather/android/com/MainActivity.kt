package weather.android.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import weather.android.com.utils.ManageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ManageFragment.addFrag(this, LoadingFragment(), "")
    }
}
