package gb.android.yanweather.utils

import gb.android.yanweather.domain.Weather
import gb.android.yanweather.domain.getDefaultCity
import gb.android.yanweather.repository.WeatherDTO


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    return Weather(
        getDefaultCity(),
        weatherDTO.fact.temp,
        weatherDTO.fact.feels_like,
        weatherDTO.fact.condition,
        weatherDTO.fact.icon
    )
}