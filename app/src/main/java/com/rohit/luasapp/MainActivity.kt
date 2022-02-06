package com.rohit.luasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rohit.luasapp.ui.forecast.ForecastFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Very first Activity to be loaded and responsible for holding the fragments.
 * Initialize with the ForecastFragment.
 *
 * Annotated with @AndroidEntryPoint to generate an individual Hilt component for each
 * Android class in your project. These components can receive dependencies from their
 * respective parent classes.
 *
 * Android classes that depend on this Activity should also be annotated with @AndroidEntryPoint
 * in order to receive the dependencies.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ForecastFragment.newInstance())
                    .commitNow()
        }
    }
}