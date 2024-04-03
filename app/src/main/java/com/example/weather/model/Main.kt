package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class Main(

    @SerializedName("temp") var temp: String,
    @SerializedName("feels_like") var feelsLike: String,
    @SerializedName("temp_min") var tempMin: String,
    @SerializedName("temp_max") var tempMax: String,
    @SerializedName("pressure") var pressure: String,
    @SerializedName("sea_level") var seaLevel: String,
    @SerializedName("grnd_level") var grndLevel: String,
    @SerializedName("humidity") var humidity: String,
    @SerializedName("temp_kf") var tempKf: String

)