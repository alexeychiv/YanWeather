package gb.android.yanweather.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
) : Parcelable

fun getDefaultCity() = City("Moscow", 55.0, 37.0)