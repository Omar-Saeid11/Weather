package com.example.weather.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val SHARED_PREFERENCES_NAME = "MySharedPref"
    private const val LATITUDE = "latitude"
    private const val LONGITUDE = "longitude"
    private var sharePref: SharedPreferences? = null
    fun initPrefUtil(context: Context) {
        sharePref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    var latitude: String?
        get() = sharePref?.getString(LATITUDE, null)
        set(value) {
            sharePref?.edit()?.putString(LATITUDE, value)?.apply()
        }
    var longitude: String?
        get() = sharePref?.getString(LONGITUDE, null)
        set(value) {
            sharePref?.edit()?.putString(LONGITUDE, value)?.apply()
        }
    var city: String?
        get() = sharePref?.getString("city", null)
        set(value) {
            sharePref?.edit()?.putString("city", value)?.apply()
        }
}