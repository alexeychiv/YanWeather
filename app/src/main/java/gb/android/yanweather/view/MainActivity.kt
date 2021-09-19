package gb.android.yanweather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.android.yanweather.R
import gb.android.yanweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val mainBroadcastReceiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commit()

        registerReceiver(
            mainBroadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(mainBroadcastReceiver)
    }
}