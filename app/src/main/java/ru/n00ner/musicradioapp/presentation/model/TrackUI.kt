package ru.n00ner.musicradioapp.presentation.model

data class TrackUI(
    val id: Int,
    val title: String,
    val performerTitle: String,
    val onAir:String?,
    val mediafile: Int
)