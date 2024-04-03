package com.example.weather.model

import com.google.gson.annotations.SerializedName


data class City(

    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("coord") var coord: Coord,
    @SerializedName("country") var country: String,
    @SerializedName("population") var population: String,
    @SerializedName("timezone") var timezone: String,
    @SerializedName("sunrise") var sunrise: String,
    @SerializedName("sunset") var sunset: String

)