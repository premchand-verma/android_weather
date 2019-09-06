package weather.android.com.model

data class ResponceDataModel(
    val alert: Alert,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)