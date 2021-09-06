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
import gb.android.yanweather.view.OnItemViewClickListener
import gb.android.yanweather.viewmodel.AppState
import gb.android.yanweather.viewmodel.MainViewModel

class MainFragment : Fragment(), OnItemViewClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private var isDataSetRus: Boolean = true
    private var adapter = MainFragmentAdapter()

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

        binding.mainFragmentRecyclerView.adapter = adapter
        adapter.setOnItemViewClickListener(this)

        binding.mainFragmentFAB.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                isDataSetRus = !isDataSetRus

                if (isDataSetRus) {
                    viewModel.getWeatherFromLocalSourceRus()
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    viewModel.getWeatherFromLocalSourceWorld()
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }
        })

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getLiveData()
            .observe(viewLifecycleOwner, Observer<AppState> { appState: AppState ->
                renderData(appState)
            })

        viewModel.getWeatherFromLocalSourceRus()
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
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "ERROR $throwable", Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val weather = appState.weatherData
                adapter.setWeather(weather)
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
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