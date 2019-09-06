package weather.android.com

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
import weather.android.com.viewmodel.LoadingViewModel


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

            error_view.visibility = View.VISIBLE
            Log.e(TAG, model.location.name)

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

    //career@beepnbuy.com

}
