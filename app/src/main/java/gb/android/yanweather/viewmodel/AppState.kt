package gb.android.yanweather.viewmodel

import gb.android.yanweather.domain.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
}
