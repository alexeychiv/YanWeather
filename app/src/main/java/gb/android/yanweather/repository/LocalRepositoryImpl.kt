package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather
import gb.android.yanweather.room.HistoryDAO
import gb.android.yanweather.utils.convertHistoryEntityToWeather
import gb.android.yanweather.utils.convertWeatherToHistoryEntity


class LocalRepositoryImpl(
    private val localDataSource: HistoryDAO
) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToHistoryEntity(weather))
    }
}