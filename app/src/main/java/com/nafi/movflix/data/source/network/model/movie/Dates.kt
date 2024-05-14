package com.nafi.movflix.data.source.network.model.movie

import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maxDate: String,
    @SerializedName("minimum")
    val minDate: String,
)
