package gb.android.yanweather.domain

data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
)

fun getDefaultCity() = City("Moscow", 55.0, 37.0)