package ru.n00ner.musicradioapp.datasource.model

import com.google.gson.annotations.SerializedName

data class CurrentTrackResponse (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("performer_title") val performer_title : String,
    @SerializedName("type") val type : Int,
    @SerializedName("values") val values : List<Int>
)