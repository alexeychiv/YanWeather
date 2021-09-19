package gb.android.yanweather.viewmodel

import gb.android.yanweather.domain.Weather

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val weatherData: Weather) : DetailsState()
    data class Error(val error: String) : DetailsState()
}