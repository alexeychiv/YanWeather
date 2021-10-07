package gb.android.yanweather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import gb.android.yanweather.R
import gb.android.yanweather.view.history.HistoryFragment
import gb.android.yanweather.view.main.MainFragment
import gb.android.yanweather.view.map.MapFragment

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { it ->
            if (it.isSuccessful) {
                Log.d("BLAH", it.result.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(mainBroadcastReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_fragment_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HistoryFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            R.id.action_open_map -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MapFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}