package com.rohit.luasapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rohit.luasapp.ui.forecast.ForecastFragment
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container, ForecastFragment.newInstance())
                .commitNow()
        }
    }

}