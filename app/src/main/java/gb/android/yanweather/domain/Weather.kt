package gb.android.yanweather.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temp: Int = -1,
    val feel: Int = -5,
)

