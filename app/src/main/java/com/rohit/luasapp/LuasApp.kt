package com.rohit.luasapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Overridden Application class to initialize libraries at a higher level, like Hilt dependency injection
 *
 * In the Manifest, this class will be listed under the Application tag in the name field.
 */
@HiltAndroidApp
class LuasApp : Application()