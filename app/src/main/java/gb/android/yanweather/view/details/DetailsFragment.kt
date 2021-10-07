package gb.android.yanweather.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.R
import gb.android.yanweather.databinding.FragmentDetailsBinding
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.utils.showActionSnackbar
import gb.android.yanweather.utils.showSnackbar
import gb.android.yanweather.viewmodel.DetailsState
import gb.android.yanweather.viewmodel.DetailsViewModel


class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

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

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        getWeather()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //===========================================================================================
    // DATA UTILS

    private fun getWeather() {
        viewModel.getWeatherFromRemoteSource(localWeather.city.lat, localWeather.city.lon)
    }

    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Error -> {
                binding.loadingLayout.visibility = View.INVISIBLE
                binding.mainView.visibility = View.VISIBLE
                val throwable = detailsState.error
                view?.showActionSnackbar(
                    R.string.loading_error,
                    Snackbar.LENGTH_LONG,
                    R.string.try_again
                ) {
                    getWeather()
                }
            }

            DetailsState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.mainView.visibility = View.INVISIBLE
            }

            is DetailsState.Success -> {
                binding.loadingLayout.visibility = View.INVISIBLE
                binding.mainView.visibility = View.VISIBLE
                val weather = detailsState.weatherData
                viewModel.saveWeather(
                    Weather(
                        localWeather.city,
                        weather.temperature,
                        weather.feelsLike,
                        weather.condition
                    )
                )
                showWeather(weather)
                view?.showSnackbar(R.string.loading_success, Snackbar.LENGTH_LONG)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showWeather(weather: Weather) {

        with(binding, {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}\nlon ${localWeather.city.lon}"
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = "${weather.feelsLike}"
            weatherCondition.text = getStringResourceByName(weather.condition.replace('-', '_'))

            binding.ivHeader.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png") {
                placeholder(R.drawable.ic_action_loading)
                error(R.drawable.ic_action_error_loading)
            }

            binding.ivIcon.loadIconFromURL("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")
        })
    }

    private fun getStringResourceByName(str: String): String {
        return getString(resources.getIdentifier(str, "string", requireActivity().packageName))
    }

    private fun ImageView.loadIconFromURL(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadIconFromURL.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

}