package gb.android.yanweather.repository

import gb.android.yanweather.utils.YANDEX_API_KEY_NAME
import gb.android.yanweather.utils.YANDEX_API_URL_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface WeatherApi {

    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) apikey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}