package com.example.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherListResponse(
    @SerializedName("list") val weatherList: List<WeatherList>
)