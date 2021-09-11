package gb.android.yanweather.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.R
import gb.android.yanweather.databinding.FragmentDetailsBinding
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.repository.WeatherDTO
import gb.android.yanweather.repository.WeatherLoader
import gb.android.yanweather.repository.WeatherLoaderListener


class DetailsFragment : Fragment(), WeatherLoaderListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    //===========================================================================================
    // COMPANION

    companion object {

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val BUNDLE_WEATHER_KEY = "key"
    }

    //===========================================================================================
    // LIFECYCLE EVENTS

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val localWeather: Weather by lazy {
        (arguments?.getParcelable(BUNDLE_WEATHER_KEY)) ?: Weather()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherLoader(this, localWeather.city.lat, localWeather.city.lon).loadWeather()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //===========================================================================================
    // WeatherLoaderListener EVENTS

    override fun onLoaded(weatherDTO: WeatherDTO) {
        showWeather(weatherDTO)
    }

    override fun onFailed(throwable: Throwable) {
        Snackbar
            .make(binding.root, R.string.loading_error, Snackbar.LENGTH_LONG)
            .show()
    }

    //===========================================================================================
    // DATA UTILS

    @SuppressLint("SetTextI18n")
    private fun showWeather(weatherDTO: WeatherDTO) {

        with(binding, {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}\nlon ${localWeather.city.lon}"
            temperatureValue.text = weatherDTO.fact.temp.toString()
            feelsLikeValue.text = "${weatherDTO.fact.feels_like}"
            weatherCondition.text = weatherDTO.fact.condition
        })
    }

}