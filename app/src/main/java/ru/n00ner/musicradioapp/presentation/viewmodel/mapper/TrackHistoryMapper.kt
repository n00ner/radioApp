package ru.n00ner.musicradioapp.presentation.viewmodel.mapper

import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.presentation.model.TrackUI
import ru.n00ner.musicradioapp.utils.BaseMapper

object TrackHistoryMapper : BaseMapper<List<Track>, List<TrackUI>> {
    override fun mapFrom(type: List<Track>?): List<TrackUI> {
        val result: MutableList<TrackUI> = mutableListOf()

        type?.let { tracks ->
            tracks.forEach {
                result.add(
                    TrackUI(
                        id = it.id,
                        title = it.title,
                        performerTitle = it.performerTitle,
                        onAir = it.onAir,
                        mediafile = it.mediafile
                    )
                )
            }
        }
        return result
    }
}