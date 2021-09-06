package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather
import gb.android.yanweather.domain.getRussianCities
import gb.android.yanweather.domain.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}