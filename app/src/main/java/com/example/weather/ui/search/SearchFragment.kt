package com.example.weather.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.model.WeatherList
import com.example.weather.ui.Base.BaseFragment
import com.example.weather.util.converToCelisuse
import com.example.weather.util.getCurrentDate
import com.example.weather.util.hide
import com.example.weather.util.show
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val logInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate
    override val logTag: String
        get() = this::class.java.simpleName

    override fun addCallbacks() {}

    override fun setup() {

        binding?.inputCityName?.setOnEditorActionListener { _, name, _ ->
            if (name == EditorInfo.IME_ACTION_SEARCH && binding?.inputCityName?.text?.trim()
                    ?.isNotEmpty() == true
            ) {
                searchByCityName(binding?.inputCityName?.text.toString())
                binding?.imgSearchNotFound?.hide()
                true
            } else {
                binding?.apply {
                    exploreCity.text = getString(R.string.please_enter_valid_city_name)
                    imgSearchNotFound.show()
                    imgSearch.hide()
                    resultSearch.hide()
                }

                false
            }
        }
    }

    private fun searchByCityName(city: String) {

        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=$API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "fail:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code != 200) {
                    // City not found
                    activity?.runOnUiThread {
                        binding?.apply {
                            resultSearch.hide()
                            exploreCity.text = R.string.please_enter_valid_city_name.toString()
                            imgSearch.hide()
                            imgSearchNotFound.show()
                        }
                    }
                } else {
                    response.body?.string()?.let { jsonString ->
                        val result = Gson().fromJson(jsonString, WeatherList::class.java)
                        activity?.runOnUiThread {
                            binding?.resultSearch?.show()
                            binding?.apply {
                                baseDegree.text = converToCelisuse(result.main.temp.toFloat())
                                nightDegree.text =
                                    "Night : ${converToCelisuse(result.main.tempMin.toFloat())}"
                                dayDegree.text =
                                    "Day : ${converToCelisuse(result.main.tempMax.toFloat())}"
                                fellLike.text =
                                    "Feels like : ${converToCelisuse(result.main.feelsLike.toFloat())}"
                                cityName.text = city
                                humidity.text = "Humidity : ${result.main.humidity}%"
                                typeOfWeather.text = result.weather[0].main
                                date.text = getCurrentDate()
                            }
                        }
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "Search"
        const val API_KEY = "c6cd2e02addcd092deac1b921e13af39"
    }
}