package ru.n00ner.musicradioapp.utils.dto

import com.google.gson.annotations.SerializedName

data class SendTrack(
   @SerializedName("mediafile") val mediafile: Int
)