package ru.n00ner.musicradioapp.domain.usecase

import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.model.CurrentTrack
import ru.n00ner.musicradioapp.domain.model.Stream
import ru.n00ner.musicradioapp.presentation.AppPreferences
import ru.n00ner.musicradioapp.utils.dto.Response
import ru.n00ner.musicradioapp.utils.dto.ResponseType

class GetCurrentTrackUseCase (private val networkRepository: NetworkRepository, private val prefs: AppPreferences) {

    suspend fun execute(): Response<CurrentTrack> {
        val track = networkRepository.getCurrentTrack()
        if(track.ResponseType == ResponseType.SUCCESS){
            return Response.success(CurrentTrack(track.data!!.id, track.data.title, track.data.performerTitle, prefs.isTrackDeleted(track.data.id)))
        }
        return Response.error("Track is null")
    }


}