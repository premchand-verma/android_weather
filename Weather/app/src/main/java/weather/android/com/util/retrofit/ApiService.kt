package weather.android.com.util.retrofit


import io.reactivex.Observable
import retrofit2.http.*
import weather.android.com.model.ResponceDataModel

interface ApiService {

    @GET("forecast.json")
    fun weatherForecast(
        @Query("key") key : String?,
        @Query("q") q : String?,
        @Query("days") days : String?
    ): Observable<ResponceDataModel>

}