package gb.android.yanweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.yanweather.App
import gb.android.yanweather.repository.LocalRepositoryImpl


class HistoryViewModel(
    private val historyLiveDataToObserve: MutableLiveData<MainState> = MutableLiveData(),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDAO())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveDataToObserve.value = MainState.Loading
        historyLiveDataToObserve.postValue(MainState.Success(historyRepositoryImpl.getAllHistory()))
    }

    fun getLiveData() = historyLiveDataToObserve;
}