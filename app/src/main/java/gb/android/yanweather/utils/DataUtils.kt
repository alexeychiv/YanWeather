package gb.android.yanweather.utils

import gb.android.yanweather.domain.City
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.domain.getDefaultCity
import gb.android.yanweather.repository.WeatherDTO
import gb.android.yanweather.room.HistoryEntity


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    return Weather(
        getDefaultCity(),
        weatherDTO.fact.temp,
        weatherDTO.fact.feels_like,
        weatherDTO.fact.condition,
        weatherDTO.fact.icon
    )
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(
            City(it.name, 0.0, 0.0),
            it.temperature,
            0,
            it.condition
        )
    }
}

fun convertWeatherToHistoryEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(
        0,
        weather.city.name,
        weather.temperature,
        weather.condition
    )
}