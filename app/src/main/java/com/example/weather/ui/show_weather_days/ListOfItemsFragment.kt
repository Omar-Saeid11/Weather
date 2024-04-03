package com.example.weather.ui.show_weather_days

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weather.ui.Base.BaseFragment
import com.example.weather.databinding.ListItemWeatherCardBinding
import com.example.weather.model.WeatherListResponse
import com.example.weather.util.PreferencesUtil
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ListOfItemsFragment : BaseFragment<ListItemWeatherCardBinding>() {

    private lateinit var itemAdapter: ItemAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ListItemWeatherCardBinding
        get() = ListItemWeatherCardBinding::inflate
    override val logTag: String
        get() = this::class.java.simpleName

    override fun addCallbacks() {}

    override fun setup() {
        fetchWeatherData(PreferencesUtil.city.toString())
    }

    private fun fetchWeatherData(city: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/forecast?q=${city}&appid=$API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle network failure
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val weatherList =
                    Gson().fromJson(responseData, WeatherListResponse::class.java).weatherList

                activity?.runOnUiThread {
                    itemAdapter = ItemAdapter(weatherList)
                    binding?.recyclerListOfItems?.adapter = itemAdapter
                }
            }
        })
    }

    companion object {
        const val API_KEY = "c6cd2e02addcd092deac1b921e13af39"
    }
}
