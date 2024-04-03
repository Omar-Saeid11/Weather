package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class WeatherList(

    @SerializedName("dt") var dt: String,
    @SerializedName("main") var main: Main,
    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds,
    @SerializedName("wind") var wind: Wind,
    @SerializedName("visibility") var visibility: String,
    @SerializedName("pop") var pop: String,
    @SerializedName("sys") var sys: Sys,
    @SerializedName("dt_txt") var dtTxt: String,
    @SerializedName("city") val city :City,
)