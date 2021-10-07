package gb.android.yanweather.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import gb.android.yanweather.R
import gb.android.yanweather.databinding.FragmentSearchMapBinding
import gb.android.yanweather.utils.showSnackbarAnchorView

class MapFragment : Fragment() {

    private lateinit var map: GoogleMap


    companion object {
        fun newInstance() = MapFragment()
    }

    private var _binding: FragmentSearchMapBinding? = null
    private val binding: FragmentSearchMapBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener {
            val geocoder = Geocoder(requireContext())
            val addressRow = binding.searchAddress.text.toString()
            val address = geocoder.getFromLocationName(addressRow, 1)
            if (address.isNotEmpty()) {
                val location = LatLng(address[0].latitude, address[0].longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))

                var addressString =
                    "${address[0].countryName}, ${address[0].locality}, ${address[0].thoroughfare}, ${address[0].subThoroughfare}"

                map.addMarker(
                    MarkerOptions()
                        .title(addressString)
                        .position(location)
                )
            } else {
                binding.root.showSnackbarAnchorView(
                    R.string.address_not_found,
                    Snackbar.LENGTH_SHORT,
                    binding.snackbarAnchor
                )
            }
        }
    }

    private val callback = OnMapReadyCallback {
        map = it

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true

        checkPermission()

        map.uiSettings.isMyLocationButtonEnabled = true

        map.setOnMapLongClickListener { location ->
            map.addMarker(
                MarkerOptions()
                    .title("")
                    .position(location)
            )
        }
    }

    //================================================================================================
    // FINE LOCATION PERMISSION

    @SuppressLint("MissingPermission")
    private val requestFineLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                map.isMyLocationEnabled = true
            }
        }

    fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                map.isMyLocationEnabled = true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                requestFineLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else -> {
                requestFineLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


}