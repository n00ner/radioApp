package ru.n00ner.musicradioapp.domain.usecase

import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class GetCurrentTrackUseCase (private val networkRepository: NetworkRepository) {

    suspend fun execute(): Response<CurrentTrack> {
        val track = networkRepository.getCurrentTrack()
        if(track.ResponseType == ResponseType.SUCCESS){
            return track
        }
        return Response.error("Track is null")
    }

    suspend fun sendDislike(trackId: Int): Response<Stream>{
        return networkRepository.dislikeTrack(trackId)
    }

    suspend fun getCurrentStream(): Response<Stream> {
        val stream = networkRepository.getStream()
        if(stream.ResponseType == ResponseType.SUCCESS){
            return stream
        }
        return Response.error("Stream is null")
    }
}