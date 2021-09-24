package gb.android.yanweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.yanweather.App.Companion.getHistoryDAO
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.repository.DetailsRepositoryImpl
import gb.android.yanweather.repository.LocalRepositoryImpl
import gb.android.yanweather.repository.RemoteDataSource
import gb.android.yanweather.repository.WeatherDTO
import gb.android.yanweather.utils.convertDtoToModel

class DetailsViewModel(
    private val detailsLiveDataToObserve: MutableLiveData<DetailsState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(getHistoryDAO())
) :
    ViewModel() {

    fun getLiveData() = detailsLiveDataToObserve

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveDataToObserve.value = DetailsState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callback)
    }

    fun saveWeather(weather: Weather) {
        historyRepositoryImpl.saveEntity(weather)
    }

    private val callback = object : retrofit2.Callback<WeatherDTO> {

        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val weatherDTO = response.body()
                weatherDTO?.let {
                    detailsLiveDataToObserve.postValue(
                        DetailsState.Success(
                            convertDtoToModel(
                                weatherDTO
                            )
                        )
                    )
                }
            } else {
                detailsLiveDataToObserve.postValue(DetailsState.Error("Error Loading Details"))
            }
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            detailsLiveDataToObserve.postValue(DetailsState.Error("Failure Loading Details"))
        }
    }


}