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


}