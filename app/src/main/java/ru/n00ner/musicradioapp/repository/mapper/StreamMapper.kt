package ru.n00ner.musicradioapp.repository.mapper


import ru.n00ner.musicradioapp.datasource.model.StreamResponse
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.utils.BaseMapper
import ru.n00ner.musicradioapp.utils.NETWORK_DATASOURCE_ERROR_MESSAGE
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

object StreamMapper : BaseMapper<Response<StreamResponse>?, Response<Stream>> {

    override fun mapFrom(type: Response<StreamResponse>?): Response<Stream> = when {
        type?.ResponseType?.equals(ResponseType.SUCCESS)!! -> Response.success(Stream(type.data!!.id, type.data.title, type.data!!.poster, type.data!!.stream_url))
        else -> Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
    }
}