package gb.android.yanweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.yanweather.repository.Repository
import gb.android.yanweather.repository.RepositoryImpl
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import java.util.*

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getDataFromRemoteSource() {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(2000)

            if (Math.random() * 2 > 1)
                liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromRemoteSource()))
            else
                liveDataToObserve.postValue(AppState.Error(IllegalStateException()))
        }.start()
    }

}