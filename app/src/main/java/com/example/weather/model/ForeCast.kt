package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class ForeCast(

  @SerializedName("cod") var cod: String,
  @SerializedName("message") var message:String,
  @SerializedName("cnt") var cnt: String,
  @SerializedName("list") var weatherList: ArrayList<WeatherList> = arrayListOf(),
  @SerializedName("city") var city: City

)