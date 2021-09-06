package gb.android.yanweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.yanweather.repository.Repository
import gb.android.yanweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceWorld() {
        getDataFromLocalSource(false)
    }

    fun getWeatherFromLocalSourceRus() {
        getDataFromLocalSource(true)
    }

    fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(500)

            if (isRussian)
                liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorageRus()))
            else
                liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorageWorld()))
        }.start()
    }
}