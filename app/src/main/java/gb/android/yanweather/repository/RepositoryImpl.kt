package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather

class RepositoryImpl : Repository {
    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

}