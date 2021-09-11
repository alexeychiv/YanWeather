package gb.android.yanweather.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {

    fun loadWeather() {
        val url = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        Thread {
            val urlConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(
                "X-Yandex-API-Key",
                "185c5584-a5a9-43fe-beef-fa2917437f43"
            )
            urlConnection.readTimeout = 10000

            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))

            val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)

            Handler(Looper.getMainLooper())
                .post { listener.onLoaded(weatherDTO) }

            urlConnection.disconnect()

        }.start()
    }
}