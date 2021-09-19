package gb.android.yanweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.R
import gb.android.yanweather.databinding.FragmentMainBinding
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.utils.showActionSnackbar
import gb.android.yanweather.utils.showSnackbar
import gb.android.yanweather.view.OnItemViewClickListener
import gb.android.yanweather.view.details.DetailsFragment
import gb.android.yanweather.viewmodel.MainState
import gb.android.yanweather.viewmodel.MainViewModel

class MainFragment : Fragment(), OnItemViewClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private var isDataSetRus: Boolean = true
    private var adapter = MainFragmentAdapter()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

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
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            adapter.setOnItemViewClickListener(this@MainFragment)

            with(mainFragmentFAB) {
                setOnClickListener {
                    isDataSetRus = !isDataSetRus

                    if (isDataSetRus) {
                        viewModel.getWeatherFromLocalSourceRus()
                        setImageResource(R.drawable.ic_russia)
                    } else {
                        viewModel.getWeatherFromLocalSourceWorld()
                        setImageResource(R.drawable.ic_earth)
                    }
                }
            }
        }

        viewModel.getLiveData()
            .observe(viewLifecycleOwner, Observer<MainState> { mainState: MainState ->
                renderData(mainState)
            })

        viewModel.getWeatherFromLocalSourceRus()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //===========================================================================================
    // RENDER

    private fun renderData(mainState: MainState) {
        when (mainState) {
            is MainState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = mainState.error

                binding.root.showActionSnackbar(
                    R.string.loading_error,
                    Snackbar.LENGTH_SHORT,
                    R.string.try_again
                ) {
                    if (isDataSetRus)
                        viewModel.getWeatherFromLocalSourceRus()
                    else
                        viewModel.getWeatherFromLocalSourceWorld()
                }
            }

            MainState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }

            is MainState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val weather = mainState.weatherData
                adapter.setWeather(weather)

                binding.root.showSnackbar(
                    R.string.loading_success,
                    Snackbar.LENGTH_SHORT
                )
            }
        }
    }

    //===========================================================================================
    // ON CLICK

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(DetailsFragment.BUNDLE_WEATHER_KEY, weather)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, DetailsFragment.newInstance(bundle))
            .addToBackStack("")
            .commit()
    }
}