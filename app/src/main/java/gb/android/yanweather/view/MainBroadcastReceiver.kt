package gb.android.yanweather.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val noConnectivity =
            intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (!noConnectivity) {
            StringBuilder().apply {
                append("CONNECTION FOUND!")
                toString().also {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            StringBuilder().apply {
                append("NO CONNECTION!")
                toString().also {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
