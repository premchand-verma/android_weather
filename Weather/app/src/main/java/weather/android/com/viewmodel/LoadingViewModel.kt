package weather.android.com.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.getramblin.mobile.retrofit.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import weather.android.com.model.ResponceDataModel

class LoadingViewModel(application: Application) : AndroidViewModel(application) {

    var successResponseData: MutableLiveData<ResponceDataModel> = MutableLiveData()
    var errorResponseData: MutableLiveData<String> = MutableLiveData()


    fun doWeatherForeCasting(location:String){
        val apiService = ApiClient.create()
        val days ="4"
        val apiKey = "20f50a29a7944172b19105246190609"
        val observable: Observable<ResponceDataModel> = apiService.weatherForecast(apiKey, location, days)
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(response: ResponceDataModel) {
        successResponseData.value = response
    }

    private fun handleError(error: Throwable) {
        if (error is HttpException) {

            val responseBody = error.response().errorBody();
            val jsonObject = JSONObject(responseBody?.string())
            val jsonResult = jsonObject.getJSONObject("error")

            errorResponseData.value = jsonResult.getString("message")
        }
    }
}