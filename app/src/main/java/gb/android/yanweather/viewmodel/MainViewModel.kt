package gb.android.yanweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.yanweather.repository.Repository
import gb.android.yanweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<MainState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        with(liveDataToObserve) {
            postValue(MainState.Loading)
            Thread {
                sleep(500)

                if (isRussian)
                    postValue(MainState.Success(repository.getWeatherFromLocalStorageRus()))
                else
                    postValue(MainState.Success(repository.getWeatherFromLocalStorageWorld()))
            }.start()
        }
    }
}