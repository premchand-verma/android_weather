package weather.android.com.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_loading.*
import kotlinx.android.synthetic.main.success_layout.*
import weather.android.com.viewmodel.LoadingViewModel
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import weather.android.com.R
import weather.android.com.model.Forecastday


class LoadingFragment : Fragment() {

    private lateinit var location: String
    private val TAG = LoadingFragment::class.java.simpleName.toString()
    private lateinit var loadingViewModel: LoadingViewModel
    private lateinit var ctx: MainActivity

    interface LoadingFragmentListener {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LoadingFragmentListener) {
            ctx = context as MainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement LoadingFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingViewModel = ViewModelProviders.of(this).get(LoadingViewModel::class.java)
        loadingObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun loadingObserver() {
        loadingViewModel.successResponseData.removeObservers(this)
        loadingViewModel.errorResponseData.removeObservers(this)

        loadingViewModel.successResponseData.observe(this, Observer { model ->

            progress_bar.visibility = View.GONE
            Log.e(TAG, model.location.name)
            success_view.visibility = View.VISIBLE

            lnr_bottom.y = 1000f
            val animation = TranslateAnimation(
                lnr_bottom.getX(), lnr_bottom.getX(),
                0f, -1000f
            )
            animation.duration = 1000
            animation.fillAfter = true
            lnr_bottom.startAnimation(animation)


            txt_city.text = model.location.name
            txt_temp.text = model.current.temp_c.toInt().toString()+"°"

            // Creates a vertical Layout Manager
            recyclerView.layoutManager = LinearLayoutManager(ctx)

            // You can use LinearLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
            recyclerView.layoutManager = LinearLayoutManager(ctx)

            val forecastday: List<Forecastday> = model.forecast.forecastday.subList(1, model.forecast.forecastday.size)
            // Access the RecyclerView Adapter and load the data into it
            recyclerView.adapter = ForecastAdapter(ctx, forecastday)

        })

        loadingViewModel.errorResponseData.observe(this, Observer { str ->

            progress_bar.visibility = View.GONE
            error_view.visibility = View.VISIBLE
            Log.e(TAG, str)

        })
    }

    fun getLocationForecast(currentLocation: String) {
        location = currentLocation
        loadingViewModel.doWeatherForeCasting(location)
    }

}
