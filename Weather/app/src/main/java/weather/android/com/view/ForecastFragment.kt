package weather.android.com.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_loading.*
import kotlinx.android.synthetic.main.success_layout.*
import weather.android.com.viewmodel.LoadingViewModel
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.error_layout.*
import weather.android.com.R
import weather.android.com.model.Forecastday
import weather.android.com.model.ResponceDataModel


class ForecastFragment : Fragment() {

    private lateinit var location: String
    private val TAG = ForecastFragment::class.java.simpleName.toString()
    private lateinit var loadingViewModel: LoadingViewModel
    private lateinit var ctx: MainActivity

    lateinit var callback: ForecastFragmentListener

    interface ForecastFragmentListener {
         fun getLocation()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ForecastFragmentListener) {
            ctx = context as MainActivity
            callback = context
        } else {
            throw RuntimeException(context.toString() + " must implement ForecastFragmentListener")
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
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_retry.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            error_view.visibility = View.GONE
            callback.getLocation()
        }
    }

    private fun loadingObserver() {
        loadingViewModel.successResponseData.removeObservers(this)
        loadingViewModel.errorResponseData.removeObservers(this)
        loadingViewModel.successResponseData.observe(this, Observer { model -> showSuccessView(model) })
        loadingViewModel.errorResponseData.observe(this, Observer { str -> showErrorView() })
    }

    private fun showSuccessView(model: ResponceDataModel) {
        progress_bar.visibility = View.GONE
        Log.e(TAG, model.location.name)
        success_view.visibility = View.VISIBLE

        txt_city.text = model.location.name
        txt_temp.text = model.current.temp_c.toInt().toString()+"°"

        addViewEnterAnimation()

        addRecylerView(model)
    }

    private fun addRecylerView(model: ResponceDataModel) {
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        val forecastday: List<Forecastday> = model.forecast.forecastday.subList(1, model.forecast.forecastday.size)
        recyclerView.adapter = ForecastAdapter(ctx, forecastday)
    }

    private fun addViewEnterAnimation() {
        lnr_bottom.y = 1000f
        val animation = TranslateAnimation(
            lnr_bottom.getX(), lnr_bottom.getX(),
            0f, -1000f
        )
        animation.duration = 1000
        animation.fillAfter = true
        lnr_bottom.startAnimation(animation)

    }

    fun showErrorView() {
        progress_bar.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    fun getLocationForecast(currentLocation: String) {
        location = currentLocation
        loadingViewModel.doWeatherForeCasting(location)
    }

}
