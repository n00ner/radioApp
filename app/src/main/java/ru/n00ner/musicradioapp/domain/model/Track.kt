package ru.n00ner.musicradioapp.domain.model

data class Track(
    val id: Int,
    val title: String,
    val performerTitle: String,
    val onAir:String?,
    val mediafile: Int
)