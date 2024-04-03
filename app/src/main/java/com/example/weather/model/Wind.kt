package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class Wind(

    @SerializedName("speed") var speed: String,
    @SerializedName("deg") var deg: String,
    @SerializedName("gust") var gust: String

)