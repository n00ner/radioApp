package ru.n00ner.musicradioapp.repository.mapper

import ru.n00ner.musicradioapp.datasource.model.CurrentTrackResponse
import ru.n00ner.musicradioapp.datasource.model.StreamResponse
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.utils.BaseMapper
import ru.n00ner.musicradioapp.utils.NETWORK_DATASOURCE_ERROR_MESSAGE
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

object CurrentTrackMapper : BaseMapper<Response<CurrentTrackResponse>?, Response<CurrentTrack>> {

    override fun mapFrom(type: Response<CurrentTrackResponse>?): Response<CurrentTrack> = when {
        type?.ResponseType?.equals(ResponseType.SUCCESS)!! -> Response.success(CurrentTrack(type.data!!.id, type.data.title, type.data.performer_title))
        else -> Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
    }
}