package gb.android.yanweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.databinding.FragmentMainBinding
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.viewmodel.AppState
import gb.android.yanweather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private lateinit var viewModel: MainViewModel

    //===========================================================================================
    // COMPANION

    companion object {
        fun newInstance() = MainFragment()
    }

    //===========================================================================================
    // LIFECYCLE EVENTS

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getDataFromRemoteSource()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //===========================================================================================
    // RENDER

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.mainView, "ERROR: Loading Failed! ($throwable)", Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE

                val weather = appState.weatherData

                setWeatherData(weather)

                Snackbar.make(binding.mainView, "Loading Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setWeatherData(weather: Weather) {
        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = "${weather.city.lat} : ${weather.city.lon}"
        binding.temperatureValue.text = weather.temp.toString()
        binding.feelsLikeValue.text = weather.feel.toString()
    }

}