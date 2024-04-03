package com.example.weather.ui.weather

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.weather.ui.Base.BaseFragment
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.model.ForeCast
import com.example.weather.ui.search.SearchFragment
import com.example.weather.ui.show_weather_days.ListOfItemsFragment
import com.example.weather.util.PreferencesUtil
import com.example.weather.util.converToCelisuse
import com.example.weather.util.getCurrentDate
import com.example.weather.util.replaceFragment
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {
    val listOfItemFragment = ListOfItemsFragment()
    private val logInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    private val searchFragment = SearchFragment()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWeatherBinding
        get() = FragmentWeatherBinding::inflate
    override val logTag: String
        get() = this::class.java.simpleName

    override fun addCallbacks() {
        binding?.apply {
            iconSearch.setOnClickListener {
                replaceFragment(searchFragment)
            }
        }
    }

    override fun setup() {
        val lat = PreferencesUtil.latitude
        val lon = PreferencesUtil.longitude
        getDataLatAndLong(lat, lon)
    }

    private fun getDataLatAndLong(lat: String?, lon: String?) {

        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=$API_KEY")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "fail: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString, ForeCast::class.java)
                    Log.i(TAG, result.toString())
                    activity?.runOnUiThread {
                        binding?.apply {
                            @RequiresApi(Build.VERSION_CODES.O)
                            fun bindWeather(i: Int) {
                                baseDegree.text =
                                    converToCelisuse(result.weatherList[i].main.temp.toFloat())
                                fellLike.text =
                                    "Feels Like ${converToCelisuse(result.weatherList[i].main.feelsLike.toFloat())}"
                                typeOfWeather.text = result.weatherList[i].weather[0].main
                                cityName.text = result.city.name
                                PreferencesUtil.city = result.city.name
                                dayDegree.text =
                                    "Day : ${converToCelisuse(result.weatherList[i].main.tempMax.toFloat())}"
                                nightDegree.text =
                                    "Night : ${converToCelisuse(result.weatherList[i+5].main.tempMin.toFloat())}"
                                windSpeedValue.text = ("${result.weatherList[i].wind.speed}km/h")
                                rainProbability.text = ("${result.weatherList[i].pop}")
                                pressureValue.text = ("${result.weatherList[i].main.pressure} hPa")
                                humidityValue.text = ("${result.weatherList[i].main.humidity}%")
                                weatherNow.text =
                                    converToCelisuse(result.weatherList[i].main.temp.toFloat())
                                weatherAt9am.text =
                                    converToCelisuse(result.weatherList[i + 1].main.temp.toFloat())
                                weatherAt12am.text =
                                    converToCelisuse(result.weatherList[i + 2].main.temp.toFloat())
                                weatherAt3am.text =
                                    converToCelisuse(result.weatherList[i + 3].main.temp.toFloat())
                                weatherAt6pm.text =
                                    converToCelisuse(result.weatherList[i + 4].main.temp.toFloat())
                                weatherAt8pm.text =
                                    converToCelisuse(result.weatherList[i + 5].main.temp.toFloat())
                                date.text = getCurrentDate()
                                sunriseValue.text =
                                    calculateSunriseTime(
                                        result.city.sunrise.toLong(),
                                        result.city.timezone.toInt()
                                    )
                                sunsetValue.text =
                                    calculateSunsetTime(
                                        result.city.sunset.toLong(),
                                        result.city.timezone.toInt()
                                    )
                            }
                            bindWeather(0)
                            today.setOnClickListener {
                                bindWeather(0)
                                today.setBackgroundResource(R.drawable.rectangle_8)
                                tomorrow.setBackgroundResource(R.drawable.rectangle_8_white)
                                tenDays.setBackgroundResource(R.drawable.rectangle_8_white)
                            }
                            tomorrow.setOnClickListener {
                                bindWeather(6)
                                date.text = getNextDate()
                                tomorrow.setBackgroundResource(R.drawable.rectangle_8)
                                today.setBackgroundResource(R.drawable.rectangle_8_white)
                                tenDays.setBackgroundResource(R.drawable.rectangle_8_white)
                            }
                            tenDays.setOnClickListener {
                                replaceFragment(listOfItemFragment)
                                tomorrow.setBackgroundResource(R.drawable.rectangle_8_white)
                                today.setBackgroundResource(R.drawable.rectangle_8_white)
                                tenDays.setBackgroundResource(R.drawable.rectangle_8)
                            }
                        }
                    }
                }

            }
        })
    }

    fun getNextDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Note: Months start from 0
        val day = calendar.get(Calendar.DAY_OF_MONTH) + 1

        return "$year-$month-$day"
    }

    fun calculateSunriseTime(sunrise: Long, timezone: Int): String {
        val sunriseTime = sunrise * 1000L // Convert seconds to milliseconds
        val timeZone = TimeZone.getTimeZone("GMT")
        timeZone.rawOffset = timezone * 1000 // Convert seconds to milliseconds

        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        dateFormat.timeZone = timeZone

        return dateFormat.format(Date(sunriseTime))
    }

    fun calculateSunsetTime(sunset: Long, timezone: Int): String {
        val sunsetTime = sunset * 1000L // Convert seconds to milliseconds
        val timeZone = TimeZone.getTimeZone("GMT")
        timeZone.rawOffset = timezone * 1000 // Convert seconds to milliseconds

        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        dateFormat.timeZone = timeZone

        return dateFormat.format(Date(sunsetTime))
    }

    companion object {
        const val TAG = "Weather"
        const val API_KEY = "c6cd2e02addcd092deac1b921e13af39"
    }
}