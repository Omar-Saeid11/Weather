package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class Weather(

    @SerializedName("id") var id: String,
    @SerializedName("main") var main: String,
    @SerializedName("description") var description: String,
    @SerializedName("icon") var icon: String

)