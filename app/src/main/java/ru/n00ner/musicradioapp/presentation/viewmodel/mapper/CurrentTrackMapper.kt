package ru.n00ner.musicradioapp.presentation.viewmodel.mapper

import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.presentation.model.CurrentTrackUI
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.utils.BaseMapper
import ru.n00ner.musicradioapp.utils.dto.ResponseType

object CurrentTrackMapper : BaseMapper<CurrentTrack, CurrentTrackUI?> {
    override fun mapFrom(type: CurrentTrack?): CurrentTrackUI? {
        return CurrentTrackUI(type!!.id, type.title, type.performerTitle)
    }
}