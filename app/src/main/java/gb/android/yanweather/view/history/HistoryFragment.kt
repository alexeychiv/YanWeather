package gb.android.yanweather.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.R
import gb.android.yanweather.databinding.FragmentHistoryBinding
import gb.android.yanweather.utils.showSnackbar
import gb.android.yanweather.viewmodel.HistoryViewModel
import gb.android.yanweather.viewmodel.MainState


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding!!

    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.getAllHistory()
    }

    private fun renderData(mainState: MainState) {
        when (mainState) {
            is MainState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val throwable = mainState.error

                binding.root.showSnackbar(
                    R.string.loading_error,
                    Snackbar.LENGTH_SHORT
                )
            }

            MainState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is MainState.Success -> {
                binding.historyFragmentRecyclerview.adapter = adapter
                binding.loadingLayout.visibility = View.GONE
                val weather = mainState.weatherData
                adapter.setWeather(weather)

                binding.root.showSnackbar(
                    R.string.loading_success,
                    Snackbar.LENGTH_SHORT
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
