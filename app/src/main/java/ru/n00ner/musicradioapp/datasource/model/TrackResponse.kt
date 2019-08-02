package ru.n00ner.musicradioapp.datasource.model

import com.google.gson.annotations.SerializedName

data class TrackResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("playtime") val playtime : String,
    @SerializedName("on_air") val on_air : String?,
    @SerializedName("mediafile") val mediafile : Mediafile
)

data class Mediafile (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("performer_title") val performer_title : String,
    @SerializedName("type") val type : Int,
    @SerializedName("filename") val filename : String,
    @SerializedName("duration") val duration : Int,
    @SerializedName("values") val values : List<Int>,
    @SerializedName("rightholder") val rightholder : Int,
    @SerializedName("histogram") val histogram : String,
    @SerializedName("rightholder_name") val rightholder_name : String,
    @SerializedName("rms") val rms : Int,
    @SerializedName("tagged") val tagged : Boolean,
    @SerializedName("created") val created : String,
    @SerializedName("content_type") val content_type : Int,
    @SerializedName("bpm") val bpm : Int,
    @SerializedName("sourcefile_size") val sourcefile_size : Int
)