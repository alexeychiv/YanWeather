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

    fun getDataFromRemoteSource() {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromRemoteSource()))
        }.start()
    }

}