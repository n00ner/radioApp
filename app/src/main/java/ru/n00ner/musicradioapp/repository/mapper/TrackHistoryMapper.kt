package ru.n00ner.musicradioapp.repository.mapper


import ru.n00ner.musicradioapp.datasource.model.TrackResponse
import ru.n00ner.musicradioapp.domain.model.Track
import ru.n00ner.musicradioapp.utils.BaseMapper
import ru.n00ner.musicradioapp.utils.NETWORK_DATASOURCE_ERROR_MESSAGE
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

object TrackHistoryMapper : BaseMapper<Response<List<TrackResponse>>?, Response<List<Track>>> {

    override fun mapFrom(type: Response<List<TrackResponse>>?): Response<List<Track>> = when {
        type?.ResponseType?.equals(ResponseType.SUCCESS)!! -> mapTrackList(type)
        else -> Response.error(NETWORK_DATASOURCE_ERROR_MESSAGE)
    }

    private fun mapTrackList(trackResponseList: Response<List<TrackResponse>>):
            Response<List<Track>> {

        val trackModel: MutableList<Track> = mutableListOf()

        trackResponseList.data?.forEach {
            trackModel.add(
               Track(
                   id = it.id,
                   title = it.mediafile.title,
                   performerTitle = it.mediafile.performer_title,
                   onAir = it.on_air,
                   mediafile = it.mediafile.id
               )
            )
        }
        return Response.success(trackModel)
    }
}