package gb.android.yanweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gb.android.yanweather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commit()

    }
}