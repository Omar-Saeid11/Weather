package com.example.weather.ui.show_weather_days

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weather.ui.Base.BaseAdapter
import com.example.weather.databinding.ItemWeatherCardBinding
import com.example.weather.model.WeatherList
import com.example.weather.util.converToCelisuse

class ItemAdapter( weatherList: List<WeatherList>) :
    BaseAdapter<WeatherList, ItemWeatherCardBinding>(weatherList) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemWeatherCardBinding
        get() = ItemWeatherCardBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemWeatherCardBinding>,
        position: Int,
        currentItem: WeatherList
    ) {
        holder.binding.apply {
            day.text = currentItem.dtTxt
            typeOfWind.text = currentItem.weather[0].main
            maxTemp.text = converToCelisuse(currentItem.main.tempMax.toFloat())
            minTemp.text = converToCelisuse(currentItem.main.tempMin.toFloat())
        }
    }
}
